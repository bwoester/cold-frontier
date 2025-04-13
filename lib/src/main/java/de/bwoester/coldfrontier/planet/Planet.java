package de.bwoester.coldfrontier.planet;

import de.bwoester.coldfrontier.TickComponent;
import de.bwoester.coldfrontier.accounting.AccountingService;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.accounting.TransactionMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;
import de.bwoester.coldfrontier.buildings.BuildingService;
import de.bwoester.coldfrontier.input.CreateBuildingInputMsg;
import de.bwoester.coldfrontier.input.InputService;
import de.bwoester.coldfrontier.production.ProductionService;
import de.bwoester.coldfrontier.progress.ProgressService;

import java.util.Collection;

/**
 * Handles a single planet.
 */
public class Planet implements TickComponent {

    private PlanetMsg planetData;

    private final InputService inputService;
    private final BuildingService buildingService;
    private final AccountingService accountingService;
    private final ProductionService productionService;
    private final ProgressService progressService;

    public Planet(PlanetMsg planetData, InputService inputService, BuildingService buildingService, AccountingService accountingService, ProductionService productionService, ProgressService progressService) {
        this.planetData = planetData;
        this.inputService = inputService;
        this.buildingService = buildingService;
        this.accountingService = accountingService;
        this.productionService = productionService;
        this.progressService = progressService;
    }

    @Override
    public void handleInput() {
        Collection<CreateBuildingInputMsg> input = inputService.getInput(
                CreateBuildingInputMsg.class,
                i -> i.planetId().equals(planetData.id())
        );
        for (CreateBuildingInputMsg i : input) {
            Building building = i.building();
            long constructionQueueSize = buildingService.getConstructionQueueSize();
            ResourceSetMsg costs = buildingService.calculateCosts(building);
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

    @Override
    public void handleResourceUpdates() {
        BuildingCountersMsg planetBuildings = buildingService.getBuildings();
        ResourceSetMsg planetProduction = productionService.calculateProduction(planetBuildings);
        accountingService.executeTransaction(planetData.id(),
                new TransactionMsg("Planet production", TransactionMsg.TransactionType.INCOME, planetProduction)
        );
        // TODO deplete mined resources from planet
    }

    @Override
    public void handleFinishedUnits() {
        BuildingCountersMsg completedBuildings = progressService.getCompletedBuildings();
        buildingService.addAll(completedBuildings);
    }

    @Override
    public void handleQueuedUnits() {
        if (!progressService.hasBuildingInProgress()) {
            buildingService.pollConstructionQueue().ifPresent(entry -> {
                progressService.startBuilding(entry.building(), entry.timeToBuildFactor());
            });
        }
    }

    @Override
    public void handleMovement() {
        // no movements on a planet
    }

    @Override
    public void handleConflicts() {
        // handled in Fleet component
    }

    @Override
    public void handleVisibilityUpdates() {
        // TODO not yet implemented
        //  - check visibility extending buildings (+ planet modifiers?)
        //  - check energy shortages?
        //  - update planet's visibility range
    }

    @Override
    public void handlePersistence() {
        // nothing to do?
    }

    @Override
    public void handleCleanup() {
        // TODO check planet inputs in STARTED state, which have not finished
        // TODO check planet inputs in NEW state, older than one tick
    }

}
