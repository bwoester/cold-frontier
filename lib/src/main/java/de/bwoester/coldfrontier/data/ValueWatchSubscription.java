package de.bwoester.coldfrontier.data;

/**
 * Represents a subscription to watch for changes to values in the key-value store.
 * <p>
 * This abstract class implements AutoCloseable to allow subscriptions to be used
 * in try-with-resources blocks, automatically unsubscribing when the block exits.
 */
public abstract class ValueWatchSubscription implements AutoCloseable {

    /**
     * Cancels the subscription and stops receiving notifications about value changes.
     */
    public abstract void unsubscribe();

    /**
     * Implements the AutoCloseable interface by unsubscribing from the watch.
     * This allows ValueWatchSubscription to be used in try-with-resources blocks.
     */
    @Override
    public void close() {
        unsubscribe();
    }
}
