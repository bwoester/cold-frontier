package de.bwoester.coldfrontier.messaging.nats;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.bwoester.coldfrontier.messaging.Event;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * JSON-based serializer implementation for Event objects, handling generic type information.
 *
 * @param <T> The type of payload within the Event
 */
@RequiredArgsConstructor
public class JsonEventSerializer<T> implements EventSerializer<Event<T>> {

    private final ObjectMapper objectMapper;
    private final Class<?> eventClass;
    private final Class<T> payloadClass;

    @Override
    public byte[] serialize(Event<T> message) throws IOException {
        return objectMapper.writeValueAsBytes(message);
    }

    @Override
    public Event<T> deserialize(byte[] data) throws IOException {
        JavaType type = objectMapper.getTypeFactory().constructParametricType(eventClass, payloadClass);
        return objectMapper.readValue(data, type);
    }
}
