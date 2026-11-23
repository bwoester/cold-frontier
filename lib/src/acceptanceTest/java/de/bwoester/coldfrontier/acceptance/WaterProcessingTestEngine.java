package de.bwoester.coldfrontier.acceptance;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;
import java.util.OptionalDouble;

public interface WaterProcessingTestEngine {
    void initialize(WaterFixture fixture);

    void advanceTo(Duration gameTime);

    WaterState readState();

    record WaterFixture(long ice, long energy, long water, long waterStorage,
                        long waterPerHour, long icePerUnit, long energyPerUnit,
                        Optional<PowerPlantFixture> powerPlant) {
    }

    record PowerPlantFixture(long energyPerHour, BigDecimal retainedEnergy) {
    }

    record WaterState(long ice, long energy, long water, OptionalDouble progressFraction) {
        public boolean unitInProgress() {
            return progressFraction.isPresent();
        }
    }
}
