package de.bwoester.coldfrontier.messaging.memory;

import de.bwoester.coldfrontier.messaging.Event;
import de.bwoester.coldfrontier.messaging.EventFactory;
import de.bwoester.coldfrontier.messaging.EventLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryEventLog implements EventLog<Event<?>> {

    private final List<Event<?>> events = new ArrayList<>();
    private final EventFactory eventFactory;

    public InMemoryEventLog(EventFactory eventFactory) {
        this.eventFactory = eventFactory;
    }

    public <T> EventLog<T> viewOfType(Class<T> clazz, String subject) {
        return new EventView<>(this, eventFactory, clazz, subject);
    }

    @Override
    public boolean isEmpty() {
        return events.isEmpty();
    }

    @Override
    public Event<?> getLatest() {
        return events.getLast();
    }

    @Override
    public void add(Event<?> event) {
        events.add(event);
    }

    @Override
    public List<Event<?>> getAll() {
        return Collections.unmodifiableList(events);
    }

    public String prettyPrint() {
        return GameEventPrinter.prettyPrint(events);
    }
}
