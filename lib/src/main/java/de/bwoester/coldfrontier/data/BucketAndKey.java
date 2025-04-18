package de.bwoester.coldfrontier.data;

/**
 * Represents the location of a value in the NATS key-value store.
 * <p>
 * This record encapsulates both the bucket (collection of key-value pairs) and
 * the key within that bucket, which together uniquely identify a value in the store.
 */
public record BucketAndKey(
        /**
         * The name of the bucket (collection) in the key-value store.
         */
        String bucket, 
        
        /**
         * The key identifying a specific value within the bucket.
         */
        String key
) {
}
