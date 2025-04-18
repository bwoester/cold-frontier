package de.bwoester.coldfrontier.data.nats;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bwoester.coldfrontier.data.Event;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * JSON-based serializer implementation for Event objects, handling generic type information.
 *
 * @param <T> The type of payload within the Event
 */
@RequiredArgsConstructor
public class JsonEventSerializer<T> implements EventSerializer<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> payloadClass;

    @Override
    public byte[] serialize(Event<T> message) throws IOException {
        return objectMapper.writeValueAsBytes(message);
    }

    @Override
    public Event<T> deserialize(byte[] data) throws IOException {
        JavaType type = objectMapper.getTypeFactory().constructParametricType(Event.class, payloadClass);
        return objectMapper.readValue(data, type);
    }
}
