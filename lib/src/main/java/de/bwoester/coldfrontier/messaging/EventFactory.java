package de.bwoester.coldfrontier.messaging;

import java.util.function.Supplier;

public class EventFactory {

    private final Supplier<Long> tickSupplier;

    public EventFactory(Supplier<Long> tickSupplier) {
        this.tickSupplier = tickSupplier;
    }

    public <T> Event<T> create(String subject, T payload) {
        return new Event<>(tickSupplier.get(), subject, payload);
    }

}
