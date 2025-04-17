package de.bwoester.coldfrontier.messaging;

import java.util.ArrayList;
import java.util.List;

/**
 * A filtered view of a {@link InMemoryGameEventLog} that only returns events with
 * a specific payload type and subject. This class acts as a type-safe and subject-scoped
 * view over the underlying event log.
 * <p>
 * Events are filtered by:
 * <ul>
 *   <li>Payload type - must be instances of class {@code T}</li>
 *   <li>Subject - must match the specified subject string</li>
 * </ul>
 * <p>
 * When retrieving events, this class performs runtime type checking and casting.
 * When adding events, it automatically wraps the payload in a {@link GameEvent} with
 * the appropriate subject using the provided {@link GameEventFactory}.
 *
 * @param <T> the type of payload contained in the events this view handles
 */
public class EventView<T> implements GameEventLog<T> {

    /**
     * The underlying event log containing all events
     */
    private final InMemoryGameEventLog log;
    
    /**
     * Factory for creating new game events
     */
    private final GameEventFactory gameEventFactory;
    
    /**
     * The class object representing the type of payload this view handles
     */
    private final Class<T> clazz;
    
    /**
     * The subject string used to filter events
     */
    private final String subject;

    /**
     * Creates a new event view over the specified event log.
     *
     * @param log the underlying event log containing all events
     * @param gameEventFactory factory for creating new game events
     * @param clazz the class representing the payload type this view handles
     * @param subject the subject string used to filter events
     */
    public EventView(InMemoryGameEventLog log,
                     GameEventFactory gameEventFactory,
                     Class<T> clazz,
                     String subject) {
        this.log = log;
        this.gameEventFactory = gameEventFactory;
        this.clazz = clazz;
        this.subject = subject;
    }

    /**
     * Checks if there are any events matching this view's criteria in the underlying log.
     *
     * @return {@code true} if no matching events exist, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        List<GameEvent<?>> all = log.getAll();
        for (int i = all.size() - 1; i >= 0; i--) {
            GameEvent<?> event = all.get(i);
            Object payload = event.payload();
            if (clazz.isInstance(payload) && subject.equals(event.subject())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Retrieves the most recent event payload matching this view's criteria.
     * Searches through the event log in reverse order (newest to oldest)
     * and returns the first matching payload.
     *
     * @return the payload of the most recent matching event, or {@code null} if no matching events exist
     * // TODO define: null or exception?
     */
    @Override
    public T getLatest() {
        List<GameEvent<?>> all = log.getAll();
        for (int i = all.size() - 1; i >= 0; i--) {
            GameEvent<?> event = all.get(i);
            Object payload = event.payload();
            if (clazz.isInstance(payload) && subject.equals(event.subject())) {
                return clazz.cast(payload);
            }
        }
        // TODO define: null or exception?
        return null;
        //throw new IllegalStateException("No event found");
    }
    
    /**
     * Adds a new event with the specified payload to the underlying log.
     * The event is automatically created with this view's subject using the provided factory.
     *
     * @param payload the payload to add
     */
    @Override
    public void add(T payload) {
        log.add(gameEventFactory.create(subject, payload));
    }
    
    /**
     * Retrieves all event payloads matching this view's criteria.
     * Filters the events by payload type and subject, and returns a list
     * of all matching payloads in their original order (oldest to newest).
     *
     * @return a list containing all matching event payloads
     */
    @Override
    public List<T> getAll() {
        List<T> result = new ArrayList<>();
        for (GameEvent<?> event : log.getAll()) {
            Object payload = event.payload();
            if (clazz.isInstance(payload) && subject.equals(event.subject())) {
                result.add(clazz.cast(payload));
            }
        }
        return result;
    }

}
