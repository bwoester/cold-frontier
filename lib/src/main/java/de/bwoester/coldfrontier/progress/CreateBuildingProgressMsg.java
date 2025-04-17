package de.bwoester.coldfrontier.progress;

import de.bwoester.coldfrontier.buildings.Building;

public record CreateBuildingProgressMsg(
    Building building, 
    double timeToBuildMultiplier, 
    double progress, 
    boolean consumed
) implements ProgressMsg {
    
    /**
     * Creates a new building progress message with unconsumed status.
     * 
     * @param building the building being constructed
     * @param timeToBuildMultiplier multiplier for build time
     * @param progress current progress value (0.0 to 1.0)
     */
    public CreateBuildingProgressMsg(Building building, double timeToBuildMultiplier, double progress) {
        this(building, timeToBuildMultiplier, progress, false);
    }
}
