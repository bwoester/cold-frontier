package de.bwoester.coldfrontier.progress;

/**
 * Represents a progress message for tracking the completion status of various operations.
 * This is a sealed interface with specific implementations for different types of progress.
 */
public sealed interface ProgressMsg permits CreateBuildingProgressMsg {

    /**
     * Gets the current progress value of the operation.
     * 
     * @return a double value representing the progress, typically in the range of 0.0 (not started) to 1.0 (completed)
     */
    double progress();

    /**
     * Indicates whether this completed progress has been consumed.
     * Only relevant for progress values >= 1.0
     * 
     * @return true if the completed progress has been consumed
     */
    boolean consumed();

}
