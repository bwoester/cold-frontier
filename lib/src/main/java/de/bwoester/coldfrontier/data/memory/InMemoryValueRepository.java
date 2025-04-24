package de.bwoester.coldfrontier.data.memory;

import de.bwoester.coldfrontier.data.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InMemoryValueRepository extends AbstractValueRepository {

    private final List<ValueEntry<?>> data;
    private final EventFactory eventFactory;

    @Override
    protected <T> Value<T> createValue(Class<T> clazz, String key) {
        return new InMemoryValue<>(data, new BucketAndKey(GAME_EVENTS_BUCKET, key), clazz, eventFactory);
    }
}
