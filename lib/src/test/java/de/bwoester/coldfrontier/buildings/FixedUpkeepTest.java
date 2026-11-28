package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.resources.ResourceAmounts;
import org.apache.commons.math3.fraction.BigFraction;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;
import static de.bwoester.coldfrontier.resources.ResourceType.IRON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FixedUpkeepTest {

    @Test
    void renewalsCoincideEvenWhenCoverageIsAFractionOfASecond() {
        var upkeep = new FixedUpkeep(new ResourceAmounts(Map.of(IRON, 14L, ENERGY, 7L)));
        var completion = upkeep.complete(new BigFraction(5), new ResourceAmounts(Map.of(IRON, 3L, ENERGY, 1L)));
        var firstRenewal = upkeep.renew(completion.state(), completion.stock());
        var coincidentRenewal = upkeep.renew(firstRenewal.state(), firstRenewal.stock());

        assertEquals(new BigFraction(1835, 7), firstRenewal.effectiveTime());
        assertEquals(new ResourceAmounts(Map.of(IRON, 1L)), firstRenewal.due());
        assertTrue(firstRenewal.paid());

        assertEquals(new BigFraction(3635, 7), coincidentRenewal.effectiveTime());
        assertEquals(new ResourceAmounts(Map.of(IRON, 1L, ENERGY, 1L)), coincidentRenewal.due());
        assertFalse(coincidentRenewal.paid());
        assertEquals(firstRenewal.stock(), coincidentRenewal.stock());
        assertFalse(coincidentRenewal.state().operational());
        assertTrue(coincidentRenewal.state().nextRenewal().isEmpty());
    }
}
