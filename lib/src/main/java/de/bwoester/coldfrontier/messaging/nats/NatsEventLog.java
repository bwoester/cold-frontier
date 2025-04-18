package de.bwoester.coldfrontier.messaging.nats;

import de.bwoester.coldfrontier.messaging.EventLog;
import io.nats.client.*;
import io.nats.client.api.ConsumerConfiguration;
import io.nats.client.api.ConsumerInfo;
import io.nats.client.api.DeliverPolicy;
import io.nats.client.api.PublishAck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of EventLog using NATS JetStream for persistence.
 *
 * @param <T> The type of events stored in this log
 */
@Slf4j
@RequiredArgsConstructor
public class NatsEventLog<T> implements EventLog<T> {

    private final JetStream jetStream;
    private final JetStreamManagement jsManagement;
    private final String subject;
    private final EventSerializer<T> serializer;
    private final String streamName;
    private T latestEvent;

    /**
     * Checks if the event log contains any events for the specific subject.
     *
     * @return true if the log is empty for this subject, false otherwise
     */
    @Override
    public boolean isEmpty() {
        try {
            // Create a temporary consumer to count messages for this subject
            String consumerId = "count-" + UUID.randomUUID().toString().substring(0, 8);

            ConsumerConfiguration cc = ConsumerConfiguration.builder()
                    .durable(consumerId)
                    .filterSubject(subject)
                    .deliverPolicy(DeliverPolicy.All)
                    .build();

            ConsumerInfo info = jsManagement.addOrUpdateConsumer(streamName, cc);
            long msgCount = info.getNumPending();

            // Cleanup the temporary consumer
            try {
                jsManagement.deleteConsumer(streamName, consumerId);
            } catch (Exception e) {
                log.warn("Failed to delete temporary consumer {}", consumerId, e);
            }

            return msgCount == 0;
        } catch (IOException | JetStreamApiException e) {
            log.error("Failed to check if subject {} is empty", subject, e);
            return true;
        }
    }

    /**
     * Gets the most recent event added to the log for this subject.
     *
     * @return the latest event, or null if the log is empty
     */
    @Override
    public T getLatest() {
        if (latestEvent != null) {
            return latestEvent;
        }

        try {
            if (isEmpty()) {
                return null;
            }

            List<T> allEvents = getAll();
            if (!allEvents.isEmpty()) {
                latestEvent = allEvents.getLast();
                return latestEvent;
            }
            return null;
        } catch (Exception e) {
            log.error("Failed to get latest event for subject {}", subject, e);
            return null;
        }
    }

    /**
     * Adds a new event to the log for this subject.
     *
     * @param latest The event to add to the log
     */
    @Override
    public void add(T latest) {
        try {
            byte[] serialized = serializer.serialize(latest);
            PublishOptions options = PublishOptions.builder().build();

            CompletableFuture<PublishAck> future = jetStream.publishAsync(subject, serialized, options);
            future.thenAccept(ack -> {
                log.debug("Event published successfully to subject {}: {}", subject, ack.getStream());
                latestEvent = latest;
            }).exceptionally(ex -> {
                log.error("Failed to publish event to subject {}", subject, ex);
                return null;
            });
        } catch (IOException e) {
            log.error("Failed to serialize and add event to subject {}", subject, e);
        }
    }

    /**
     * Returns all events currently in the log for this subject.
     *
     * @return A list containing all events in chronological order
     */
    @Override
    public List<T> getAll() {
        List<T> events = new ArrayList<>();
        try {
            // First, check how many messages we expect
            String consumerId = "count-" + UUID.randomUUID().toString().substring(0, 8);

            ConsumerConfiguration cc = ConsumerConfiguration.builder()
                    .durable(consumerId)
                    .filterSubject(subject)
                    .deliverPolicy(DeliverPolicy.All)
                    .build();

            ConsumerInfo info = jsManagement.addOrUpdateConsumer(streamName, cc);
            long totalMsgCount = info.getNumPending();

            // Clean up the count consumer
            try {
                jsManagement.deleteConsumer(streamName, consumerId);
            } catch (Exception e) {
                log.warn("Failed to delete temporary count consumer {}", consumerId, e);
            }

            if (totalMsgCount == 0) {
                return events; // No messages to fetch
            }

            // Generate a unique consumer name for fetching messages
            String consumerName = "get-all-" + UUID.randomUUID().toString().substring(0, 8);

            // Subscribe to the specific subject using pull-based consumer
            io.nats.client.PullSubscribeOptions options = io.nats.client.PullSubscribeOptions.builder()
                    .durable(consumerName)
                    .configuration(ConsumerConfiguration.builder()
                        .deliverPolicy(DeliverPolicy.All)
                        .filterSubject(subject)
                        .build())
                    .build();
            
            JetStreamSubscription subscription = null;
            try {
                subscription = jetStream.subscribe(subject, options);
                
                // Define batch size and timeout
                final int batchSize = 100;
                final int timeoutMs = 2000;
            
                // Keep fetching batches until we have all messages or fail to get more
                boolean fetchComplete = false;
                int fetchAttempts = 0;
                final int maxFetchAttempts = 10; // Prevent infinite loops
            
                while (!fetchComplete && fetchAttempts < maxFetchAttempts) {
                    List<Message> batch = subscription.fetch(batchSize, timeoutMs);
                    fetchAttempts++;
            
                    if (batch.isEmpty()) {
                        // If we received an empty batch and already have messages or tried multiple times
                        if (!events.isEmpty() || fetchAttempts >= 3) {
                            fetchComplete = true;
                            log.debug("Completed fetching after receiving empty batch. Total events: {}", events.size());
                        } else {
                            log.debug("Received empty batch, retrying... (attempt {})", fetchAttempts);
                        }
                    } else {
                        // Process the batch of messages
                        for (Message message : batch) {
                            try {
                                T event = serializer.deserialize(message.getData());
                                events.add(event);
                                // Don't need to ack() since we're just reading historical data
                            } catch (Exception e) {
                                log.error("Failed to deserialize message from subject {}", subject, e);
                                // Don't need to nak() for historical reads
                            }
                        }
            
                        log.debug("Fetched batch of {} messages. Total so far: {}/{}",
                                batch.size(), events.size(), totalMsgCount);
            
                        // Check if we've received all expected messages
                        if (events.size() >= totalMsgCount) {
                            fetchComplete = true;
                            log.debug("Completed fetching all {} expected messages", totalMsgCount);
                        }
                    }
                }
            
                if (!fetchComplete) {
                    log.warn("Failed to fetch all messages after {} attempts. Got {}/{} expected messages",
                            maxFetchAttempts, events.size(), totalMsgCount);
                }
            } finally {
                // Clean up resources
                if (subscription != null) {
                    try {
                        subscription.unsubscribe();
                    } catch (Exception e) {
                        log.warn("Failed to unsubscribe subscription", e);
                    }
                }
                
                // Clean up the consumer after use
                try {
                    jsManagement.deleteConsumer(streamName, consumerName);
                } catch (Exception e) {
                    log.warn("Failed to delete consumer {}", consumerName, e);
                }
            }

            // Sort events by sequence if possible (NATS JetStream guarantees ordered delivery by default)

            if (!events.isEmpty()) {
                latestEvent = events.getLast();
            }

            return events;
        } catch (Exception e) {
            log.error("Failed to get all events for subject {}", subject, e);
            return events;
        }
    }
}
