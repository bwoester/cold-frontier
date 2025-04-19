package de.bwoester.coldfrontier.data.memory;

import de.bwoester.coldfrontier.data.Event;
import de.bwoester.coldfrontier.data.EventFactory;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.ValueFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class InMemoryValueFactory implements ValueFactory {

    private final List<Entry<Event<?>>> data = new ArrayList<>();
    private final EventFactory eventFactory;

    public <T> Value<T> create(Class<T> clazz, String key) {
        return new InMemoryValue<>(data, key, clazz, eventFactory);
    }

    public List<Entry<Event<?>>> getData() {
        return Collections.unmodifiableList(data);
    }

    public String prettyPrint() {
        // return GameEventPrinter.prettyPrint(events);
        // TODO
        return "";
    }

}
