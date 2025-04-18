package de.bwoester.coldfrontier.messaging.nats;

import de.bwoester.coldfrontier.messaging.Event;
import de.bwoester.coldfrontier.messaging.EventLog;
import de.bwoester.coldfrontier.messaging.EventLogProvider;
import io.nats.client.Connection;
import io.nats.client.JetStream;
import io.nats.client.JetStreamApiException;
import io.nats.client.JetStreamManagement;
import io.nats.client.api.StorageType;
import io.nats.client.api.StreamConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of EventLogProvider that uses NATS JetStream for event storage and retrieval.
 */
@Slf4j
@RequiredArgsConstructor
public class NatsEventLogProvider implements EventLogProvider {

    private static final String GLOBAL_STREAM_NAME = "global-events";
    private static final String GLOBAL_SUBJECT = "events.>";
    
    private final Connection natsConnection;
    private final EventSerializer<Event<?>> globalEventSerializer;
    private final Map<String, EventLog<?>> viewCache = new HashMap<>();
    
    private EventLog<Event<?>> globalEventLog;
    
    /**
     * Initializes the JetStream storage for events.
     * This method should be called once after creating the provider.
     */
    public void initialize() throws IOException, JetStreamApiException {
        JetStreamManagement jsm = natsConnection.jetStreamManagement();
        
        // Create global stream if it doesn't exist
        try {
            jsm.getStreamInfo(GLOBAL_STREAM_NAME);
            log.info("Global event stream already exists: {}", GLOBAL_STREAM_NAME);
        } catch (JetStreamApiException e) {
            if (e.getErrorCode() == 404) {
                StreamConfiguration streamConfig = StreamConfiguration.builder()
                        .name(GLOBAL_STREAM_NAME)
                        .subjects(GLOBAL_SUBJECT)
                        .storageType(StorageType.File)
                        .replicas(1)
                        .maxAge(Duration.ofDays(30))
                        .build();
                
                jsm.addStream(streamConfig);
                log.info("Created global event stream: {}", GLOBAL_STREAM_NAME);
            } else {
                throw e;
            }
        }
    }

    /**
     * Returns the global event log containing all events regardless of subject or payload type.
     *
     * @return An EventLog containing all events in the system
     */
    @Override
    public EventLog<Event<?>> getGlobalEventLog() {
        if (globalEventLog == null) {
            try {
                JetStream jetStream = natsConnection.jetStream();
                JetStreamManagement jsManagement = natsConnection.jetStreamManagement();
                globalEventLog = new NatsEventLog<>(
                        jetStream,
                        jsManagement,
                        GLOBAL_SUBJECT,
                        globalEventSerializer,
                        GLOBAL_STREAM_NAME
                );
            } catch (IOException e) {
                log.error("Failed to create global event log", e);
                throw new RuntimeException("Failed to create global event log", e);
            }
        }
        
        return globalEventLog;
    }

    /**
     * Creates a view of the event log for a specific subject and payload type.
     * Uses the subject-specific filtering capabilities of NatsEventLog.
     *
     * @param subject The subject identifier to filter events by
     * @param clazz   The class representing the expected payload type
     * @param <T>     The type of event payload to be included in the view
     * @return An EventLog containing only events matching the specified subject and payload type
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> EventLog<T> getView(String subject, Class<T> clazz) {
        String cacheKey = subject + "_" + clazz.getName();
        
        if (viewCache.containsKey(cacheKey)) {
            return (EventLog<T>) viewCache.get(cacheKey);
        }
        
        try {
            JetStream jetStream = natsConnection.jetStream();
            JetStreamManagement jsManagement = natsConnection.jetStreamManagement();
            
            // Create view-specific subject pattern
            String viewSubject = "events." + subject;
            
            // Create a NatsEventLog directly with the appropriate serializer
            EventLog<T> eventLog = new NatsEventLog<>(
                jetStream,
                jsManagement,
                viewSubject,
                EventSerializerFactory.createSerializer(clazz),
                GLOBAL_STREAM_NAME
            );
            
            viewCache.put(cacheKey, eventLog);
            return eventLog;
        } catch (IOException e) {
            log.error("Failed to create view for subject: {} and class: {}", subject, clazz.getName(), e);
            throw new RuntimeException("Failed to create event log view", e);
        }
    }
}
