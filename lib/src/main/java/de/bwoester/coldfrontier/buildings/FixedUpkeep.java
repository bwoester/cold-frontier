package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.resources.ResourceAmounts;
import de.bwoester.coldfrontier.resources.ResourceType;
import org.apache.commons.math3.fraction.BigFraction;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

/**
 * Calculates fixed upkeep for one building's payment terms. Effective times are exact
 * fractions of a game second.
 *
 * <p>The renewal timestamps in {@link State} are a working representation. With a fixed rate,
 * each is the last successful payment time for that resource plus the time one unit covers.
 * The colony can derive these timestamps from its saved state when advancing game time.
 */
public final class FixedUpkeep {
    private final Map<ResourceType, BigFraction> coverageByResource;

    public FixedUpkeep(ResourceAmounts ratesPerHour) {
        var coverage = new EnumMap<ResourceType, BigFraction>(ResourceType.class);
        ratesPerHour.amounts().forEach((resource, rate) -> coverage.put(resource, new BigFraction(3600, rate)));
        coverageByResource = Map.copyOf(coverage);
    }

    /** Settles initial upkeep at construction completion using stock eligible at that time. */
    public Settlement complete(BigFraction effectiveTime, ResourceAmounts eligibleStock) {
        var schedule = new EnumMap<ResourceType, BigFraction>(ResourceType.class);
        coverageByResource.keySet().forEach(resource -> schedule.put(resource, effectiveTime));
        return settle(schedule, effectiveTime, eligibleStock);
    }

    /** Settles only the next renewal using stock eligible at its effective time. */
    public Settlement renew(State state, ResourceAmounts eligibleStock) {
        BigFraction effectiveTime = state.nextRenewal().orElseThrow(
                () -> new IllegalStateException("No fixed upkeep renewal is due"));
        return settle(state.renewalTimes(), effectiveTime, eligibleStock);
    }

    private Settlement settle(Map<ResourceType, BigFraction> schedule, BigFraction effectiveTime,
                              ResourceAmounts eligibleStock) {
        var dueUnits = new EnumMap<ResourceType, Long>(ResourceType.class);
        var nextSchedule = new EnumMap<ResourceType, BigFraction>(ResourceType.class);
        schedule.forEach((resource, time) -> {
            if (time.equals(effectiveTime)) {
                dueUnits.put(resource, 1L);
                nextSchedule.put(resource, time.add(coverageByResource.get(resource)));
            } else {
                nextSchedule.put(resource, time);
            }
        });

        var due = new ResourceAmounts(dueUnits);
        var remainingStock = eligibleStock.withdraw(due);
        if (remainingStock.isEmpty()) {
            return new Settlement(effectiveTime, due, false, eligibleStock, new State(false, Map.of()));
        }
        return new Settlement(effectiveTime, due, true, remainingStock.orElseThrow(), new State(true, nextSchedule));
    }

    public record State(boolean operational, Map<ResourceType, BigFraction> renewalTimes) {
        public State {
            renewalTimes = Map.copyOf(renewalTimes);
        }

        public Optional<BigFraction> nextRenewal() {
            return renewalTimes.values().stream().min(Comparator.naturalOrder());
        }
    }

    /** The payment or shortfall and the stock and building state that result from it. */
    public record Settlement(BigFraction effectiveTime, ResourceAmounts due, boolean paid,
                             ResourceAmounts stock, State state) {
    }
}
