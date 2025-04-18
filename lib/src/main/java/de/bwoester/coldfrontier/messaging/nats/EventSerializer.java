package de.bwoester.coldfrontier.messaging.nats;

import java.io.IOException;

public interface EventSerializer<T> {
    byte[] serialize(T message) throws IOException;
    T deserialize(byte[] data) throws IOException;
}
