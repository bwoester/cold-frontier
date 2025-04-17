package de.bwoester.coldfrontier.progress;

import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingDataProvider;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import de.bwoester.coldfrontier.messaging.GameEventLog;

import java.util.Optional;

/**
 * Tracks the progress of buildings on a planet.
 */
public class ProgressService {

    private final GameEventLog<ProgressMsg> progressLog;
    private final BuildingDataProvider buildingDataProvider;

    public ProgressService(GameEventLog<ProgressMsg> progressLog, BuildingDataProvider buildingDataProvider) {
        this.progressLog = progressLog;
        this.buildingDataProvider = buildingDataProvider;
    }

    public void startBuilding(Building building, double timeToBuildMultiplier) {
        // Create a new progress entry with 0% progress
        CreateBuildingProgressMsg progressMsg = new CreateBuildingProgressMsg(building, timeToBuildMultiplier, 0.0);
        progressLog.add(progressMsg);
    }

    public boolean hasBuildingInProgress() {
        ProgressMsg latest = progressLog.getLatest();
        // Check if there's a latest progress message that is not completed
        return latest != null && latest.progress() < 1.0;
    }

    public void increaseProgress() {
        ProgressMsg latest = progressLog.getLatest();
        
        // If there's no progress or it's already completed and consumed, do nothing
        if (latest == null || (latest.progress() >= 1.0 && latest.consumed())) {
            return;
        }

        if (latest instanceof CreateBuildingProgressMsg buildingProgress) {
            // If progress is complete but not consumed, don't update further
            if (buildingProgress.progress() >= 1.0) {
                return;
            }
            
            Building building = buildingProgress.building();
            double timeToBuildMultiplier = buildingProgress.timeToBuildMultiplier();
            double currentProgress = buildingProgress.progress();
            
            // Get the building data to determine how long it takes to build
            BuildingMsg buildingData = buildingDataProvider.getData(building);
            
            // Calculate progress increment for this tick
            double progressIncrement = 1.0 / (buildingData.ticksToBuild() * timeToBuildMultiplier);
            
            // Calculate new progress value, capping at 1.0 (100%)
            double newProgress = Math.min(1.0, currentProgress + progressIncrement);
            
            // Create and add new progress message (still not consumed)
            CreateBuildingProgressMsg newProgressMsg = new CreateBuildingProgressMsg(
                    building, timeToBuildMultiplier, newProgress, false);
            progressLog.add(newProgressMsg);
        }
    }

    public Optional<Building> pollCompletedBuilding() {
        ProgressMsg latest = progressLog.getLatest();
        
        // If there's no latest message, return empty
        if (latest == null) {
            return Optional.empty();
        }
        
        // If the latest message represents a completed building that hasn't been consumed yet
        if (latest.progress() >= 1.0 && !latest.consumed() && latest instanceof CreateBuildingProgressMsg buildingProgress) {
            // Mark the building as consumed in the event log
            CreateBuildingProgressMsg consumedMsg = new CreateBuildingProgressMsg(
                    buildingProgress.building(),
                    buildingProgress.timeToBuildMultiplier(),
                    buildingProgress.progress(),
                    true // Mark as consumed
            );
            progressLog.add(consumedMsg);
            
            // Return the completed building
            return Optional.of(buildingProgress.building());
        }
        
        return Optional.empty();
    }
}
