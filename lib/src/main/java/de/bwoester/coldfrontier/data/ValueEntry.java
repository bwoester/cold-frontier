package de.bwoester.coldfrontier.data;

/**
 * Represents an entry in the key-value store with its metadata.
 * <p>
 * This record encapsulates all information about a specific value in the key-value store,
 * including its location (bucket and key), the value itself, and metadata about the entry
 * such as tick, revision, and the operation that was performed.
 *
 * @param <T> The type of the value stored in this entry
 */
public record ValueEntry<T>(
        /**
         * The bucket and key identifying the location of this entry in the key-value store.
         */
        BucketAndKey bucketAndKey,

        /**
         * The value stored in this entry.
         */
        Event<T> event
) {
}
