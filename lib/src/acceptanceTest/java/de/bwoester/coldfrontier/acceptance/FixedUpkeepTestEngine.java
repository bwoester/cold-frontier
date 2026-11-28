package de.bwoester.coldfrontier.acceptance;

import java.time.Duration;
import java.util.Optional;

public interface FixedUpkeepTestEngine {
    void initialize(Duration completionTime, long fuelPerHour, long energyPerHour, long fuel, long energy);

    void advanceTo(Duration gameTime);

    BuildingStatus readStatus();

    Stock readStock();

    Optional<Payment> readPayment(Duration gameTime);

    enum BuildingStatus {
        UNDER_CONSTRUCTION,
        OPERATIONAL,
        UNDERSUPPLIED
    }

    record Stock(long fuel, long energy) {
    }

    record Payment(long fuelDue, long energyDue, boolean paid) {
    }
}
