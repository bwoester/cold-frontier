package de.bwoester.coldfrontier.data.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bwoester.coldfrontier.data.AbstractValueRepository;
import de.bwoester.coldfrontier.data.EventFactory;
import de.bwoester.coldfrontier.data.Value;
import io.nats.client.KeyValue;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class NatsValueRepository extends AbstractValueRepository {

    private final KeyValue kv;
    private final EventFactory eventFactory;
    private final ObjectMapper objectMapper;
    
    // Cache for EventSerializer instances by class type
    private final Map<Class<?>, EventSerializer<?>> serializerCache = new ConcurrentHashMap<>();

    public NatsValueRepository(KeyValue kv, EventFactory eventFactory, ObjectMapper objectMapper) {
        this.kv = kv;
        this.eventFactory = eventFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    protected <T> Value<T> createValue(Class<T> clazz, String key) {
        EventSerializer<T> eventSerializer = getEventSerializer(clazz);
        return new NatsValue<>(kv, key, eventSerializer, eventFactory);
    }

    @SuppressWarnings("unchecked")
    private <T> EventSerializer<T> getEventSerializer(Class<T> clazz) {
        return (EventSerializer<T>) serializerCache.computeIfAbsent(clazz, c -> 
            new JsonEventSerializer<>(objectMapper, clazz)
        );
    }
}
