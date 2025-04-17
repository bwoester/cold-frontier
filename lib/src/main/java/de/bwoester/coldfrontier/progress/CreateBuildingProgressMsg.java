package de.bwoester.coldfrontier.progress;

import de.bwoester.coldfrontier.buildings.Building;

/**
 * Progress message for tracking building construction.
 * This record implements the {@link ProgressMsg} interface and provides information about 
 * a building's construction state, including the building reference, time modifiers, 
 * current progress, and whether the completed construction has been processed.
 * 
 * @param building the building being constructed
 * @param timeToBuildMultiplier a multiplier that affects the building construction time
 * @param progress the current construction progress (0.0 to 1.0 where 1.0 means complete)
 * @param consumed indicates if the completed construction has been processed
 */
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
