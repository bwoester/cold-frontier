package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.accounting.ResourceSetMsg;

/** Construction time uses game seconds. Net production rates use game hours, with negative rates for consumption. */
public record BuildingMsg(
        String id,
        long buildTimeSeconds,
        ResourceSetMsg costs,
        ResourceSetMsg productionPerHour
) {
}
