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

    /** Each signed remainder is a numerator with the number of nanoseconds per hour as denominator. */
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
