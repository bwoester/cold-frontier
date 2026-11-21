package de.bwoester.coldfrontier.acceptance.prototype;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.acceptance.ExtractionTestEngine;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import de.bwoester.coldfrontier.prototype.testing.InMemoryPlanetSimulation;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

public final class TickPrototypeExtractionAdapter implements ExtractionTestEngine {
    // The prototype expresses its stored numerator over the number of nanoseconds per game hour.
    private static final BigDecimal REMAINDER_DENOMINATOR = BigDecimal.valueOf(Duration.ofHours(1).toNanos());

    private InMemoryPlanetSimulation simulation;
    private long storageCapacity;

    @Override
    public void startWithCompletedMines(MineFixture fixture, long completedMines) {
        start(fixture, completedMines, 0);
    }

    @Override
    public void startWithMineUnderConstruction(MineFixture fixture, long completedMines, Duration completionTime) {
        Duration buildTime = completionTime.minus(fixture.startTime());
        if (buildTime.isNegative() || buildTime.isZero() || buildTime.getNano() != 0) {
            throw new IllegalArgumentException("The prototype fixture requires a positive whole-second build time");
        }
        start(fixture, completedMines, buildTime.toSeconds());
        simulation.progressService().startBuilding(Building.IRON_MINE, 1.0);
    }

    private void start(MineFixture fixture, long completedMines, long buildTimeSeconds) {
        if (!fixture.startTime().isZero()) {
            throw new IllegalArgumentException("The prototype fixture starts at game time zero");
        }
        storageCapacity = fixture.storageCapacity();
        BuildingMsg mine = new BuildingMsg(Building.IRON_MINE.toString(), buildTimeSeconds, ResourceSetMsg.createDefault(),
                ironOnly(fixture.ironPerHour()));
        long initialNumerator = fixture.retainedIron().multiply(REMAINDER_DENOMINATOR).longValueExact();
        Map<Building, Long> completedBuildings = completedMines == 0
                ? Map.of() : Map.of(Building.IRON_MINE, completedMines);
        simulation = InMemoryPlanetSimulation.create(TickCoordinator.DEFAULT_TICK_DURATION, building -> mine,
                completedBuildings, ironOnly(fixture.availableIron()).planetResources(),
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

    @Override
    public MineStatus readMineStatus(long mineNumber) {
        long completed = simulation.buildingService().getBuildings().counters().getOrDefault(Building.IRON_MINE, 0L);
        if (mineNumber <= completed) {
            return MineStatus.OPERATIONAL;
        }
        if (mineNumber == completed + 1 && simulation.progressService().hasBuildingInProgress()) {
            return MineStatus.UNDER_CONSTRUCTION;
        }
        throw new IllegalStateException("No test mine with number " + mineNumber);
    }

    private static ResourceSetMsg ironOnly(long iron) {
        return new ResourceSetMsg(new PlanetResourceSetMsg(iron, 0, 0), 0);
    }
}
