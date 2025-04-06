package de.bwoester.coldfrontier.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

public class InputService {

    private final Queue<InputMsg> queue = new LinkedList<>();
    private final Collection<InputMsg> input = new ArrayList<>();
    private final Supplier<Long> tickSupplier;

    public InputService(Supplier<Long> tickSupplier) {
        this.tickSupplier = tickSupplier;
    }

    public void tick() {
        long tick = tickSupplier.get();
        input.clear();
        input.addAll(queue.stream()
                .filter(i -> i.inputTick() < tick)
                .toList());
    }

    /**
     * Returns all InputMsg to be processed, that happened before the current
     * tick. Inputs happen at a certain tick and are to be processed during the
     * next tick.
     *
     * @return all InputMsg to be processed
     */
    public Collection<InputMsg> getInput() {
        return input;
    }

    public void add(InputMsg inputMsg) {
        queue.add(inputMsg);
    }

    public boolean removeAll(Collection<InputMsg> inputMsgs) {
        return queue.removeAll(inputMsgs);
    }
}
