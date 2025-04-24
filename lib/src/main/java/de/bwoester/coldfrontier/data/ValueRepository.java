package de.bwoester.coldfrontier.data;

/**
 * Repository for accessing and managing Value objects that serve as references to data
 * in the key-value store. Following the repository pattern, this interface abstracts
 * the data access mechanisms for the Value objects.
 * <p>
 * Implementations should cache Value instances to improve performance and ensure
 * consistency when the same key is accessed multiple times.
 */
public interface ValueRepository {

    String GAME_EVENTS_BUCKET = "EVENTS";

    /**
     * Gets a Value instance for the specified class and key.
     * <p>
     * Implementations should return an existing instance if one has already been
     * created for this class and key combination, or create a new one if needed.
     *
     * @param <T> The type of the value
     * @param clazz The class representing the type of the value
     * @param key The key that identifies this value in the store
     * @return A Value instance that provides access to the data
     */
    <T> Value<T> get(Class<T> clazz, String key);

}
