package de.bwoester.coldfrontier.input;

import de.bwoester.coldfrontier.resources.ResourceAmounts;

import java.util.UUID;

/** System input for resources that become available at the current game time. */
public record SupplyPlanetResourcesInputMsg(long inputTick, UUID inputUuid, String planetId,
                                             ResourceAmounts amounts) implements InputMsg {
}
