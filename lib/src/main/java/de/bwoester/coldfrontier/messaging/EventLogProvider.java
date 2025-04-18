package de.bwoester.coldfrontier.messaging;

/**
 * Provides access to event logs and allows creating filtered views of event data.
 */
public interface EventLogProvider {

    /**
     * Returns the global event log containing all events regardless of subject or payload type.
     * 
     * @return An EventLog containing all events in the system
     */
    EventLog<Event<?>> getGlobalEventLog();

    /**
     * Creates a filtered view of the event log for a specific subject and payload type.
     * 
     * @param subject The subject identifier to filter events by
     * @param clazz The class representing the expected payload type
     * @param <T> The type of event payload to be included in the view
     * @return An EventLog containing only events matching the specified subject and payload type
     */
    <T> EventLog<T> getView(String subject, Class<T> clazz);
    
}
