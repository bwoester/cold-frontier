package de.bwoester.coldfrontier.messaging.memory;

import de.bwoester.coldfrontier.messaging.Event;
import de.bwoester.coldfrontier.messaging.EventLog;
import de.bwoester.coldfrontier.messaging.EventLogProvider;

public class InMemoryEventLogProvider implements EventLogProvider {

    private final InMemoryEventLog inMemoryEventLog;

    public InMemoryEventLogProvider(InMemoryEventLog inMemoryEventLog) {
        this.inMemoryEventLog = inMemoryEventLog;
    }

    @Override
    public EventLog<Event<?>> getGlobalEventLog() {
        return inMemoryEventLog;
    }

    @Override
    public <T> EventLog<T> getView(String subject, Class<T> clazz) {
        return inMemoryEventLog.viewOfType(clazz, subject);
    }
}
