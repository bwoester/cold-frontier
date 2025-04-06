package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;

public enum Building {

    IRON_MINE(new BuildingMsg("",
            2L,
            new ResourceSetMsg(new PlanetResourceSetMsg(1, 1, 1), 1),
            new ResourceSetMsg(new PlanetResourceSetMsg(1, -1, 0), 0)
    )),
    ;

    private final BuildingMsg data;

    Building(BuildingMsg data) {
        this.data = new BuildingMsg(toString(),
                data.ticksToBuild(),
                data.cost(),
                data.production()
        );
    }

    public BuildingMsg getData() {
        return data;
    }
}
