package de.bwoester.coldfrontier.buildings;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public record ConstructionQueueMsg(Queue<ConstructionQueueEntryMsg> queuedBuildings) {

    public ConstructionQueueMsg add(ConstructionQueueEntryMsg entry) {
        Queue<ConstructionQueueEntryMsg> newEntries = new LinkedList<>(queuedBuildings);
        newEntries.add(entry);
        return new ConstructionQueueMsg(newEntries);
    }

    public ConstructionQueueMsg poll(Consumer<ConstructionQueueEntryMsg> polledEntryConsumer) {
        if (queuedBuildings.isEmpty()) {
            return this;
        }
        Queue<ConstructionQueueEntryMsg> newEntries = new LinkedList<>(queuedBuildings);
        polledEntryConsumer.accept(newEntries.poll());
        return new ConstructionQueueMsg(newEntries);
    }
}
