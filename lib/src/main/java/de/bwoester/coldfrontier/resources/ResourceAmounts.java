package de.bwoester.coldfrontier.resources;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

/** Immutable, nonnegative whole amounts. An absent resource has an amount of zero. */
public record ResourceAmounts(Map<ResourceType, Long> amounts) {

    public ResourceAmounts {
        var entries = new EnumMap<ResourceType, Long>(ResourceType.class);
        amounts.forEach((type, amount) -> {
            if (amount < 0) {
                throw new IllegalArgumentException("Negative amount for " + type);
            }
            if (amount != 0) {
                entries.put(type, amount);
            }
        });
        amounts = Map.copyOf(entries);
    }

    public long amountOf(ResourceType type) {
        return amounts.getOrDefault(type, 0L);
    }

    /** Returns the updated stock only if every input can be withdrawn. */
    public Optional<ResourceAmounts> withdraw(ResourceAmounts inputs) {
        if (inputs.amounts.entrySet().stream().anyMatch(entry -> amountOf(entry.getKey()) < entry.getValue())) {
            return Optional.empty();
        }
        var stock = copyAmounts();
        inputs.amounts.forEach((type, amount) -> stock.put(type, amountOf(type) - amount));
        return Optional.of(new ResourceAmounts(stock));
    }

    /** Credits production, discarding excess for types that have a storage limit. */
    public ResourceAmounts credit(ResourceAmounts output, Map<ResourceType, Long> storageLimits) {
        var stock = copyAmounts();
        output.amounts.forEach((type, amount) -> {
            long current = amountOf(type);
            Long limit = storageLimits.get(type);
            long accepted = limit == null ? amount : Math.min(amount, Math.max(0, limit - current));
            stock.put(type, Math.addExact(current, accepted));
        });
        return new ResourceAmounts(stock);
    }

    private EnumMap<ResourceType, Long> copyAmounts() {
        var copy = new EnumMap<ResourceType, Long>(ResourceType.class);
        copy.putAll(amounts);
        return copy;
    }
}
