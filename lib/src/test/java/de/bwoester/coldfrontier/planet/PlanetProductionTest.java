package de.bwoester.coldfrontier.planet;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import de.bwoester.coldfrontier.prototype.testing.InMemoryPlanetSimulation;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static de.bwoester.coldfrontier.resources.ResourceType.IRON;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlanetProductionTest {

    @Test
    void booksWholeCreditsToThePlayerAndLocalProductionToThePlanet() {
        var hourlyRate = new ResourceSetMsg(new PlanetResourceSetMsg(Map.of(IRON, 3600L)), 1800);
        var startingBalance = new PlanetResourceSetMsg(Map.of());
        var simulation = InMemoryPlanetSimulation.create(TickCoordinator.DEFAULT_TICK_DURATION,
                building -> new BuildingMsg(building.name(), 1, ResourceSetMsg.createDefault(), hourlyRate),
                Map.of(Building.IRON_MINE, 1L), startingBalance, ResourceSetMsg.createDefault());

        simulation.ticker().tick();

        assertEquals(0, simulation.userAccount().getBalance());
        assertEquals(new PlanetResourceSetMsg(Map.of(IRON, 1L)), simulation.account().getBalance());
        assertEquals(new ResourceSetMsg(startingBalance, 1_800_000_000_000L), simulation.remainder().get());

        simulation.ticker().tick();

        assertEquals(1, simulation.userAccount().getBalance());
        assertEquals(new PlanetResourceSetMsg(Map.of(IRON, 2L)), simulation.account().getBalance());
        assertEquals(ResourceSetMsg.createDefault(), simulation.remainder().get());
    }
}
