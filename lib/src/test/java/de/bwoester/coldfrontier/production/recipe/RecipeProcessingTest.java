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
        var processing = new RecipeProcessing(steel, 2400, initialStock, Map.of());

        processing.advanceTick(Duration.ofSeconds(1));

        assertEquals(initialStock, processing.state().stock());
        assertFalse(processing.state().completedWork().isPresent());
    }

    @Test
    void twoQuarterSecondTicksCompleteOneHalfSecondOperation() {
        var initialStock = new ResourceAmounts(Map.of(IRON, 2L, ENERGY, 1L));
        var processing = new RecipeProcessing(steel, 7200, initialStock, Map.of());
        var tickDuration = Duration.ofMillis(250);

        processing.advanceTick(tickDuration);
        assertEquals(0L, processing.state().stock().amountOf(STEEL));

        processing.advanceTick(tickDuration);
        assertEquals(1L, processing.state().stock().amountOf(STEEL));
        assertFalse(processing.state().completedWork().isPresent());
    }

    @Test
    void steelRecipeRetainsWorkAcrossTicksAndUsesLeftoverCapacity() {
        var initialStock = new ResourceAmounts(Map.of(IRON, 4L, ENERGY, 2L));
        var processing = new RecipeProcessing(steel, 2400, initialStock, Map.of());

        processing.advanceTick(Duration.ofSeconds(1));
        assertEquals(0L, processing.state().stock().amountOf(STEEL));

        processing.advanceTick(Duration.ofSeconds(1));
        assertEquals(1L, processing.state().stock().amountOf(STEEL));
        assertEquals(0L, processing.state().stock().amountOf(IRON));
        assertEquals(0L, processing.state().stock().amountOf(ENERGY));

        processing.advanceTick(Duration.ofSeconds(1));
        assertEquals(2L, processing.state().stock().amountOf(STEEL));
        assertFalse(processing.state().completedWork().isPresent());
    }
}
