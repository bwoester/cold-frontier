package de.bwoester.coldfrontier.data.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bwoester.coldfrontier.data.EventFactory;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.ValueFactory;
import io.nats.client.KeyValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NatsValueFactory implements ValueFactory {

    private final KeyValue kv;
    private final EventFactory eventFactory;
    private final ObjectMapper objectMapper;

    public <T> Value<T> create(Class<T> clazz, String key) {
        EventSerializer<T> eventSerializer = getEventSerializer(clazz);
        return new NatsValue<>(kv, key, eventSerializer, eventFactory);
    }

    private <T> EventSerializer<T> getEventSerializer(Class<T> clazz) {
        return new JsonEventSerializer<>(objectMapper, clazz);
    }

}
