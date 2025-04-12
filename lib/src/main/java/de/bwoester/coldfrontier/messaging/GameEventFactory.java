package de.bwoester.coldfrontier.messaging;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class GameEventFactory {

    private final AtomicLong nextSeq = new AtomicLong();
    private final Supplier<Long> tickSupplier;

    public GameEventFactory(Supplier<Long> tickSupplier) {
        this.tickSupplier = tickSupplier;
    }

    public <T> GameEvent<T> create(String subject, T payload) {
        return new GameEvent<>(nextSeq.getAndIncrement(), tickSupplier.get(), subject, payload);
    }

}
