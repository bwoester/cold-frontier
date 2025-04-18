package de.bwoester.coldfrontier.messaging.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.bwoester.coldfrontier.messaging.Event;

/**
 * Factory for creating EventSerializer instances for different types of objects.
 */
public class EventSerializerFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Creates a JSON-based serializer for Event objects.
     *
     * @param <T> The type of the event payload
     * @return An EventSerializer capable of serializing/deserializing Event objects
     */
    public static <T> EventSerializer<Event<T>> createEventSerializer(Class<T> payloadClass) {
        return new JsonEventSerializer<>(objectMapper, Event.class, payloadClass);
    }

    /**
     * Creates a JSON-based serializer for generic objects.
     *
     * @param <T> The type of the object to serialize
     * @param clazz The class of the object to serialize
     * @return An EventSerializer capable of serializing/deserializing objects of the specified class
     */
    public static <T> EventSerializer<T> createSerializer(Class<T> clazz) {
        return new JsonSerializer<>(objectMapper, clazz);
    }
}
