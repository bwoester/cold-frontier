package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.accounting.ResourceSetMsg;

public record BuildingMsg(
        String id,
        long ticksToBuild,
        ResourceSetMsg cost,
        ResourceSetMsg production
) {
}
