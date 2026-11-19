package de.bwoester.coldfrontier;

import de.bwoester.coldfrontier.accounting.AccountingService;
import de.bwoester.coldfrontier.accounting.Ledger;
import de.bwoester.coldfrontier.accounting.PlayerLedgerRepo;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.accounting.TransactionMsg;
import de.bwoester.coldfrontier.buildings.*;
import de.bwoester.coldfrontier.data.Keys;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.ValuesUtil;
import de.bwoester.coldfrontier.input.InputQueueMsg;
import de.bwoester.coldfrontier.input.InputService;
import de.bwoester.coldfrontier.planet.Planet;
import de.bwoester.coldfrontier.planet.PlanetMsg;
import de.bwoester.coldfrontier.planet.PlanetService;
import de.bwoester.coldfrontier.production.ProductionService;
import de.bwoester.coldfrontier.progress.ProgressMsg;
import de.bwoester.coldfrontier.progress.ProgressService;
import de.bwoester.coldfrontier.user.UserIdsMsg;
import de.bwoester.coldfrontier.user.UserMsg;
import de.bwoester.coldfrontier.user.UserService;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Map;

@Slf4j
public class ColdFrontier {

    private static final String TICK_DURATION_PROPERTY = "cold-frontier.tick-duration";
    private final ValuesUtil valuesUtil;
    private final PlayerLedgerRepo playerLedgerRepo;
    private final TickCoordinator tickCoordinator;
    private final UserService userService;
    private final PlanetService planetService;

    public static void main(String[] args) {
        log.info("Starting Cold Frontier...");
        String tickDuration = System.getProperty(TICK_DURATION_PROPERTY, TickCoordinator.DEFAULT_TICK_DURATION.toString());
        ColdFrontier.create(Duration.parse(tickDuration));
        try {
            while (true) {
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            // stop
        }
    }

    private ColdFrontier(Duration tickDuration) {
        valuesUtil = ValuesUtil.create();
        Value<Long> tick = valuesUtil.get(Long.class, "tick", 0L);
        tickCoordinator = new TickCoordinator(tick, tickDuration);
        // A saved tick is meaningful only with the resolution at which it was recorded.
        Duration initialDuration = tick.get() == 0 ? tickDuration : TickCoordinator.DEFAULT_TICK_DURATION;
        Value<String> savedTickDuration = valuesUtil.get(String.class, "tick-duration", initialDuration.toString());
        if (!tickDuration.equals(Duration.parse(savedTickDuration.get()))) {
            throw new IllegalArgumentException("Tick duration cannot change for an existing simulation");
        }
        playerLedgerRepo = new PlayerLedgerRepo(valuesUtil);
        planetService = new PlanetService(valuesUtil);
        userService = new UserService(valuesUtil, planetService, playerLedgerRepo);
    }

    public static ColdFrontier create(Duration tickDuration) {
        log.info("Creating Cold Frontier instance...");
        ColdFrontier coldFrontier = new ColdFrontier(tickDuration);
        coldFrontier.init();
        return coldFrontier;
    }

    public void advanceTo(Duration gameTime) {
        tickCoordinator.advanceTo(gameTime);
    }

    private void init() {
        log.info("Initializing Cold Frontier...");
        Value<UserIdsMsg> activeUsers = valuesUtil.get(UserIdsMsg.class, Keys.User.activeUsers());
        if (activeUsers.isPresent()) {
            activeUsers.get().userIds().forEach(this::initUser);
        } else {
            log.warn("No active users found. Creating a new user...");
            Value<UserMsg> user = userService.registerNewUser("someExternalId");
            initUser(user.get().id());
        }
        log.info("Cold Frontier initialized.");
    }

    private void initUser(String userId) {
        Value<UserMsg> user = valuesUtil.get(UserMsg.class, Keys.User.user(userId));
        UserMsg userMsg = user.get();

        Value<InputQueueMsg> newInput = valuesUtil.get(InputQueueMsg.class, Keys.Input.newInput(userId), new InputQueueMsg(new ArrayDeque<>()));
        Value<InputQueueMsg> startedInput = valuesUtil.get(InputQueueMsg.class, Keys.Input.startedInput(userId), new InputQueueMsg(new ArrayDeque<>()));
        Value<InputQueueMsg> finishedInput = valuesUtil.get(InputQueueMsg.class, Keys.Input.finishedInput(userId), new InputQueueMsg(new ArrayDeque<>()));
        Value<InputQueueMsg> failedInput = valuesUtil.get(InputQueueMsg.class, Keys.Input.failedInput(userId), new InputQueueMsg(new ArrayDeque<>()));
        InputService inputService = new InputService(newInput, startedInput, finishedInput, failedInput);

        Ledger ledger = playerLedgerRepo.get(userMsg);
        Value<TransactionMsg> transactions = valuesUtil.get(TransactionMsg.class, Keys.Accounting.playerTransactions(userId));
        AccountingService accountingService = new AccountingService(ledger, transactions);

        userMsg.userProfile().planetIds()
                .forEach(planetId -> initPlanet(planetId, inputService, accountingService));
    }

    private void initPlanet(String planetId, InputService inputService, AccountingService accountingService) {
        Value<PlanetMsg> planetData = valuesUtil.get(PlanetMsg.class, Keys.Planet.planet(planetId));

        Value<BuildingCountersMsg> buildings = valuesUtil.get(BuildingCountersMsg.class, Keys.Building.counters(planetId), new BuildingCountersMsg(Map.of()));
        Value<ConstructionQueueMsg> constructionQueue = valuesUtil.get(ConstructionQueueMsg.class, Keys.Building.queue(planetId), new ConstructionQueueMsg(new ArrayDeque<>()));
        BuildingDataProvider buildingDataProvider = new StaticBuildingDataProvider();
        BuildingService buildingService = new BuildingService(buildings, constructionQueue, buildingDataProvider);

        ProductionService productionService = new ProductionService(buildingDataProvider);

        Value<ProgressMsg> progress = valuesUtil.get(ProgressMsg.class, Keys.Progress.building(planetId));
        ProgressService progressService = new ProgressService(progress, buildingDataProvider);

        Value<ResourceSetMsg> productionRemainder = valuesUtil.get(ResourceSetMsg.class,
                Keys.Planet.productionRemainder(planetId), ResourceSetMsg.createDefault());
        Planet planet = new Planet(planetData, inputService, buildingService, accountingService,
                productionService, productionRemainder, progressService);
        tickCoordinator.add(planet);
    }

}
