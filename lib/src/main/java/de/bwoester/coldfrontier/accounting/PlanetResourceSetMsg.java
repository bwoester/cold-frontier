package de.bwoester.coldfrontier.accounting;

import de.bwoester.coldfrontier.resources.ResourceAmounts;
import de.bwoester.coldfrontier.resources.ResourceType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.LongUnaryOperator;

import static de.bwoester.coldfrontier.resources.ResourceType.CREDITS;
import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;
import static de.bwoester.coldfrontier.resources.ResourceType.IRON;
import static de.bwoester.coldfrontier.resources.ResourceType.POPULATION;

/** Planetary amounts or signed changes. The original fields remain readable in stored messages. */
public record PlanetResourceSetMsg(long iron, long energy, long population,
                                   Map<ResourceType, Long> additionalResources) {

    public PlanetResourceSetMsg {
        var extras = new EnumMap<ResourceType, Long>(ResourceType.class);
        if (additionalResources != null) {
            additionalResources.forEach((type, amount) -> {
                if (type == IRON || type == ENERGY || type == POPULATION || type == CREDITS) {
                    throw new IllegalArgumentException("Invalid additional planetary resource: " + type);
                }
                if (amount != 0) {
                    extras.put(type, amount);
                }
            });
        }
        additionalResources = Map.copyOf(extras);
    }

    public PlanetResourceSetMsg(long iron, long energy, long population) {
        this(iron, energy, population, Map.of());
    }

    static PlanetResourceSetMsg createDefault() {
        return new PlanetResourceSetMsg(0, 0, 0);
    }

    static PlanetResourceSetMsg createOne() {
        return new PlanetResourceSetMsg(1, 1, 1);
    }

    public ResourceAmounts toResourceAmounts() {
        var amounts = new EnumMap<ResourceType, Long>(ResourceType.class);
        amounts.putAll(additionalResources);
        amounts.put(IRON, iron);
        amounts.put(ENERGY, energy);
        amounts.put(POPULATION, population);
        return new ResourceAmounts(amounts);
    }

    public static PlanetResourceSetMsg fromResourceAmounts(ResourceAmounts stock) {
        var extras = new EnumMap<ResourceType, Long>(ResourceType.class);
        extras.putAll(stock.amounts());
        extras.remove(IRON);
        extras.remove(ENERGY);
        extras.remove(POPULATION);
        return new PlanetResourceSetMsg(stock.amountOf(IRON), stock.amountOf(ENERGY), stock.amountOf(POPULATION), extras);
    }

    public boolean allGreaterOrEqual(int value) {
        if (iron < value || energy < value || population < value) {
            return false;
        }
        for (ResourceType type : ResourceType.values()) {
            if (type != IRON && type != ENERGY && type != POPULATION && type != CREDITS
                    && additionalResources.getOrDefault(type, 0L) < value) {
                return false;
            }
        }
        return true;
    }

    public PlanetResourceSetMsg add(PlanetResourceSetMsg other) {
        return new PlanetResourceSetMsg(iron + other.iron, energy + other.energy, population + other.population,
                combineAdditionalResources(other, 1));
    }

    public PlanetResourceSetMsg subtract(PlanetResourceSetMsg other) {
        return new PlanetResourceSetMsg(iron - other.iron, energy - other.energy, population - other.population,
                combineAdditionalResources(other, -1));
    }

    public PlanetResourceSetMsg multiply(long factor) {
        return scale(amount -> amount * factor);
    }

    public PlanetResourceSetMsg multiply(double factor) {
        return scale(amount -> (long) (amount * factor));
    }

    private PlanetResourceSetMsg scale(LongUnaryOperator factor) {
        var extras = new EnumMap<ResourceType, Long>(ResourceType.class);
        additionalResources.forEach((type, amount) -> extras.put(type, factor.applyAsLong(amount)));
        return new PlanetResourceSetMsg(factor.applyAsLong(iron), factor.applyAsLong(energy),
                factor.applyAsLong(population), extras);
    }

    private Map<ResourceType, Long> combineAdditionalResources(PlanetResourceSetMsg other, long sign) {
        var extras = new EnumMap<ResourceType, Long>(ResourceType.class);
        extras.putAll(additionalResources);
        other.additionalResources.forEach((type, amount) -> extras.merge(type, sign * amount, Long::sum));
        return extras;
    }
}
