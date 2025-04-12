package de.bwoester.coldfrontier;

import de.bwoester.coldfrontier.messaging.GameEventFactory;
import de.bwoester.coldfrontier.messaging.InMemoryGameEventLog;

public class EventLogStub {

    public long tick = 0;
    public GameEventFactory gameEventFactory = new GameEventFactory(() -> tick);
    public InMemoryGameEventLog inMemoryGameEventLog = new InMemoryGameEventLog(gameEventFactory);

}
