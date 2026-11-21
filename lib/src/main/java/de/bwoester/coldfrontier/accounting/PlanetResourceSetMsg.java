package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.resources.ResourceAmounts;
import de.bwoester.coldfrontier.resources.ResourceType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.LongUnaryOperator;

import static de.bwoester.coldfrontier.resources.ResourceType.CREDITS;

/**
 * Keyed planetary accounting amounts. An absent resource has a value of zero.
 * The owning message determines whether the values are stock, signed changes,
 * net production rates, or fractional production remainders.
 *
 * <p>Domain operations such as checking recipe inputs use {@link ResourceAmounts}
 * for nonnegative whole quantities. Convert a validated stock balance explicitly.
 * Credits are carried separately by {@link ResourceSetMsg#credits()}.
 */
public record PlanetResourceSetMsg(Map<ResourceType, Long> amounts) {

    public PlanetResourceSetMsg {
        var entries = new EnumMap<ResourceType, Long>(ResourceType.class);
        amounts.forEach((type, amount) -> {
            if (type == CREDITS) {
                throw new IllegalArgumentException("Credits belong to the player account");
            }
            if (amount != 0) {
                entries.put(type, amount);
            }
        });
        amounts = Map.copyOf(entries);
    }

    static PlanetResourceSetMsg createDefault() {
        return new PlanetResourceSetMsg(Map.of());
    }

    public long amountOf(ResourceType type) {
        return amounts.getOrDefault(type, 0L);
    }

    public ResourceAmounts toResourceAmounts() {
        return new ResourceAmounts(amounts);
    }

    public static PlanetResourceSetMsg fromResourceAmounts(ResourceAmounts stock) {
        return new PlanetResourceSetMsg(stock.amounts());
    }

    boolean isNonnegative() {
        return amounts.values().stream().allMatch(amount -> amount >= 0);
    }

    public PlanetResourceSetMsg add(PlanetResourceSetMsg other) {
        return combine(other, 1);
    }

    public PlanetResourceSetMsg subtract(PlanetResourceSetMsg other) {
        return combine(other, -1);
    }

    public PlanetResourceSetMsg multiply(long factor) {
        return scale(amount -> amount * factor);
    }

    public PlanetResourceSetMsg multiply(double factor) {
        return scale(amount -> (long) (amount * factor));
    }

    private PlanetResourceSetMsg scale(LongUnaryOperator factor) {
        var scaled = new EnumMap<ResourceType, Long>(ResourceType.class);
        amounts.forEach((type, amount) -> scaled.put(type, factor.applyAsLong(amount)));
        return new PlanetResourceSetMsg(scaled);
    }

    private PlanetResourceSetMsg combine(PlanetResourceSetMsg other, long sign) {
        var combined = new EnumMap<ResourceType, Long>(ResourceType.class);
        combined.putAll(amounts);
        other.amounts.forEach((type, amount) -> combined.merge(type, sign * amount, Long::sum));
        return new PlanetResourceSetMsg(combined);
    }
}
