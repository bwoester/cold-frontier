package de.bwoester.coldfrontier.data.nats;

import de.bwoester.coldfrontier.data.*;
import io.nats.client.JetStreamApiException;
import io.nats.client.KeyValue;
import io.nats.client.api.KeyValueEntry;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class NatsValue<T> implements Value<T> {

    private final KeyValue kv;
    private final String key;
    private final EventSerializer<T> serializer;
    private final EventFactory eventFactory;

    @Override
    public boolean isPresent() {
        try {
            return kv.get(key) != null;
        } catch (IOException | JetStreamApiException e) {
            return false;
        }
    }

    @Override
    public T get() {
        try {
            KeyValueEntry keyValueEntry = kv.get(key);
            return keyValueEntry != null
                    ? serializer.deserialize(keyValueEntry.getValue()).payload()
                    : null;
        } catch (IOException | JetStreamApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void set(T value) {
        try {
            Event<T> event = eventFactory.createEvent(value);
            kv.put(key, serializer.serialize(event));
        } catch (IOException | JetStreamApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete() {
        try {
            kv.delete(key);
        } catch (IOException | JetStreamApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ValueWatchSubscription watch(ValueWatcher<T> watcher) throws JetStreamApiException, IOException, InterruptedException {
        NatsValueWatcher<T> natsValueWatcher = new NatsValueWatcher<>(watcher, serializer, eventFactory);
        return new NatsValueWatchSubscription(kv.watch(key, natsValueWatcher));
    }
}
