package de.bwoester.coldfrontier;

import de.bwoester.coldfrontier.messaging.EventFactory;
import de.bwoester.coldfrontier.messaging.memory.InMemoryEventLog;

public class EventLogStub {

    public long tick = 0;
    public EventFactory eventFactory = new EventFactory(() -> tick);
    public InMemoryEventLog inMemoryGameEventLog = new InMemoryEventLog(eventFactory);

}
