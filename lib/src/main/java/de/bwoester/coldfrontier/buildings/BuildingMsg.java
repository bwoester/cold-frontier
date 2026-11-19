package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.accounting.ResourceSetMsg;

/** Construction time and net production use game seconds. Negative rates consume resources. */
public record BuildingMsg(
        String id,
        long buildTimeSeconds,
        ResourceSetMsg costs,
        ResourceSetMsg productionPerSecond
) {
}
