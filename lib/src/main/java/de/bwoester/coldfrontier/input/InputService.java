package de.bwoester.coldfrontier.input;

import de.bwoester.coldfrontier.data.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Handles input for one player.
 */
@Slf4j
public class InputService {

    private final Value<InputQueueMsg> newInput;
    private final Value<InputQueueMsg> startedInput;
    private final Value<InputQueueMsg> finishedInput;
    private final Value<InputQueueMsg> failedInput;

    public InputService(Value<InputQueueMsg> newInput,
                        Value<InputQueueMsg> startedInput,
                        Value<InputQueueMsg> finishedInput,
                        Value<InputQueueMsg> failedInput) {
        this.newInput = newInput;
        this.startedInput = startedInput;
        this.finishedInput = finishedInput;
        this.failedInput = failedInput;
    }

    /**
     * Adds a new input message into the NEW input queue.
     *
     * @param inputMsg the input message to handle (during the next input handling phase)
     */
    synchronized public void add(InputMsg inputMsg) {
        add(inputMsg, newInput);
    }

    /**
     * Moves matching {@link InputMsg}s from NEW to STARTED.
     *
     * @param tClass    the class of input messages to handle
     * @param predicate only handle input matching this predicate
     * @param <T>       the type of input messages to handle
     * @return the queue of input messages to handle
     */
    synchronized public <T extends InputMsg> Queue<T> startInputHandling(Class<T> tClass, Predicate<T> predicate) {
        Queue<T> matches = find(tClass, predicate, newInput);
        addAll(matches, startedInput);
        removeAll(matches, newInput);
        return matches;
    }

    /**
     * Moves the {@link InputMsg} from STARTED to FINISHED.
     *
     * @param inputMsg the successfully handled input msg
     */
    synchronized public void finishInputHandling(InputMsg inputMsg) {
        add(inputMsg, finishedInput);
        remove(inputMsg, startedInput);
    }

    /**
     * Moves the {@link InputMsg} from STARTED to FAILED.
     *
     * @param inputMsg the failed input msg
     * @param reason   the error that happened during input handling
     */
    synchronized public void failedInputHandling(InputMsg inputMsg, Throwable reason) {
        add(new FailedInputMsg(0, inputMsg, reason), failedInput);
        remove(inputMsg, startedInput);
    }

    /**
     * Verifies input handling has been properly completed.
     * There might be NEW inputs that arrived after we started this tick's input processing, this is okay.
     * But there must not be:
     * <ul>
     *     <li>NEW inputs from more than one tick ago -> we have a type of input that no component cares about</li>
     *     <li>STARTED inputs -> should have FINISHED or FAILED</li>
     *     <li>FAILED inputs -> log/ alert?</li>
     * </ul>
     * TODO: also clean up?
     *
     * @param tick the current game tick
     */
    synchronized public void verifyState(long tick) {
        // Check for NEW inputs from more than one tick ago
        if (newInput.isPresent()) {
            Queue<InputMsg> oldInputs = newInput.get().msgQueue().stream()
                    .filter(msg -> tick - msg.inputTick() > 1)
                    .collect(Collectors.toCollection(LinkedList::new));

            if (!oldInputs.isEmpty()) {
                log.error("Found {} unhandled input(s) from more than one tick ago!", oldInputs.size());
                for (InputMsg oldInput : oldInputs) {
                    log.error("Unhandled input: {}", oldInput);
                }
                // Clean up by removing old inputs
                removeAll(oldInputs, newInput);
            }
        }

        // Check for any remaining STARTED inputs
        if (startedInput.isPresent() && !startedInput.get().msgQueue().isEmpty()) {
            Queue<InputMsg> startedInputs = startedInput.get().msgQueue();
            log.error("Found {} input(s) that started but never finished or failed!", startedInputs.size());
            for (InputMsg startedInput : startedInputs) {
                log.error("Incomplete input: {}", startedInput);
            }
            // Clean up by re-setting the started inputs queue to empty
            startedInput.set(new InputQueueMsg(new LinkedList<>()));
        }

        // Log any FAILED inputs
        if (failedInput.isPresent() && !failedInput.get().msgQueue().isEmpty()) {
            Queue<InputMsg> failedInputs = failedInput.get().msgQueue();
            log.error("There are {} failed input(s):", failedInputs.size());
            for (InputMsg failedInput : failedInputs) {
                if (failedInput instanceof FailedInputMsg failedInputMsg) {
                    log.error("Failed input: {}, reason: ", failedInputMsg.inputMsg(), failedInputMsg.reason());
                } else {
                    log.error("Failed input: {}", failedInput);
                }
            }
            // Clean up by re-setting the failed inputs queue to empty
            failedInput.set(new InputQueueMsg(new LinkedList<>()));
        }
    }

    private static <T extends InputMsg> Queue<T> find(Class<T> tClass, Predicate<T> predicate, Value<InputQueueMsg> input) {
        return input.get().msgQueue().stream()
                .filter(tClass::isInstance)
                .map(tClass::cast)
                .filter(predicate)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static void add(InputMsg inputMsg, Value<InputQueueMsg> inputs) {
        Queue<InputMsg> input = new LinkedList<>(inputs.get().msgQueue());
        input.add(inputMsg);
        inputs.set(new InputQueueMsg(input));
    }

    private static void addAll(Queue<? extends InputMsg> inputMsgs, Value<InputQueueMsg> inputs) {
        Queue<InputMsg> input = new LinkedList<>(inputs.get().msgQueue());
        input.addAll(inputMsgs);
        inputs.set(new InputQueueMsg(input));
    }

    private static void remove(InputMsg inputMsg, Value<InputQueueMsg> inputs) {
        Queue<InputMsg> input = new LinkedList<>(inputs.get().msgQueue());
        input.remove(inputMsg);
        inputs.set(new InputQueueMsg(input));
    }

    private static void removeAll(Queue<? extends InputMsg> inputMsgs, Value<InputQueueMsg> inputs) {
        Queue<InputMsg> input = new LinkedList<>(inputs.get().msgQueue());
        input.removeAll(inputMsgs);
        inputs.set(new InputQueueMsg(input));
    }
}
