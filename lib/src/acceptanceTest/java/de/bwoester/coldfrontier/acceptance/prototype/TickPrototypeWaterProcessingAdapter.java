package de.bwoester.coldfrontier.acceptance.prototype;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.acceptance.WaterProcessingTestEngine;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import de.bwoester.coldfrontier.production.recipe.Recipe;
import de.bwoester.coldfrontier.production.recipe.RecipeProcessing;
import de.bwoester.coldfrontier.resources.ResourceAmounts;
import de.bwoester.coldfrontier.prototype.testing.InMemoryPlanetSimulation;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;
import static de.bwoester.coldfrontier.resources.ResourceType.ICE;
import static de.bwoester.coldfrontier.resources.ResourceType.WATER;

public final class TickPrototypeWaterProcessingAdapter implements WaterProcessingTestEngine {
    private static final BigDecimal REMAINDER_DENOMINATOR = BigDecimal.valueOf(Duration.ofHours(1).toNanos());

    private InMemoryPlanetSimulation simulation;

    @Override
    public void initialize(WaterFixture fixture) {
        var recipe = new Recipe(
                new ResourceAmounts(Map.of(ICE, fixture.icePerUnit(), ENERGY, fixture.energyPerUnit())),
                new ResourceAmounts(Map.of(WATER, 1L)));
        var stock = new ResourceAmounts(Map.of(ICE, fixture.ice(), ENERGY, fixture.energy(), WATER, fixture.water()));
        var buildings = fixture.powerPlant()
                .map(plant -> Map.of(Building.SOLAR_POWER_PLANT, new BuildingMsg(Building.SOLAR_POWER_PLANT.name(),
                        0, ResourceSetMsg.createDefault(), energyOnly(plant.energyPerHour()))))
                .orElseGet(Map::of);
        var initialRemainder = fixture.powerPlant()
                .map(plant -> energyOnly(plant.retainedEnergy().multiply(REMAINDER_DENOMINATOR).longValueExact()))
                .orElseGet(ResourceSetMsg::createDefault);
        var completedBuildings = buildings.isEmpty() ? Map.<Building, Long>of() : Map.of(Building.SOLAR_POWER_PLANT, 1L);
        simulation = InMemoryPlanetSimulation.create(TickCoordinator.DEFAULT_TICK_DURATION,
                buildings::get, completedBuildings, PlanetResourceSetMsg.fromResourceAmounts(stock), initialRemainder);
        simulation.planet().initializeRecipeProcessing(
                new RecipeProcessing(recipe, fixture.waterPerHour(), Map.of(WATER, fixture.waterStorage())));
    }

    @Override
    public void advanceTo(Duration gameTime) {
        simulation.ticker().advanceTo(gameTime);
    }

    private static ResourceSetMsg energyOnly(long energy) {
        return new ResourceSetMsg(new PlanetResourceSetMsg(Map.of(ENERGY, energy)), 0);
    }

    @Override
    public WaterState readState() {
        var state = simulation.planet().recipeState();
        var stock = state.stock();
        return new WaterState(stock.amountOf(ICE), stock.amountOf(ENERGY), stock.amountOf(WATER),
                state.progressFraction());
    }
}
