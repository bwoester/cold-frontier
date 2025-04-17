package de.bwoester.coldfrontier;

import de.bwoester.coldfrontier.accounting.*;
import de.bwoester.coldfrontier.buildings.*;
import de.bwoester.coldfrontier.input.CreateBuildingInputMsg;
import de.bwoester.coldfrontier.input.InputQueueMsg;
import de.bwoester.coldfrontier.input.InputService;
import de.bwoester.coldfrontier.messaging.GameEventFactory;
import de.bwoester.coldfrontier.messaging.GameEventLog;
import de.bwoester.coldfrontier.messaging.GameEventSubject;
import de.bwoester.coldfrontier.messaging.InMemoryGameEventLog;
import de.bwoester.coldfrontier.production.ProductionService;
import de.bwoester.coldfrontier.progress.ProgressService;
import de.bwoester.coldfrontier.user.UserMsg;
import de.bwoester.coldfrontier.user.UserProfileMsg;
import de.bwoester.coldfrontier.user.UserSettingsMsg;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;

public class GameLoopService {

    private final NumberFormat tickFormat = NumberFormat.getIntegerInstance(Locale.ENGLISH);

    private final InputService inputService;
    private final BuildingService buildingService;
    private final ProductionService productionService;
    private final AccountingService accountingService;
    private final ProgressService progressService;

    private long currentTick;

    private final GameEventFactory gameEventFactory;
    private final InMemoryGameEventLog eventLog;

    public GameLoopService(long currentTick) {
        this.currentTick = currentTick;
        gameEventFactory = new GameEventFactory(() -> currentTick);
        eventLog = new InMemoryGameEventLog(gameEventFactory);
        UserMsg userMsg = new UserMsg(
                "user-1",
                Set.of("external-1"),
                new UserSettingsMsg("de"),
                new UserProfileMsg(Set.of("planet-1"))
        );

        GameEventLog<InputQueueMsg> newInputsLog = eventLog.viewOfType(InputQueueMsg.class,
                GameEventSubject.Input.newInput(userMsg.id()));
        GameEventLog<InputQueueMsg> startedInputsLog = eventLog.viewOfType(InputQueueMsg.class,
                GameEventSubject.Input.startedInput(userMsg.id()));
        GameEventLog<InputQueueMsg> finishedInputsLog = eventLog.viewOfType(InputQueueMsg.class,
                GameEventSubject.Input.finishedInput(userMsg.id()));
        GameEventLog<InputQueueMsg> failedInputsLog = eventLog.viewOfType(InputQueueMsg.class,
                GameEventSubject.Input.failedInput(userMsg.id()));
        inputService = new InputService(newInputsLog, startedInputsLog, finishedInputsLog, failedInputsLog);

        GameEventLog<BuildingCountersMsg> buildingCountersLog = eventLog.viewOfType(BuildingCountersMsg.class,
                GameEventSubject.Building.counters("planet-1"));
        GameEventLog<ConstructionQueueMsg> constructionQueueLog = eventLog.viewOfType(ConstructionQueueMsg.class,
                GameEventSubject.Building.queue("planet-1"));
        BuildingDataProvider buildingDataProvider = new StaticBuildingDataProvider();
        buildingService = new BuildingService(buildingCountersLog, constructionQueueLog, buildingDataProvider);
        productionService = new ProductionService(buildingDataProvider);

        PlayerLedgerRepo playerLedgerRepo = new PlayerLedgerRepo(eventLog);
        PlayerLedger playerLedger = playerLedgerRepo.get(userMsg);
        GameEventLog<TransactionMsg> playerTransactions = eventLog.viewOfType(TransactionMsg.class,
                GameEventSubject.Accounting.playerTransactions(userMsg.id()));
        accountingService = new AccountingService(playerLedger, playerTransactions);
        progressService = new ProgressService(() -> currentTick, buildingDataProvider);
    }

    public void tick() {
        // we calculate the next tick, so let's advance it
        ++currentTick;

        // 1. Input Processing
        Queue<CreateBuildingInputMsg> input = inputService.startInputHandling(CreateBuildingInputMsg.class,
                i -> i.planetId().equals("planet-1"));
        CreateBuildingInputMsg msg = null;
        try {
            Iterator<CreateBuildingInputMsg> iterator = input.iterator();
            while (iterator.hasNext()) {
                msg = iterator.next();

                Building building = msg.building();
                // TODO should we tick() building and accounting service?
                //  should we get rid of explicit tick()?
                long constructionQueueSize = buildingService.getConstructionQueueSize();
                ResourceSetMsg costs = buildingService.calculateCosts(msg.building());
                TransactionMsg transactionMsg = new TransactionMsg(
                        String.format("Create building %s (construction queue position %d).", building, constructionQueueSize),
                        TransactionMsg.TransactionType.EXPENSE,
                        costs
                );
                // input processing *could* be done later, after resource updates
                // but input is from previous ticks, so it makes sense to process it first
                if (accountingService.executeTransaction(msg.planetId(), transactionMsg)) {
                    buildingService.addToConstructionQueue(building);
                } else {
                    // TODO CreateBuildingInputMsg failed, provide feedback to user
                }
                inputService.finishInputHandling(msg);
            }
        } catch (Exception e) {
            inputService.failedInputHandling(msg, e);
        }

        // TODO
        inputService.verifyState(currentTick);

        // 2. Resource Updates
        BuildingCountersMsg planetBuildings = buildingService.getBuildings();

        //productionService.tick(planetBuildings);
        ResourceSetMsg planetProduction = productionService.calculateProduction(planetBuildings);

        accountingService.executeTransaction("planet-1",
                new TransactionMsg(
                        "tick " + tickFormat.format(currentTick),
                        TransactionMsg.TransactionType.INCOME,
                        planetProduction)
        );

        // 3. Building and Unit Production

        // 3.1 Check for completed buildings, units, and tech
        progressService.tick();
        BuildingCountersMsg completedBuildings = progressService.getCompletedBuildings();
        buildingService.addAll(completedBuildings);

        // 3.2 Start any queued constructions that should begin this tick
        if (!completedBuildings.counters().isEmpty()) {
            buildingService.pollConstructionQueue().ifPresent(entry -> {
                progressService.startBuilding(entry.building(), entry.timeToBuildFactor());
            });
        }

        // 4. Movement and Position Updates
        // 5. Conflict Resolution
        // 6. Player Visibility & Sensor Updates

        // 7. Cleanup & Persistence
        //inputService.removeAll(input);
    }

    public void advanceTo(long tick) {
        while (currentTick < tick) {
            tick();
        }
    }

}
