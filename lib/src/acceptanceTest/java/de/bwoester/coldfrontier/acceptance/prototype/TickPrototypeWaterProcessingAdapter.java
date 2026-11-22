package de.bwoester.coldfrontier.acceptance.prototype;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.acceptance.WaterProcessingTestEngine;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.StaticBuildingDataProvider;
import de.bwoester.coldfrontier.production.recipe.Recipe;
import de.bwoester.coldfrontier.production.recipe.RecipeProcessing;
import de.bwoester.coldfrontier.resources.ResourceAmounts;
import de.bwoester.coldfrontier.prototype.testing.InMemoryPlanetSimulation;

import java.time.Duration;
import java.util.Map;

import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;
import static de.bwoester.coldfrontier.resources.ResourceType.ICE;
import static de.bwoester.coldfrontier.resources.ResourceType.WATER;

public final class TickPrototypeWaterProcessingAdapter implements WaterProcessingTestEngine {
    private InMemoryPlanetSimulation simulation;

    @Override
    public void initialize(WaterFixture fixture) {
        var recipe = new Recipe(
                new ResourceAmounts(Map.of(ICE, fixture.icePerUnit(), ENERGY, fixture.energyPerUnit())),
                new ResourceAmounts(Map.of(WATER, 1L)));
        var stock = new ResourceAmounts(Map.of(ICE, fixture.ice(), ENERGY, fixture.energy(), WATER, fixture.water()));
        simulation = InMemoryPlanetSimulation.create(TickCoordinator.DEFAULT_TICK_DURATION,
                new StaticBuildingDataProvider(), Map.of(), PlanetResourceSetMsg.fromResourceAmounts(stock),
                ResourceSetMsg.createDefault());
        simulation.planet().initializeRecipeProcessing(
                new RecipeProcessing(recipe, fixture.waterPerHour(), Map.of(WATER, fixture.waterStorage())));
    }

    @Override
    public void advanceTo(Duration gameTime) {
        simulation.ticker().advanceTo(gameTime);
    }

    @Override
    public WaterState readState() {
        var state = simulation.planet().recipeState();
        var stock = state.stock();
        return new WaterState(stock.amountOf(ICE), stock.amountOf(ENERGY), stock.amountOf(WATER),
                state.progressFraction());
    }
}
