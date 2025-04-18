package de.bwoester.coldfrontier.messaging;

import java.io.IOException;

public interface EventSerializer<T> {
    byte[] serialize(T message) throws IOException;
    T deserialize(byte[] data) throws IOException;
}
