package de.bwoester.coldfrontier.messaging;

import java.util.ArrayList;
import java.util.List;

// Filtered view
public class EventView<T> implements GameEventLog<T> {

    private final InMemoryGameEventLog log;
    private final GameEventFactory gameEventFactory;
    private final Class<T> clazz;
    private final String subject;

    public EventView(InMemoryGameEventLog log,
                     GameEventFactory gameEventFactory,
                     Class<T> clazz,
                     String subject) {
        this.log = log;
        this.gameEventFactory = gameEventFactory;
        this.clazz = clazz;
        this.subject = subject;
    }

    @Override
    public boolean isEmpty() {
        List<GameEvent<?>> all = log.getAll();
        for (int i = all.size() - 1; i >= 0; i--) {
            GameEvent<?> event = all.get(i);
            Object payload = event.payload();
            if (clazz.isInstance(payload) && subject.equals(event.subject())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T getLatest() {
        List<GameEvent<?>> all = log.getAll();
        for (int i = all.size() - 1; i >= 0; i--) {
            GameEvent<?> event = all.get(i);
            Object payload = event.payload();
            if (clazz.isInstance(payload) && subject.equals(event.subject())) {
                return clazz.cast(payload);
            }
        }
        throw new IllegalStateException("No event found");
    }

    @Override
    public void add(T payload) {
        log.add(gameEventFactory.create(subject, payload));
    }

    @Override
    public List<T> getAll() {
        List<T> result = new ArrayList<>();
        for (GameEvent<?> event : log.getAll()) {
            Object payload = event.payload();
            if (clazz.isInstance(payload) && subject.equals(event.subject())) {
                result.add(clazz.cast(payload));
            }
        }
        return result;
    }

}
