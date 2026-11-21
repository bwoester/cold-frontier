package de.bwoester.coldfrontier.acceptance;

import java.math.BigDecimal;
import java.time.Duration;

public interface ExtractionTestEngine {
    void startWithCompletedMine(MineFixture fixture);

    /** Starts with construction in progress, its costs already paid, and no further construction consumption. */
    void startWithMineUnderConstruction(MineFixture fixture, Duration completionTime);

    void advanceTo(Duration gameTime);

    IronState readIron();

    MineStatus readMineStatus();

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
