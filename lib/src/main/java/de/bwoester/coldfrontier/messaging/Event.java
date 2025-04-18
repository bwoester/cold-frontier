package de.bwoester.coldfrontier.messaging;

public record Event<T>(long tick, String subject, T payload) {
}
