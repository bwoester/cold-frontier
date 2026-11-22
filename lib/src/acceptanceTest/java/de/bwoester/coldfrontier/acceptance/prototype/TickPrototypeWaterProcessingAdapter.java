package de.bwoester.coldfrontier.acceptance.prototype;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.acceptance.WaterProcessingTestEngine;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.StaticBuildingDataProvider;
import de.bwoester.coldfrontier.input.SupplyPlanetResourcesInputMsg;
import de.bwoester.coldfrontier.production.recipe.Recipe;
import de.bwoester.coldfrontier.production.recipe.RecipeProcessing;
import de.bwoester.coldfrontier.resources.ResourceAmounts;
import de.bwoester.coldfrontier.prototype.testing.InMemoryPlanetSimulation;

import java.time.Duration;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;
import static de.bwoester.coldfrontier.resources.ResourceType.ICE;
import static de.bwoester.coldfrontier.resources.ResourceType.WATER;

public final class TickPrototypeWaterProcessingAdapter implements WaterProcessingTestEngine {
    private final TreeMap<Duration, Long> energySupplies = new TreeMap<>();
    private InMemoryPlanetSimulation simulation;

    @Override
    public void initialize(WaterFixture fixture) {
        energySupplies.clear();
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
    public void scheduleEnergySupply(long units, Duration gameTime) {
        Duration tickDuration = simulation.ticker().tickDuration();
        if (units <= 0 || gameTime.compareTo(simulation.gameTime()) <= 0
                || !tickDuration.multipliedBy(gameTime.dividedBy(tickDuration)).equals(gameTime)) {
            throw new IllegalArgumentException("Energy must be supplied at a future tick boundary");
        }
        energySupplies.merge(gameTime, units, Math::addExact);
    }

    @Override
    public void advanceTo(Duration gameTime) {
        while (!energySupplies.isEmpty() && energySupplies.firstKey().compareTo(gameTime) <= 0) {
            var supply = energySupplies.firstEntry();
            Duration effectiveTime = supply.getKey();
            simulation.ticker().advanceTo(effectiveTime.minus(simulation.ticker().tickDuration()));
            simulation.input().add(new SupplyPlanetResourcesInputMsg(simulation.tick().get(),
                    UUID.randomUUID(), simulation.planetId(), new ResourceAmounts(Map.of(ENERGY, supply.getValue()))));
            simulation.ticker().advanceTo(effectiveTime);
            energySupplies.pollFirstEntry();
        }
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
