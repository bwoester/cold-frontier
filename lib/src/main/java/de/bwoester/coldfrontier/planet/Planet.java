package de.bwoester.coldfrontier.planet;

import de.bwoester.coldfrontier.TickComponent;
import de.bwoester.coldfrontier.accounting.AccountingService;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.accounting.TransactionMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;
import de.bwoester.coldfrontier.buildings.BuildingService;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.input.CreateBuildingInputMsg;
import de.bwoester.coldfrontier.input.InputService;
import de.bwoester.coldfrontier.production.ProductionService;
import de.bwoester.coldfrontier.production.recipe.RecipeProcessing;
import de.bwoester.coldfrontier.progress.ProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.OptionalLong;

/**
 * Handles a single planet.
 */
@Slf4j
@RequiredArgsConstructor
public class Planet implements TickComponent {

    private final Value<PlanetMsg> planetData;
    private final InputService inputService;
    private final BuildingService buildingService;
    private final AccountingService accountingService;
    private final ProductionService productionService;
    private final Value<ResourceSetMsg> productionRemainder;
    private final ProgressService progressService;
    private RecipeProcessing recipeProcessing;
    private OptionalLong completedRecipeWork = OptionalLong.empty();

    /** Configures the prepared planet's recipe and starts the first operation at game time zero if supplied. */
    public void initializeRecipeProcessing(RecipeProcessing processing) {
        if (recipeProcessing != null) {
            throw new IllegalStateException("Recipe processing is already initialized");
        }
        PlanetResourceSetMsg balance = accountingService.getPlanetBalance(planetData.get().id());
        bookRecipeChanges(balance, processing.initialize(balance.toResourceAmounts()));
        recipeProcessing = processing;
    }

    public boolean hasRecipeInProgress() {
        return completedRecipeWork.isPresent();
    }

    /** Reads the current recipe progress against the planet's shared stock. */
    public RecipeProcessing.State recipeState() {
        return recipeState(accountingService.getPlanetBalance(planetData.get().id()));
    }

    private RecipeProcessing.State recipeState(PlanetResourceSetMsg balance) {
        return new RecipeProcessing.State(balance.toResourceAmounts(), completedRecipeWork);
    }

    @Override
    public void handleInput() {
        Collection<CreateBuildingInputMsg> input = inputService.startInputHandling(
                CreateBuildingInputMsg.class,
                i -> i.planetId().equals(planetData.get().id())
        );
        input.forEach(this::handleCreateBuilding);
    }

    private void handleCreateBuilding(CreateBuildingInputMsg input) {
        try {
            Building building = input.building();
            long constructionQueueSize = buildingService.getConstructionQueueSize();
            ResourceSetMsg costs = buildingService.calculateCosts(building);
            TransactionMsg t = new TransactionMsg(
                    String.format("Create building %s (construction queue position %d).", building, constructionQueueSize),
                    TransactionMsg.TransactionType.EXPENSE,
                    costs
            );
            // input processing *could* be done later, after resource updates
            // but input is from previous ticks, so it makes sense to process it first
            if (accountingService.executeTransaction(input.planetId(), t)) {
                buildingService.addToConstructionQueue(building);
            } else {
                // TODO CreateBuildingInputMsg failed, provide feedback to user
            }
            inputService.finishInputHandling(input);
        } catch (Exception e) {
            inputService.failedInputHandling(input, e);
        }
    }

    @Override
    public void handleResourceUpdates(Duration tickDuration) {
        ResourceSetMsg previousRemainder = productionRemainder.get();
        ProductionService.ProductionStep production = productionService.calculateTickProduction(
                buildingService.getBuildings(), previousRemainder);
        TransactionMsg transaction = new TransactionMsg(
                "Planet production", TransactionMsg.TransactionType.INCOME, production.wholeUnits());
        if (accountingService.executeTransaction(planetData.get().id(), transaction)
                && !production.remainder().equals(previousRemainder)) {
            productionRemainder.set(production.remainder());
        }
        // TODO deplete mined resources from planet
        if (recipeProcessing != null) {
            PlanetResourceSetMsg balance = accountingService.getPlanetBalance(planetData.get().id());
            bookRecipeChanges(balance, recipeProcessing.advanceTick(recipeState(balance), tickDuration));
        }
    }

    private void bookRecipeChanges(PlanetResourceSetMsg before, RecipeProcessing.State after) {
        PlanetResourceSetMsg nextBalance = PlanetResourceSetMsg.fromResourceAmounts(after.stock());
        if (!before.equals(nextBalance)) {
            var change = new ResourceSetMsg(nextBalance.subtract(before), 0);
            var transaction = new TransactionMsg("Recipe processing", TransactionMsg.TransactionType.INCOME, change);
            if (!accountingService.executeTransaction(planetData.get().id(), transaction)) {
                throw new IllegalStateException("Could not book recipe stock changes");
            }
        }
        completedRecipeWork = after.completedWork();
    }

    @Override
    public void handleFinishedUnits(Duration elapsedGameTime) {
        progressService.increaseProgress(elapsedGameTime);
        progressService.pollCompletedBuilding()
                .ifPresent(i -> buildingService.addAll(new BuildingCountersMsg(Map.of(i, 1L))));
    }

    @Override
    public void handleQueuedUnits() {
        if (!progressService.hasBuildingInProgress()) {
            buildingService.pollConstructionQueue()
                    .ifPresent(i -> progressService.startBuilding(i.building(), i.timeToBuildFactor()));
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
