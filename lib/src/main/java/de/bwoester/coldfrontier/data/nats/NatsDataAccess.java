package de.bwoester.coldfrontier.data.nats;

import de.bwoester.coldfrontier.data.DataAccess;
import de.bwoester.coldfrontier.data.ValueEntry;
import de.bwoester.coldfrontier.data.ValueRepository;
import io.nats.client.*;
import io.nats.client.api.KeyValueConfiguration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class NatsDataAccess implements DataAccess {

    private static final String GAME_EVENTS_BUCKET = ValueRepository.GAME_EVENTS_BUCKET;

    private final String natsEndpoint;

    private Connection nc;

    @Getter
    private KeyValueManagement kvm;

    @Getter
    private KeyValue kv;

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
        if (bucketNames.contains(GAME_EVENTS_BUCKET)) {
            log.info("Bucket '{}' already exists.", GAME_EVENTS_BUCKET);
        } else {
            // 2. Create only if it doesn't exist
            KeyValueConfiguration kvConfig = KeyValueConfiguration.builder()
                    .name(GAME_EVENTS_BUCKET)
                    .maxHistoryPerKey(10)
                    .build();

            kvm.create(kvConfig);
            log.info("Bucket '{}' created.", GAME_EVENTS_BUCKET);
        }

        // 3. Get the KV context
        kv = nc.keyValue(GAME_EVENTS_BUCKET);
    }

    @Override
    public List<ValueEntry<?>> getMostRecentEntries() {
        return List.of();
    }

    @Override
    public List<ValueEntry<?>> getMostRecentEntries(int maxEntries) {
        return List.of();
    }
}
