package de.bwoester.coldfrontier.messaging;

import java.util.function.Supplier;

/**
 * Factory class responsible for creating new event instances with consistent timing.
 * Uses a tick supplier to ensure all events created by the same factory instance
 * receive consistent timestamps.
 */
public class EventFactory {

    private final Supplier<Long> tickSupplier;

    /**
     * Creates a new event factory with the specified tick supplier.
     *
     * @param tickSupplier A supplier that provides the current tick (timestamp)
     *                    for generated events
     */
    public EventFactory(Supplier<Long> tickSupplier) {
        this.tickSupplier = tickSupplier;
    }

    /**
     * Creates a new event with the current tick, specified subject, and payload.
     *
     * @param subject The subject or category identifier for the event
     * @param payload The payload data to be carried by the event
     * @param <T> The type of the payload
     * @return A new Event instance
     */
    public <T> Event<T> create(String subject, T payload) {
        return new Event<>(tickSupplier.get(), subject, payload);
    }

}
