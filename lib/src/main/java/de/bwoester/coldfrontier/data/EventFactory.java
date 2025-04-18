package de.bwoester.coldfrontier.data;

import java.util.function.Supplier;

public class EventFactory {

    private final Supplier<Long> tickSupplier;

    public EventFactory(Supplier<Long> tickSupplier) {
        this.tickSupplier = tickSupplier;
    }

    public <T> Event<T> createEvent(T payload) {
        return new Event<>(tickSupplier.get(), payload);
    }

}
