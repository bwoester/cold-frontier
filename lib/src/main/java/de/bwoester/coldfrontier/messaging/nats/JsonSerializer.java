package de.bwoester.coldfrontier.messaging.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * JSON-based implementation of EventSerializer using Jackson's ObjectMapper.
 *
 * @param <T> The type of object to serialize/deserialize
 */
@RequiredArgsConstructor
public class JsonSerializer<T> implements EventSerializer<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> targetClass;

    @Override
    public byte[] serialize(T message) throws IOException {
        return objectMapper.writeValueAsBytes(message);
    }

    @Override
    public T deserialize(byte[] data) throws IOException {
        return objectMapper.readValue(data, targetClass);
    }
}
