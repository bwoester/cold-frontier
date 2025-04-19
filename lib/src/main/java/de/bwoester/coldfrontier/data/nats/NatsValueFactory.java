package de.bwoester.coldfrontier.data.nats;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bwoester.coldfrontier.data.EventFactory;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.ValueFactory;
import io.nats.client.*;
import io.nats.client.api.KeyValueConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class NatsValueFactory implements ValueFactory {

    private static final String GAME_EVENTS_BUCK = "EVENTS";

    private final String natsEndpoint;

    private final EventFactory eventFactory;
    private final ObjectMapper objectMapper;

    private Connection nc;
    private KeyValueManagement kvm;
    private KeyValue kv;

    public NatsValueFactory(String natsEndpoint, EventFactory eventFactory, ObjectMapper objectMapper) {
        this.natsEndpoint = natsEndpoint;
        this.eventFactory = eventFactory;
        this.objectMapper = objectMapper;
    }

    public <T> Value<T> create(Class<T> clazz, String key) {
        EventSerializer<T> eventSerializer = getEventSerializer(clazz);
        return new NatsValue<>(kv, key, eventSerializer, eventFactory);
    }

    private <T> EventSerializer<T> getEventSerializer(Class<T> clazz) {
        return new JsonEventSerializer<>(objectMapper, clazz);
    }

    public void init() {
        try {
            // 1. Connect to NATS
            connectToNats();

            // 2. Create or get a Key-Value bucket
            initKeyValueStore();
        } catch (InterruptedException e) {
            log.error("Interrupted while initializing NATS.", e);
            Thread.currentThread().interrupt();
        } catch (IOException | JetStreamApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void connectToNats() throws IOException, InterruptedException {
        Options options = new Options.Builder()
                .server(natsEndpoint)
                .build();
        nc = Nats.connect(options);
    }

    private void initKeyValueStore() throws IOException, JetStreamApiException {
        kvm = nc.keyValueManagement();

        // 1. Check existing bucket names
        List<String> bucketNames = kvm.getBucketNames();
        if (bucketNames.contains(GAME_EVENTS_BUCK)) {
            log.info("Bucket '{}' already exists.", GAME_EVENTS_BUCK);
        } else {
            // 2. Create only if it doesn't exist
            KeyValueConfiguration kvConfig = KeyValueConfiguration.builder()
                    .name(GAME_EVENTS_BUCK)
                    .maxHistoryPerKey(10)
                    .build();

            kvm.create(kvConfig);
            log.info("Bucket '{}' created.", GAME_EVENTS_BUCK);
        }

        // 3. Get the KV context
        kv = nc.keyValue(GAME_EVENTS_BUCK);
    }
}
