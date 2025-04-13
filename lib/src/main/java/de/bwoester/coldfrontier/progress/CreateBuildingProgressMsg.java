package de.bwoester.coldfrontier.progress;

import de.bwoester.coldfrontier.buildings.Building;

public record CreateBuildingProgressMsg(Building building, double timeToBuildMultiplier, float progress) implements ProgressMsg {
}
