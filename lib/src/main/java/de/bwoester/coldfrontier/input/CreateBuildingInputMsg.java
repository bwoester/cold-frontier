package de.bwoester.coldfrontier.input;

import de.bwoester.coldfrontier.buildings.Building;

import java.util.UUID;

public record CreateBuildingInputMsg(long inputTick, UUID inputUuid, Building building) implements InputMsg {

}
