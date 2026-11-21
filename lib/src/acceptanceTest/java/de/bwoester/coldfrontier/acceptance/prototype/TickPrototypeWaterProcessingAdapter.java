package de.bwoester.coldfrontier.acceptance.prototype;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.acceptance.WaterProcessingTestEngine;
import de.bwoester.coldfrontier.data.EventFactory;
import de.bwoester.coldfrontier.data.memory.InMemoryDataAccess;
import de.bwoester.coldfrontier.data.memory.InMemoryValueRepository;
import de.bwoester.coldfrontier.production.recipe.Recipe;
import de.bwoester.coldfrontier.production.recipe.RecipeProcessing;
import de.bwoester.coldfrontier.resources.ResourceAmounts;

import java.time.Duration;
import java.util.Map;

import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;
import static de.bwoester.coldfrontier.resources.ResourceType.ICE;
import static de.bwoester.coldfrontier.resources.ResourceType.WATER;

public final class TickPrototypeWaterProcessingAdapter implements WaterProcessingTestEngine {
    private TickCoordinator ticker;
    private RecipeProcessing processing;

    @Override
    public void initialize(WaterFixture fixture) {
        var data = new InMemoryDataAccess();
        var values = new InMemoryValueRepository(data.getData(), new EventFactory(() -> 0L));
        ticker = new TickCoordinator(values.get(Long.class, "tick", 0L));
        var recipe = new Recipe(
                new ResourceAmounts(Map.of(ICE, fixture.icePerUnit(), ENERGY, fixture.energyPerUnit())),
                new ResourceAmounts(Map.of(WATER, 1L)));
        var stock = new ResourceAmounts(Map.of(ICE, fixture.ice(), ENERGY, fixture.energy(), WATER, fixture.water()));
        processing = new RecipeProcessing(recipe, fixture.waterPerHour(), stock, Map.of(WATER, fixture.waterStorage()));
        ticker.addResourceUpdate(processing::advanceTick);
    }

    @Override
    public void advanceTo(Duration gameTime) {
        ticker.advanceTo(gameTime);
    }

    @Override
    public WaterState readState() {
        var state = processing.state();
        var stock = state.stock();
        return new WaterState(stock.amountOf(ICE), stock.amountOf(ENERGY), stock.amountOf(WATER),
                state.completedWork().isPresent());
    }
}
