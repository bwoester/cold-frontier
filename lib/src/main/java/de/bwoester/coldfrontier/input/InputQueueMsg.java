package de.bwoester.coldfrontier.input;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public record InputQueueMsg(Queue<InputMsg> msgQueue) {

    public InputQueueMsg add(InputMsg entry) {
        Queue<InputMsg> newEntries = new LinkedList<>(msgQueue);
        newEntries.add(entry);
        return new InputQueueMsg(newEntries);
    }

    public InputQueueMsg poll(Consumer<InputMsg> polledEntryConsumer) {
        if (msgQueue.isEmpty()) {
            return this;
        }
        Queue<InputMsg> newEntries = new LinkedList<>(msgQueue);
        polledEntryConsumer.accept(newEntries.poll());
        return new InputQueueMsg(newEntries);
    }
}
