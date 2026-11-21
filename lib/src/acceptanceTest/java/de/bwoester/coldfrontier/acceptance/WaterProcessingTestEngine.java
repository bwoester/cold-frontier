package de.bwoester.coldfrontier.acceptance;

import java.time.Duration;

public interface WaterProcessingTestEngine {
    void initialize(WaterFixture fixture);

    void advanceTo(Duration gameTime);

    WaterState readState();

    record WaterFixture(long ice, long energy, long water, long waterStorage,
                        long waterPerHour, long icePerUnit, long energyPerUnit) {
    }

    record WaterState(long ice, long energy, long water, boolean unitInProgress) {
    }
}
