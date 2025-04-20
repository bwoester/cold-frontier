package de.bwoester.coldfrontier;

import de.bwoester.coldfrontier.accounting.AccountingService;
import de.bwoester.coldfrontier.accounting.PlayerLedger;
import de.bwoester.coldfrontier.accounting.PlayerLedgerRepo;
import de.bwoester.coldfrontier.accounting.TransactionMsg;
import de.bwoester.coldfrontier.buildings.*;
import de.bwoester.coldfrontier.data.Keys;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.ValuesUtil;
import de.bwoester.coldfrontier.input.InputQueueMsg;
import de.bwoester.coldfrontier.input.InputService;
import de.bwoester.coldfrontier.planet.Planet;
import de.bwoester.coldfrontier.planet.PlanetMsg;
import de.bwoester.coldfrontier.production.ProductionService;
import de.bwoester.coldfrontier.progress.ProgressMsg;
import de.bwoester.coldfrontier.progress.ProgressService;
import de.bwoester.coldfrontier.user.UserIdsMsg;
import de.bwoester.coldfrontier.user.UserMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ColdFrontier {

    // TODO needs to be initialized from data access
    //  But so far, data access takes a tick supplier to be created
    //  --> refactor
    private final AtomicLong tick = new AtomicLong(0);
    private final Value<Long> tickValue;
    private final ValuesUtil valuesUtil;
    private final PlayerLedgerRepo playerLedgerRepo;
    private final TickCoordinator tickCoordinator;

    public static void main(String[] args) {
        log.info("Starting Cold Frontier...");
        ColdFrontier coldFrontier = ColdFrontier.create();
        try {
            while (true) {
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            // stop
        }
    }

    private ColdFrontier() {
        valuesUtil = ValuesUtil.create(tick::get);
        tickValue = valuesUtil.create(Long.class, "TODO");
        playerLedgerRepo = new PlayerLedgerRepo(valuesUtil);
        tickCoordinator = new TickCoordinator(tickValue);
    }

    private static ColdFrontier create() {
        ColdFrontier coldFrontier = new ColdFrontier();
        coldFrontier.init();
        return coldFrontier;
    }

    private void init() {
        Value<UserIdsMsg> activeUsers = valuesUtil.create(UserIdsMsg.class, Keys.User.activeUsers());
        activeUsers.get().userIds().forEach(this::initUser);
    }

    private void initUser(String userId) {
        Value<UserMsg> user = valuesUtil.create(UserMsg.class, Keys.User.user(userId));
        UserMsg userMsg = user.get();

        Value<InputQueueMsg> newInput = valuesUtil.create(InputQueueMsg.class, Keys.Input.newInput(userId));
        Value<InputQueueMsg> startedInput = valuesUtil.create(InputQueueMsg.class, Keys.Input.startedInput(userId));
        Value<InputQueueMsg> finishedInput = valuesUtil.create(InputQueueMsg.class, Keys.Input.finishedInput(userId));
        Value<InputQueueMsg> failedInput = valuesUtil.create(InputQueueMsg.class, Keys.Input.failedInput(userId));
        InputService inputService = new InputService(newInput, startedInput, finishedInput, failedInput);

        PlayerLedger playerLedger = playerLedgerRepo.get(userMsg);
        Value<TransactionMsg > transactions = valuesUtil.create(TransactionMsg.class, Keys.Accounting.playerTransactions(userId));
        AccountingService accountingService = new AccountingService(playerLedger, transactions);

        userMsg.userProfile().planetIds()
                .forEach(planetId -> initPlanet(planetId, userMsg, inputService, accountingService));
    }

    private void initPlanet(String planetId, UserMsg userMsg, InputService inputService, AccountingService accountingService) {
        Value<PlanetMsg> planetData = valuesUtil.create(PlanetMsg.class, Keys.Planet.planet(planetId));

        Value<BuildingCountersMsg> buildings = valuesUtil.create(BuildingCountersMsg.class, Keys.Building.counters(planetId));
        Value<ConstructionQueueMsg> constructionQueue = valuesUtil.create(ConstructionQueueMsg.class, Keys.Building.queue(planetId));
        BuildingDataProvider buildingDataProvider = new StaticBuildingDataProvider();
        BuildingService buildingService = new BuildingService(buildings, constructionQueue, buildingDataProvider);

        ProductionService productionService = new ProductionService(buildingDataProvider);

        Value<ProgressMsg> progress = valuesUtil.create(ProgressMsg.class, Keys.Progress.building(planetId));
        ProgressService progressService = new ProgressService(progress, buildingDataProvider);

        Planet planet = new Planet(planetData, inputService, buildingService, accountingService, productionService, progressService);
        tickCoordinator.add(planet);
    }

}
