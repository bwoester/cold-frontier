package de.bwoester.coldfrontier.production;

import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;
import de.bwoester.coldfrontier.buildings.BuildingDataProvider;
import de.bwoester.coldfrontier.buildings.BuildingMsg;

import java.util.Map;

/**
 * Calculates the production of a planet.
 */
public class ProductionService {

    private final BuildingDataProvider buildingDataProvider;

    public ProductionService(BuildingDataProvider buildingDataProvider) {
        this.buildingDataProvider = buildingDataProvider;
    }

    public ResourceSetMsg calculateProduction(BuildingCountersMsg buildingCounters) {
        ResourceSetMsg production = ResourceSetMsg.createDefault();
        for (Map.Entry<Building, Long> counter : buildingCounters.counters().entrySet()) {
            Building building = counter.getKey();
            // TODO take planet modifiers into account
            // TODO take player profile modifiers into account
            // TODO take energy loss into account
            // TODO take source material shortage into account
            BuildingMsg data = this.buildingDataProvider.getData(building);
            ResourceSetMsg oneBuildingProd = data.production();
            ResourceSetMsg allBuildingsProd = oneBuildingProd.multiply(counter.getValue());
            production = production.add(allBuildingsProd);
        }
        return production;
    }

}
