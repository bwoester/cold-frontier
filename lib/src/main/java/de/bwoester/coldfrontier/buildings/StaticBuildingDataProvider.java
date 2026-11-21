package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;

import java.util.HashMap;
import java.util.Map;

import static de.bwoester.coldfrontier.resources.ResourceType.ENERGY;
import static de.bwoester.coldfrontier.resources.ResourceType.IRON;
import static de.bwoester.coldfrontier.resources.ResourceType.POPULATION;

public class StaticBuildingDataProvider implements BuildingDataProvider {

    private final Map<Building, BuildingMsg> data = new HashMap<>();

    {
        data.put(Building.IRON_MINE, new BuildingMsg(Building.IRON_MINE.toString(), 2L,
                new ResourceSetMsg(new PlanetResourceSetMsg(Map.of(IRON, 1L, ENERGY, 1L, POPULATION, 1L)), 1),
                new ResourceSetMsg(new PlanetResourceSetMsg(Map.of(IRON, 3600L, ENERGY, -3600L)), 0)
        ));
    }

    @Override
    public BuildingMsg getData(Building building) {
        return data.get(building);
    }
}
