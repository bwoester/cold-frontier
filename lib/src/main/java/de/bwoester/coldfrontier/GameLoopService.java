package de.bwoester.coldfrontier;

import de.bwoester.coldfrontier.accounting.AccountingService;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.accounting.TransactionMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;
import de.bwoester.coldfrontier.buildings.BuildingService;
import de.bwoester.coldfrontier.input.CreateBuildingInputMsg;
import de.bwoester.coldfrontier.input.InputMsg;
import de.bwoester.coldfrontier.input.InputService;
import de.bwoester.coldfrontier.production.ProductionService;
import de.bwoester.coldfrontier.progress.ProgressService;
import de.bwoester.coldfrontier.user.UserMsg;
import de.bwoester.coldfrontier.user.UserProfileMsg;
import de.bwoester.coldfrontier.user.UserSettingsMsg;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

public class GameLoopService {

    private final NumberFormat tickFormat = NumberFormat.getIntegerInstance(Locale.ENGLISH);

    private final InputService inputService;
    private final BuildingService buildingService;
    private final ProductionService productionService;
    private final AccountingService accountingService;
    private final ProgressService progressService;

    private long currentTick;

    public GameLoopService(long currentTick) {
        this.currentTick = currentTick;
        UserMsg userMsg = new UserMsg("user-1", new UserSettingsMsg("de"), new UserProfileMsg());
        inputService = new InputService(() -> currentTick);
        buildingService = new BuildingService(() -> currentTick, new BuildingCountersMsg(Collections.emptyMap()));
        productionService = new ProductionService(() -> currentTick);
        accountingService = new AccountingService(() -> currentTick, userMsg);
        progressService = new ProgressService(() -> currentTick);
    }

    public void tick() {
        // we calculate the next tick, so let's advance it
        ++currentTick;

        // 1. Input Processing
        inputService.tick();
        Collection<InputMsg> input = inputService.getInput();
        for (InputMsg inputMsg : input) {
            switch (inputMsg) {
                case CreateBuildingInputMsg msg -> {
                    Building building = msg.building();
                    // TODO should we tick() building and accounting service?
                    //  should we get rid of explicit tick()?
                    long constructionQueueSize = buildingService.getConstructionQueueSize();
                    ResourceSetMsg costs = buildingService.calculateCosts(msg);
                    TransactionMsg t = new TransactionMsg(
                            String.format("Create building %s (construction queue position %d).", building, constructionQueueSize),
                            TransactionMsg.TransactionType.EXPENSE,
                            costs
                    );
                    // input processing *could* be done later, after resource updates
                    // but input is from previous ticks, so it makes sense to process it first
                    if (accountingService.executeTransaction("planet-1", t)) {
                        buildingService.addToConstructionQueue(building);
                    } else {
                        // TODO CreateBuildingInputMsg failed, provide feedback to user
                    }
                }
            }
        }

        // 2. Resource Updates
        buildingService.tick();
        BuildingCountersMsg planetBuildings = buildingService.getBuildings();

        productionService.tick(planetBuildings);
        ResourceSetMsg planetProduction = productionService.getProduction();

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
        inputService.removeAll(input);
    }

    public void advanceTo(long tick) {
        while (currentTick < tick) {
            tick();
        }
    }

}
