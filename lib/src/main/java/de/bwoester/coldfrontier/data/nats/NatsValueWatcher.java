package de.bwoester.coldfrontier.data.nats;

import de.bwoester.coldfrontier.data.*;
import io.nats.client.api.KeyValueEntry;
import io.nats.client.api.KeyValueWatcher;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class NatsValueWatcher<T> implements KeyValueWatcher {

    private final ValueWatcher<T> valueWatcher;
    private final EventSerializer<T> serializer;
    private final EventFactory eventFactory;

    @Override
    public void watch(KeyValueEntry keyValueEntry) {
        byte[] serializedValue = keyValueEntry.getValue();
        try {
            Event<T> event = serializedValue == null
                    ? eventFactory.createEvent(null)
                    : serializer.deserialize(serializedValue);
            BucketAndKey bucketAndKey = new BucketAndKey(keyValueEntry.getBucket(), keyValueEntry.getKey());
            valueWatcher.watch(new ValueEntry<>(bucketAndKey, event));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endOfData() {
        valueWatcher.endOfData();
    }
}
