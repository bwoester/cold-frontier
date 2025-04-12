package de.bwoester.coldfrontier.messaging;

public record GameEvent<T>(long seq, long tick, String subject, T payload) {
}
