package de.bwoester.coldfrontier.data.nats;

import de.bwoester.coldfrontier.data.Event;

import java.io.IOException;

public interface EventSerializer<T> {
    byte[] serialize(Event<T> message) throws IOException;
    Event<T> deserialize(byte[] data) throws IOException;
}
