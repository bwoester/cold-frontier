package de.bwoester.coldfrontier.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Handles input for one player.
 */
public class InputService {

    private final Queue<InputMsg> queue = new LinkedList<>();
    private final Collection<InputMsg> input = new ArrayList<>();
    private final Supplier<Long> tickSupplier;

    public InputService(Supplier<Long> tickSupplier) {
        this.tickSupplier = tickSupplier;
    }

    // not sure if needed, if input is available at a certain stage, handle it
    // if it's not handled, it'll be done in the next tick
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
     * TODO get all available input?
     *  Split into NEW, STARTED, DONE (separate _SUCCESS/ _FAIL?), EXCEPTION
     * TODO report inputs:
     *  NEW but not picked up by the end of next tick
     *  STARTED but not finished within the same tick
     *  EXCEPTION
     *
     * @return all InputMsg to be processed
     */
    public Collection<InputMsg> getInput() {
        return input;
    }

    public <T extends InputMsg> Collection<T> getInput(Class<T> tClass, Predicate<T> predicate) {
        return input.stream()
                .filter(tClass::isInstance)
                .map(tClass::cast)
                .filter(predicate)
                .toList();
    }

    public void add(InputMsg inputMsg) {
        queue.add(inputMsg);
    }

    public boolean removeAll(Collection<InputMsg> inputMsgs) {
        return queue.removeAll(inputMsgs);
    }
}
