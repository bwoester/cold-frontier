package de.bwoester.coldfrontier.production.recipe;

import de.bwoester.coldfrontier.resources.ResourceAmounts;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;
import static de.bwoester.coldfrontier.resources.ResourceType.IRON;
import static de.bwoester.coldfrontier.resources.ResourceType.STEEL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RecipeProcessingTest {
    private final Recipe steel = new Recipe(new ResourceAmounts(Map.of(IRON, 2L, ENERGY, 1L)),
            new ResourceAmounts(Map.of(STEEL, 1L)));

    @Test
    void missingEnergyLeavesIronInStock() {
        var initialStock = new ResourceAmounts(Map.of(IRON, 2L));
        var processing = new RecipeProcessing(steel, 2400, Map.of());
        var state = processing.initialize(initialStock);

        state = processing.advanceTick(state, Duration.ofSeconds(1));

        assertEquals(initialStock, state.stock());
        assertFalse(state.completedWork().isPresent());
    }

    @Test
    void twoQuarterSecondTicksCompleteOneHalfSecondOperation() {
        var initialStock = new ResourceAmounts(Map.of(IRON, 2L, ENERGY, 1L));
        var processing = new RecipeProcessing(steel, 7200, Map.of());
        var state = processing.initialize(initialStock);
        var tickDuration = Duration.ofMillis(250);

        state = processing.advanceTick(state, tickDuration);
        assertEquals(0L, state.stock().amountOf(STEEL));

        state = processing.advanceTick(state, tickDuration);
        assertEquals(1L, state.stock().amountOf(STEEL));
        assertFalse(state.completedWork().isPresent());
    }

    @Test
    void boundarySupplyCanFundTheNextOperationButCannotAdvanceIt() {
        var processing = new RecipeProcessing(steel, 7200, Map.of());
        var initial = processing.initialize(new ResourceAmounts(Map.of(IRON, 4L, ENERGY, 1L)));
        var supply = new ResourceAmounts(Map.of(ENERGY, 1L));
        var credited = new RecipeProcessing.State(initial.stock().credit(supply, Map.of()), initial.completedWork());

        var state = processing.advanceTickWithSupplyAtEnd(credited, Duration.ofSeconds(1), supply);

        assertEquals(new ResourceAmounts(Map.of(STEEL, 1L)), state.stock());
        assertEquals(0L, state.completedWork().orElseThrow());
    }

    @Test
    void steelRecipeRetainsWorkAcrossTicksAndUsesLeftoverCapacity() {
        var initialStock = new ResourceAmounts(Map.of(IRON, 4L, ENERGY, 2L));
        var processing = new RecipeProcessing(steel, 2400, Map.of());
        var state = processing.initialize(initialStock);

        state = processing.advanceTick(state, Duration.ofSeconds(1));
        assertEquals(0L, state.stock().amountOf(STEEL));

        state = processing.advanceTick(state, Duration.ofSeconds(1));
        assertEquals(1L, state.stock().amountOf(STEEL));
        assertEquals(0L, state.stock().amountOf(IRON));
        assertEquals(0L, state.stock().amountOf(ENERGY));

        state = processing.advanceTick(state, Duration.ofSeconds(1));
        assertEquals(2L, state.stock().amountOf(STEEL));
        assertFalse(state.completedWork().isPresent());
    }
}
