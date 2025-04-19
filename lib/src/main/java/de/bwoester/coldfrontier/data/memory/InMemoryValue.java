package de.bwoester.coldfrontier.data.memory;

import de.bwoester.coldfrontier.data.*;
import io.nats.client.JetStreamApiException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class InMemoryValue<T> implements Value<T> {

    private final List<Entry<Event<?>>> data;
    private final String key;
    private final Class<T> payloadClass;
    private final EventFactory eventFactory;

    @Override
    public boolean isPresent() {
        for (int i = data.size() - 1; i >= 0; i--) {
            Entry<Event<?>> dataEntry = data.get(i);
            Event<?> event = dataEntry.event();
            Object payload = event.payload();
            if (payloadClass.isInstance(payload) && key.equals(dataEntry.key())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T get() {
        for (int i = data.size() - 1; i >= 0; i--) {
            Entry<Event<?>> dataEntry = data.get(i);
            Event<?> event = dataEntry.event();
            Object payload = event.payload();
            if (payloadClass.isInstance(payload) && key.equals(dataEntry.key())) {
                return payloadClass.cast(payload);
            }
        }
        return null;
    }

    @Override
    public void set(T value) {
        Event<T> event = eventFactory.createEvent(value);
        data.add(new Entry<>(key, event));
    }

    @Override
    public void delete() {
        Event<T> event = eventFactory.createEvent(null);
        data.add(new Entry<>(key, event));
    }

    @Override
    public ValueWatchSubscription watch(ValueWatcher<T> watcher) throws JetStreamApiException, IOException, InterruptedException {
        // TODO
        return null;
    }
}
