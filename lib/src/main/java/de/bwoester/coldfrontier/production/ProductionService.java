package de.bwoester.coldfrontier.production;

import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;
import de.bwoester.coldfrontier.buildings.BuildingDataProvider;
import de.bwoester.coldfrontier.buildings.BuildingMsg;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Map;

/**
 * Calculates the production of a planet.
 */
public class ProductionService {

    private static final BigInteger NANOS_PER_HOUR = BigInteger.valueOf(Duration.ofHours(1).toNanos());
    private static final BigInteger NANOS_PER_SECOND = BigInteger.valueOf(Duration.ofSeconds(1).toNanos());

    private final BigInteger nanosPerTick;
    private final BuildingDataProvider buildingDataProvider;

    /**
     * Creates a production calculator for a fixed tick duration in game time.
     *
     * <p>A {@link Duration} exposes whole seconds and a nanosecond adjustment. Combining
     * them as {@code seconds * 1_000_000_000 + nanos} gives the exact tick length in
     * nanoseconds. Using {@link BigInteger} keeps the conversion exact even if the
     * total exceeds the range of a {@code long}.
     */
    public ProductionService(BuildingDataProvider buildingDataProvider, Duration tickDuration) {
        if (tickDuration.isZero() || tickDuration.isNegative()) {
            throw new IllegalArgumentException("Tick duration must be positive");
        }
        this.buildingDataProvider = buildingDataProvider;
        nanosPerTick = BigInteger.valueOf(tickDuration.getSeconds()).multiply(NANOS_PER_SECOND)
                .add(BigInteger.valueOf(tickDuration.getNano()));
    }

    /** Calculates production and consumption for exactly one configured tick. */
    public ProductionStep calculateTickProduction(BuildingCountersMsg buildingCounters,
                                                   ResourceSetMsg previousRemainder) {
        ResourceSetMsg rate = calculateProductionPerHour(buildingCounters);
        PlanetResourceSetMsg planetaryRate = rate.planetResources();
        PlanetResourceSetMsg planetaryRemainder = previousRemainder.planetResources();

        ResourceDelta iron = ResourceDelta.calculate(planetaryRate.iron(), nanosPerTick, planetaryRemainder.iron());
        ResourceDelta energy = ResourceDelta.calculate(planetaryRate.energy(), nanosPerTick, planetaryRemainder.energy());
        ResourceDelta population = ResourceDelta.calculate(planetaryRate.population(), nanosPerTick, planetaryRemainder.population());
        ResourceDelta credits = ResourceDelta.calculate(rate.credits(), nanosPerTick, previousRemainder.credits());

        ResourceSetMsg wholeUnits = new ResourceSetMsg(
                new PlanetResourceSetMsg(iron.wholeUnits(), energy.wholeUnits(), population.wholeUnits()),
                credits.wholeUnits());
        ResourceSetMsg remainder = new ResourceSetMsg(
                new PlanetResourceSetMsg(iron.remainderNumerator(), energy.remainderNumerator(), population.remainderNumerator()),
                credits.remainderNumerator());
        return new ProductionStep(wholeUnits, remainder);
    }

    /** Returns the combined net production rate in resource units per game hour. */
    public ResourceSetMsg calculateProductionPerHour(BuildingCountersMsg buildingCounters) {
        ResourceSetMsg production = ResourceSetMsg.createDefault();
        for (Map.Entry<Building, Long> counter : buildingCounters.counters().entrySet()) {
            Building building = counter.getKey();
            // TODO take planet modifiers into account
            // TODO take player profile modifiers into account
            // TODO take energy loss into account
            // TODO take source material shortage into account
            BuildingMsg data = this.buildingDataProvider.getData(building);
            ResourceSetMsg oneBuildingProd = data.productionPerHour();
            ResourceSetMsg allBuildingsProd = oneBuildingProd.multiply(counter.getValue());
            production = production.add(allBuildingsProd);
        }
        return production;
    }

    /**
     * Whole units to book this tick and the signed fractional units to carry forward,
     * calculated independently for each resource and for credits.
     *
     * <p>The rate is in units per game hour. Multiplying it by the tick length in
     * nanoseconds gives a numerator over 3,600,000,000,000, the number of nanoseconds
     * in one hour. Add the previous numerator, then divide by this common denominator.
     * The quotient is the whole-unit change and the remainder is the next numerator.
     *
     * <p>For example, at 3,600 iron per hour and a 250 ms tick, one tick adds
     * {@code 3_600 * 250_000_000 = 900_000_000_000} to the iron numerator.
     * That is a quarter unit. Starting from zero, the fourth tick books one iron
     * and leaves a zero remainder. A consumption rate is negative and uses the
     * same calculation.
     */
    public record ProductionStep(ResourceSetMsg wholeUnits, ResourceSetMsg remainder) {
    }

    private record ResourceDelta(long wholeUnits, long remainderNumerator) {

        static ResourceDelta calculate(long unitsPerHour, BigInteger nanosPerTick, long previousNumerator) {
            BigInteger total = BigInteger.valueOf(unitsPerHour).multiply(nanosPerTick)
                    .add(BigInteger.valueOf(previousNumerator));
            BigInteger[] parts = total.divideAndRemainder(NANOS_PER_HOUR);
            return new ResourceDelta(parts[0].longValueExact(), parts[1].longValueExact());
        }
    }

}
