package de.bwoester.coldfrontier.messaging;

import java.util.List;

/**
 * Represents a log of specific events.
 * Provides methods to access, add, and retrieve events.
 *
 * @param <T> The type of events stored in this log
 */
public interface EventLog<T> {

    /**
     * Checks if the event log contains any events.
     *
     * @return true if the log is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Gets the most recent event added to the log.
     *
     * @return the latest event, or null if the log is empty
     */
    T getLatest();

    /**
     * Adds a new event to the log.
     *
     * @param latest The event to add to the log
     */
    void add(T latest);

    /**
     * Returns all events currently in the log.
     *
     * @return A list containing all events in chronological order
     */
    List<T> getAll();
}
