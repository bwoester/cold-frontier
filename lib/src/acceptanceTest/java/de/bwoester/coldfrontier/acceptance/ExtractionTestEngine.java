package de.bwoester.coldfrontier.acceptance;

import java.math.BigDecimal;
import java.time.Duration;

public interface ExtractionTestEngine {
    void startWithCompletedMine(CompletedMine fixture);

    void advanceTo(Duration gameTime);

    IronState readIron();

    record CompletedMine(Duration startTime, long availableIron, BigDecimal retainedIron,
                         long ironPerHour, long storageCapacity) {
    }

    record IronState(Duration gameTime, long available, BigDecimal retained) {
    }
}
