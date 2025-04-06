package de.bwoester.coldfrontier.progress;

public sealed interface ProgressMsg permits CreateBuildingProgressMsg {

    float progress();

}
