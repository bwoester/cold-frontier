package de.bwoester.coldfrontier.progress;

import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingDataProvider;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import de.bwoester.coldfrontier.data.Value;

import java.util.Optional;

/**
 * Tracks the progress of buildings on a planet.
 * <p>
 * This service maintains state information about buildings that are currently under construction
 * and provides methods to start construction, update construction progress, and retrieve completed
 * buildings. Progress information is stored in a game event log as progress messages.
 * </p>
 * <p>
 * The service ensures that building construction follows these rules:
 * <ul>
 *   <li>Only one building can be in progress at a time</li>
 *   <li>Construction progress increments based on the building's defined construction time</li>
 *   <li>Completed buildings must be retrieved before starting new construction</li>
 * </ul>
 * </p>
 */
public class ProgressService {

    /**
     * Event log that stores progress messages to track building construction state.
     */
    private final Value<ProgressMsg> progressLog;

    /**
     * Provider that supplies building data, including construction time information.
     */
    private final BuildingDataProvider buildingDataProvider;

    /**
     * Creates a new progress service for tracking building construction.
     *
     * @param progressLog          The event log where progress messages will be stored
     * @param buildingDataProvider Provider of building-specific data needed for calculations
     */
    public ProgressService(Value<ProgressMsg> progressLog, BuildingDataProvider buildingDataProvider) {
        this.progressLog = progressLog;
        this.buildingDataProvider = buildingDataProvider;
    }

    /**
     * Starts the construction of a new building.
     * <p>
     * Initializes a new construction progress entry in the event log with 0% progress.
     * </p>
     *
     * @param building              The building to start constructing
     * @param timeToBuildMultiplier A multiplier that affects how long the building takes to construct.
     *                              Values greater than 1.0 increase construction time, values less than
     *                              1.0 decrease construction time.
     */
    public void startBuilding(Building building, double timeToBuildMultiplier) {
        // Create a new progress entry with 0% progress
        CreateBuildingProgressMsg progressMsg = new CreateBuildingProgressMsg(building, timeToBuildMultiplier, 0.0);
        progressLog.set(progressMsg);
    }

    /**
     * Checks if there is currently a building under construction.
     *
     * @return {@code true} if a building is currently under construction (progress < 100%),
     * {@code false} otherwise
     */
    public boolean hasBuildingInProgress() {
        ProgressMsg latest = progressLog.get();
        // Check if there's a latest progress message that is not completed
        return latest != null && latest.progress() < 1.0;
    }

    /**
     * Increments the construction progress of the current building.
     * <p>
     * This method should be called once per game tick to update construction progress.
     * The progress increment is calculated based on the building's defined construction time
     * and any time multiplier that was set when construction started.
     * </p>
     * <p>
     * If there is no building in progress, or if the current building is completed and consumed,
     * this method does nothing.
     * </p>
     */
    public void increaseProgress() {
        ProgressMsg latest = progressLog.get();

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

            // Get the building data to determine how long it takes to build
            BuildingMsg buildingData = buildingDataProvider.getData(building);

            // Calculate the total number of ticks required
            long totalTicksRequired = Math.round(buildingData.ticksToBuild() * timeToBuildMultiplier);

            // Extract the completed ticks from the current progress
            // Current progress is a fraction: completedTicks / totalTicksRequired
            double currentProgress = buildingProgress.progress();
            long completedTicks = Math.round(currentProgress * totalTicksRequired);

            // Increment the completed ticks by 1
            completedTicks++;

            // Calculate new progress value as a fraction, capping at 1.0 (100%)
            double newProgress = Math.min(1.0, (double) completedTicks / totalTicksRequired);

            // Create and add new progress message (still not consumed)
            CreateBuildingProgressMsg newProgressMsg = new CreateBuildingProgressMsg(
                    building, timeToBuildMultiplier, newProgress, false);
            progressLog.set(newProgressMsg);
        }
    }

    /**
     * Retrieves and marks as consumed any completed building.
     * <p>
     * This method checks if there is a building that has completed construction (100% progress)
     * but has not yet been consumed. If such a building exists, it is marked as consumed
     * in the event log and returned.
     * </p>
     * <p>
     * This method should be called regularly to retrieve completed buildings before
     * starting new construction.
     * </p>
     *
     * @return An {@link Optional} containing the completed building if one exists and hasn't been
     * consumed yet, or an empty Optional if no completed building is available
     */
    public Optional<Building> pollCompletedBuilding() {
        ProgressMsg latest = progressLog.get();

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
            progressLog.set(consumedMsg);

            // Return the completed building
            return Optional.of(buildingProgress.building());
        }

        return Optional.empty();
    }
}
