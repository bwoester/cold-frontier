package de.bwoester.coldfrontier.messaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryGameEventLog implements GameEventLog<GameEvent<?>> {

    private final List<GameEvent<?>> events = new ArrayList<>();
    private final GameEventFactory gameEventFactory;

    public InMemoryGameEventLog(GameEventFactory gameEventFactory) {
        this.gameEventFactory = gameEventFactory;
    }

    public <T> GameEventLog<T> viewOfType(Class<T> clazz, String subject) {
        return new EventView<>(this, gameEventFactory, clazz, subject);
    }

    @Override
    public boolean isEmpty() {
        return events.isEmpty();
    }

    @Override
    public GameEvent<?> getLatest() {
        return events.getLast();
    }

    @Override
    public void add(GameEvent<?> event) {
        events.add(event);
    }

    @Override
    public List<GameEvent<?>> getAll() {
        return Collections.unmodifiableList(events);
    }

    public String prettyPrint() {
        return GameEventPrinter.prettyPrint(events);
    }
}
