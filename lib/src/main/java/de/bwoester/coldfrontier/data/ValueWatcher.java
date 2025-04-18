package de.bwoester.coldfrontier.data;

/**
 * Interface for watching changes to values in the key-value store.
 * <p>
 * This interface defines callbacks that are invoked when watched values change
 * or when the initial data load is complete.
 *
 * @param <T> The type of the values being watched
 */
public interface ValueWatcher<T> {

    /**
     * Called when an object has been updated
     *
     * @param t The watched object entry containing the value and its metadata
     */
    void watch(ValueEntry<T> t);

    /**
     * Called once if there is no data when the watch is created
     * or if there is data, the first time the watch exhausts all existing data.
     */
    void endOfData();

}
