package de.bwoester.coldfrontier.acceptance;

import java.math.BigDecimal;
import java.time.Duration;

public interface ExtractionTestEngine {
    void startWithCompletedMines(MineFixture fixture, long completedMines);

    /**
     * Starts with completed mines and one additional mine under construction.
     * All mines use the fixture's rate. Construction costs have already been paid,
     * and completing construction consumes no additional resources.
     */
    void startWithMineUnderConstruction(MineFixture fixture, long completedMines, Duration completionTime);

    void advanceTo(Duration gameTime);

    IronState readIron();

    /** Reads a one-based mine number, with the mine under construction ordered after the initially completed mines. */
    MineStatus readMineStatus(long mineNumber);

    enum MineStatus {
        UNDER_CONSTRUCTION,
        OPERATIONAL
    }

    record MineFixture(Duration startTime, long availableIron, BigDecimal retainedIron,
                       long ironPerHour, long storageCapacity) {
    }

    record IronState(Duration gameTime, long available, BigDecimal retained) {
    }
}
