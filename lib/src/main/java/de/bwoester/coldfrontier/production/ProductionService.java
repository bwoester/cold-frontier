package de.bwoester.coldfrontier.production;

import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;
import de.bwoester.coldfrontier.buildings.BuildingDataProvider;
import de.bwoester.coldfrontier.buildings.BuildingMsg;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

/**
 * Calculates the production of a planet.
 */
public class ProductionService {

    private final BuildingDataProvider buildingDataProvider;

    public ProductionService(BuildingDataProvider buildingDataProvider) {
        this.buildingDataProvider = buildingDataProvider;
    }

    public ProductionStep calculateProduction(BuildingCountersMsg buildingCounters, Duration elapsed,
                                               ResourceSetMsg previousRemainder) {
        ResourceSetMsg rate = calculateProduction(buildingCounters);
        BigDecimal seconds = BigDecimal.valueOf(elapsed.getSeconds()).add(BigDecimal.valueOf(elapsed.getNano(), 9));
        PlanetResourceSetMsg planetaryRate = rate.planetResources();
        PlanetResourceSetMsg planetaryRemainder = previousRemainder.planetResources();

        ResourceDelta iron = ResourceDelta.calculate(planetaryRate.iron(), seconds, planetaryRemainder.iron());
        ResourceDelta energy = ResourceDelta.calculate(planetaryRate.energy(), seconds, planetaryRemainder.energy());
        ResourceDelta population = ResourceDelta.calculate(planetaryRate.population(), seconds, planetaryRemainder.population());
        ResourceDelta credits = ResourceDelta.calculate(rate.credits(), seconds, previousRemainder.credits());

        ResourceSetMsg wholeUnits = new ResourceSetMsg(
                new PlanetResourceSetMsg(iron.wholeUnits(), energy.wholeUnits(), population.wholeUnits()),
                credits.wholeUnits());
        ResourceSetMsg remainder = new ResourceSetMsg(
                new PlanetResourceSetMsg(iron.billionths(), energy.billionths(), population.billionths()),
                credits.billionths());
        return new ProductionStep(wholeUnits, remainder);
    }

    /** Returns the combined net production rate in resource units per game second. */
    public ResourceSetMsg calculateProduction(BuildingCountersMsg buildingCounters) {
        ResourceSetMsg production = ResourceSetMsg.createDefault();
        for (Map.Entry<Building, Long> counter : buildingCounters.counters().entrySet()) {
            Building building = counter.getKey();
            // TODO take planet modifiers into account
            // TODO take player profile modifiers into account
            // TODO take energy loss into account
            // TODO take source material shortage into account
            BuildingMsg data = this.buildingDataProvider.getData(building);
            ResourceSetMsg oneBuildingProd = data.productionPerSecond();
            ResourceSetMsg allBuildingsProd = oneBuildingProd.multiply(counter.getValue());
            production = production.add(allBuildingsProd);
        }
        return production;
    }

    /** The signed remainder stores billionths of a resource unit for each resource. */
    public record ProductionStep(ResourceSetMsg wholeUnits, ResourceSetMsg remainder) {
    }

    private record ResourceDelta(long wholeUnits, long billionths) {

        static ResourceDelta calculate(long unitsPerSecond, BigDecimal seconds, long previousBillionths) {
            BigDecimal total = BigDecimal.valueOf(unitsPerSecond).multiply(seconds)
                    .add(BigDecimal.valueOf(previousBillionths, 9));
            BigDecimal[] parts = total.divideAndRemainder(BigDecimal.ONE);
            return new ResourceDelta(parts[0].longValueExact(), parts[1].movePointRight(9).longValueExact());
        }
    }

}
