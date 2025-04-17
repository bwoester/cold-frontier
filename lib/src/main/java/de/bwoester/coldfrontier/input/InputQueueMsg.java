package de.bwoester.coldfrontier.input;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public record InputQueueMsg(Queue<InputMsg> queuedInput) {

    public InputQueueMsg add(InputMsg entry) {
        Queue<InputMsg> newEntries = new LinkedList<>(queuedInput);
        newEntries.add(entry);
        return new InputQueueMsg(newEntries);
    }

    public InputQueueMsg poll(Consumer<InputMsg> polledEntryConsumer) {
        if (queuedInput.isEmpty()) {
            return this;
        }
        Queue<InputMsg> newEntries = new LinkedList<>(queuedInput);
        polledEntryConsumer.accept(newEntries.poll());
        return new InputQueueMsg(newEntries);
    }
}
