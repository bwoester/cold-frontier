package de.bwoester.coldfrontier.data.memory;

import de.bwoester.coldfrontier.data.*;
import io.nats.client.JetStreamApiException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class InMemoryValue<T> implements Value<T> {

    private final List<ValueEntry<?>> data;
    private final BucketAndKey bucketAndKey;
    private final Class<T> payloadClass;
    private final EventFactory eventFactory;

    @Override
    public boolean isPresent() {
        for (int i = data.size() - 1; i >= 0; i--) {
            ValueEntry<?> entry = data.get(i);
            Event<?> event = entry.event();
            Object payload = event.payload();
            if (payloadClass.isInstance(payload) && bucketAndKey.equals(entry.bucketAndKey())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T get() {
        for (int i = data.size() - 1; i >= 0; i--) {
            ValueEntry<?> entry = data.get(i);
            Event<?> event = entry.event();
            Object payload = event.payload();
            if (payloadClass.isInstance(payload) && bucketAndKey.equals(entry.bucketAndKey())) {
                return payloadClass.cast(payload);
            }
        }
        return null;
    }

    @Override
    public void set(T value) {
        Event<T> event = eventFactory.createEvent(value);
        data.add(new ValueEntry<>(bucketAndKey, event));
    }

    @Override
    public void delete() {
        Event<T> event = eventFactory.createEvent(null);
        data.add(new ValueEntry<>(bucketAndKey, event));
    }

    @Override
    public ValueWatchSubscription watch(ValueWatcher<T> watcher) throws JetStreamApiException, IOException, InterruptedException {
        // TODO
        return null;
    }
}
