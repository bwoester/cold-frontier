package de.bwoester.coldfrontier.planet;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.accounting.TransactionMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import de.bwoester.coldfrontier.buildings.StaticBuildingDataProvider;
import de.bwoester.coldfrontier.production.recipe.Recipe;
import de.bwoester.coldfrontier.production.recipe.RecipeProcessing;
import de.bwoester.coldfrontier.prototype.testing.InMemoryPlanetSimulation;
import de.bwoester.coldfrontier.resources.ResourceAmounts;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;
import static de.bwoester.coldfrontier.resources.ResourceType.ICE;
import static de.bwoester.coldfrontier.resources.ResourceType.WATER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PlanetRecipeProcessingTest {
    private final Recipe waterRecipe = new Recipe(new ResourceAmounts(Map.of(ICE, 1L, ENERGY, 2L)),
            new ResourceAmounts(Map.of(WATER, 1L)));

    @Test
    void recipeUsesIceCreditedToPlanetAccountAfterInitialization() {
        var simulation = InMemoryPlanetSimulation.create(TickCoordinator.DEFAULT_TICK_DURATION,
                new StaticBuildingDataProvider(), Map.of(), new PlanetResourceSetMsg(Map.of(ENERGY, 2L)),
                ResourceSetMsg.createDefault());
        simulation.planet().initializeRecipeProcessing(new RecipeProcessing(waterRecipe, 3600, Map.of()));
        assertFalse(simulation.planet().hasRecipeInProgress());
        assertEquals(2, simulation.account().getBalance().amountOf(ENERGY));

        var credit = new ResourceSetMsg(new PlanetResourceSetMsg(Map.of(ICE, 1L)), 0);
        simulation.account().executeTransaction(
                new TransactionMsg("Test ice credit", TransactionMsg.TransactionType.INCOME, credit));
        simulation.ticker().tick();

        assertEquals(new PlanetResourceSetMsg(Map.of(WATER, 1L)),
                simulation.account().getBalance());
    }

    @Test
    void recipeUsesWholeIceMinedInTheSameTick() {
        var production = new ResourceSetMsg(new PlanetResourceSetMsg(Map.of(ICE, 3600L)), 0);
        var simulation = InMemoryPlanetSimulation.create(TickCoordinator.DEFAULT_TICK_DURATION,
                building -> new BuildingMsg(building.name(), 1, ResourceSetMsg.createDefault(), production),
                Map.of(Building.IRON_MINE, 1L), new PlanetResourceSetMsg(Map.of(ENERGY, 2L)), ResourceSetMsg.createDefault());
        simulation.planet().initializeRecipeProcessing(new RecipeProcessing(waterRecipe, 3600, Map.of()));
        assertFalse(simulation.planet().hasRecipeInProgress());

        simulation.ticker().tick();

        assertEquals(new PlanetResourceSetMsg(Map.of(WATER, 1L)),
                simulation.account().getBalance());
    }
}
