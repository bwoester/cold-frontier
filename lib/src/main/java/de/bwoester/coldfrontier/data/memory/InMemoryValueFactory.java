package de.bwoester.coldfrontier.data.memory;

import de.bwoester.coldfrontier.data.*;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class InMemoryValueFactory implements ValueFactory {

    private final List<ValueEntry<?>> data;
    private final EventFactory eventFactory;

    @Override
    public <T> Value<T> create(Class<T> clazz, String key) {
        return new InMemoryValue<>(data, new BucketAndKey(GAME_EVENTS_BUCKET, key), clazz, eventFactory);
    }

    public List<ValueEntry<?>> getData() {
        return Collections.unmodifiableList(data);
    }

}
