package de.bwoester.coldfrontier.acceptance.prototype;

import de.bwoester.coldfrontier.acceptance.FixedUpkeepTestEngine;
import de.bwoester.coldfrontier.buildings.FixedUpkeep;
import de.bwoester.coldfrontier.resources.ResourceAmounts;
import de.bwoester.coldfrontier.resources.ResourceType;
import org.apache.commons.math3.fraction.BigFraction;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;

/** Drives one building with an isolated stock through its scheduled payment times. */
public final class FixedUpkeepAdapter implements FixedUpkeepTestEngine {
    // The scenario leaves the choice of fuel open. Use an existing resource for this fixture.
    private static final ResourceType FUEL = ResourceType.CHEMICAL_ELEMENTS;

    private final Map<BigFraction, FixedUpkeep.Settlement> audit = new HashMap<>();
    private FixedUpkeep upkeep;
    private BigFraction completionTime;
    private FixedUpkeep.State state;
    private ResourceAmounts stock;

    @Override
    public void initialize(Duration completionTime, long fuelPerHour, long energyPerHour, long fuel, long energy) {
        this.completionTime = seconds(completionTime);
        upkeep = new FixedUpkeep(new ResourceAmounts(Map.of(FUEL, fuelPerHour, ENERGY, energyPerHour)));
        stock = new ResourceAmounts(Map.of(FUEL, fuel, ENERGY, energy));
        state = null;
        audit.clear();
    }

    @Override
    public void advanceTo(Duration gameTime) {
        BigFraction target = seconds(gameTime);
        if (state == null) {
            if (completionTime.compareTo(target) > 0) {
                return;
            }
            apply(upkeep.complete(completionTime, stock));
        }
        while (state.nextRenewal().filter(time -> time.compareTo(target) <= 0).isPresent()) {
            apply(upkeep.renew(state, stock));
        }
    }

    private void apply(FixedUpkeep.Settlement settlement) {
        stock = settlement.stock();
        state = settlement.state();
        audit.put(settlement.effectiveTime(), settlement);
    }

    @Override
    public BuildingStatus readStatus() {
        if (state == null) {
            return BuildingStatus.UNDER_CONSTRUCTION;
        }
        return state.operational() ? BuildingStatus.OPERATIONAL : BuildingStatus.UNDERSUPPLIED;
    }

    @Override
    public Stock readStock() {
        return new Stock(stock.amountOf(FUEL), stock.amountOf(ENERGY));
    }

    @Override
    public Optional<Payment> readPayment(Duration gameTime) {
        return Optional.ofNullable(audit.get(seconds(gameTime)))
                .map(entry -> new Payment(entry.due().amountOf(FUEL), entry.due().amountOf(ENERGY), entry.paid()));
    }

    private static BigFraction seconds(Duration duration) {
        return new BigFraction(duration.getSeconds()).add(new BigFraction(duration.getNano(), 1_000_000_000));
    }
}
