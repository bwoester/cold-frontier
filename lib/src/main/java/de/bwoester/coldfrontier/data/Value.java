package de.bwoester.coldfrontier.data;

import io.nats.client.JetStreamApiException;

import java.io.IOException;
import java.util.Optional;

/**
 * A simplified interface for working with a single typed value in a NATS key-value store.
 * <p>
 * This interface provides a more streamlined API compared to the native NATS KeyValue
 * interface by abstracting the underlying bucket and key management, and providing
 * type-safe access to values.
 * 
 * @param <T> The type of the value stored in the key-value store
 */
public interface Value<T> {

    /**
     * Checks if a value is present in the key-value store.
     *
     * @return true if the value exists and is not deleted, false otherwise
     */
    boolean isPresent();

    /**
     * Retrieves the current value from the key-value store.
     *
     * @return the current value
     * @throws RuntimeException if the value does not exist or has been deleted
     */
    T get();

    /**
     * Sets a new value in the key-value store.
     *
     * @param value the value to store
     */
    void set(T value);

    /**
     * Deletes the value from the key-value store.
     */
    void delete();

    /**
     * Watches for changes to this value in the key-value store.
     *
     * @param watcher the watcher that will be notified of changes
     * @return a subscription that can be used to unsubscribe from watching
     */
    ValueWatchSubscription watch(ValueWatcher<T> watcher) throws JetStreamApiException, IOException, InterruptedException;

}
