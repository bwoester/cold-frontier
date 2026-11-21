package de.bwoester.coldfrontier.acceptance;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import de.bwoester.coldfrontier.testing.InMemoryPlanetSimulation;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

final class PrototypeExtractionTestEngine implements ExtractionTestEngine {
    // The prototype expresses its stored numerator over the number of nanoseconds per game hour.
    private static final BigDecimal REMAINDER_DENOMINATOR = BigDecimal.valueOf(Duration.ofHours(1).toNanos());

    private InMemoryPlanetSimulation simulation;
    private long storageCapacity;

    @Override
    public void startWithCompletedMine(CompletedMine fixture) {
        if (!fixture.startTime().isZero()) {
            throw new IllegalArgumentException("The prototype fixture starts at game time zero");
        }
        storageCapacity = fixture.storageCapacity();
        BuildingMsg mine = new BuildingMsg(Building.IRON_MINE.toString(), 0, ResourceSetMsg.createDefault(),
                ironOnly(fixture.ironPerHour()));
        long initialNumerator = fixture.retainedIron().multiply(REMAINDER_DENOMINATOR).longValueExact();
        simulation = InMemoryPlanetSimulation.create(TickCoordinator.DEFAULT_TICK_DURATION, building -> mine,
                Map.of(Building.IRON_MINE, 1L), ironOnly(fixture.availableIron()).planetResources(),
                ironOnly(initialNumerator));
    }

    @Override
    public void advanceTo(Duration gameTime) {
        simulation.ticker().advanceTo(gameTime);
    }

    @Override
    public IronState readIron() {
        long available = simulation.account().getBalance().iron();
        // The prototype lacks an iron storage limit. These examples require spare capacity.
        if (available >= storageCapacity) {
            throw new IllegalStateException("The prototype adapter only supports non-binding iron storage limits");
        }
        long numerator = simulation.remainder().get().planetResources().iron();
        BigDecimal retained = BigDecimal.valueOf(numerator).divide(REMAINDER_DENOMINATOR);
        return new IronState(simulation.gameTime(), available, retained);
    }

    private static ResourceSetMsg ironOnly(long iron) {
        return new ResourceSetMsg(new PlanetResourceSetMsg(iron, 0, 0), 0);
    }
}
