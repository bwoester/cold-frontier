package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;

import java.util.HashMap;
import java.util.Map;

public class StaticBuildingDataProvider implements BuildingDataProvider {

    private final Map<Building, BuildingMsg> data = new HashMap<>();

    {
        data.put(Building.IRON_MINE, new BuildingMsg(Building.IRON_MINE.toString(), 2L,
                new ResourceSetMsg(new PlanetResourceSetMsg(1, 1, 1), 1),
                new ResourceSetMsg(new PlanetResourceSetMsg(1, -1, 0), 0)
        ));
    }

    @Override
    public BuildingMsg getData(Building building) {
        return data.get(building);
    }
}
