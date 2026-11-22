package de.bwoester.coldfrontier.production.recipe;

import de.bwoester.coldfrontier.resources.ResourceAmounts;
import de.bwoester.coldfrontier.resources.ResourceType;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalLong;

/** One recipe operation over a prepared colony stock, advanced one tick at a time. */
public final class RecipeProcessing {
    private static final long WORK_PER_OPERATION = Duration.ofHours(1).toNanos();
    private static final BigInteger NANOS_PER_SECOND = BigInteger.valueOf(Duration.ofSeconds(1).toNanos());

    private final Recipe recipe;
    private final long operationsPerHour;
    private final Map<ResourceType, Long> storageLimits;

    public RecipeProcessing(Recipe recipe, long operationsPerHour,
                            Map<ResourceType, Long> storageLimits) {
        if (operationsPerHour < 0) {
            throw new IllegalArgumentException("Processing capacity must be nonnegative");
        }
        this.recipe = recipe;
        this.operationsPerHour = operationsPerHour;
        this.storageLimits = Map.copyOf(storageLimits);
        this.storageLimits.forEach((type, limit) -> {
            if (limit < 0) {
                throw new IllegalArgumentException("Invalid storage limit for " + type);
            }
        });
    }

    public State initialize(ResourceAmounts openingStock) {
        storageLimits.forEach((type, limit) -> {
            if (openingStock.amountOf(type) > limit) {
                throw new IllegalArgumentException("Initial stock exceeds storage limit for " + type);
            }
        });
        return startIfSupplied(openingStock);
    }

    public State advanceTick(State state, Duration tickDuration) {
        if (tickDuration.isNegative() || tickDuration.isZero()) {
            throw new IllegalArgumentException("Tick duration must be positive");
        }
        // getNano() is the fraction of a second, including the full duration for subsecond ticks.
        BigInteger nanos = BigInteger.valueOf(tickDuration.getSeconds()).multiply(NANOS_PER_SECOND)
                .add(BigInteger.valueOf(tickDuration.getNano()));
        BigInteger availableWork = nanos.multiply(BigInteger.valueOf(operationsPerHour));

        // Capacity × tick nanoseconds represents an exact fraction of the work for one operation.
        State next = state.completedWork().isPresent() ? state : startIfSupplied(state.stock());
        while (next.completedWork().isPresent() && availableWork.signum() > 0) {
            long completedWork = next.completedWork().orElseThrow();
            BigInteger remainingWork = BigInteger.valueOf(WORK_PER_OPERATION - completedWork);
            if (availableWork.compareTo(remainingWork) < 0) {
                next = new State(next.stock(), OptionalLong.of(completedWork + availableWork.longValueExact()));
                break;
            }
            availableWork = availableWork.subtract(remainingWork);
            next = startIfSupplied(next.stock().credit(recipe.outputs(), storageLimits));
        }
        return next;
    }

    private State startIfSupplied(ResourceAmounts stock) {
        if (operationsPerHour > 0) {
            var remainingStock = stock.withdraw(recipe.inputs());
            if (remainingStock.isPresent()) {
                return new State(remainingStock.orElseThrow(), OptionalLong.of(0));
            }
        }
        return new State(stock, OptionalLong.empty());
    }

    /** Completed work is empty when no operation is in progress, including when inputs are missing. */
    public record State(ResourceAmounts stock, OptionalLong completedWork) {
        /** Fraction of the current operation completed, or empty when no operation is active. */
        public OptionalDouble progressFraction() {
            if (completedWork.isEmpty()) {
                return OptionalDouble.empty();
            }
            return OptionalDouble.of((double) completedWork.getAsLong() / WORK_PER_OPERATION);
        }
    }
}
