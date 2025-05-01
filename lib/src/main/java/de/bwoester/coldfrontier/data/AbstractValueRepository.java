package de.bwoester.coldfrontier.data;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract base class for ValueRepository implementations that provides caching functionality.
 * <p>
 * This class handles the caching of Value instances to avoid recreating them when the same
 * class and key combination is requested multiple times. Subclasses only need to implement
 * the createValue method to specify how a specific Value instance should be created when
 * it's not found in the cache.
 */
@RequiredArgsConstructor
public abstract class AbstractValueRepository implements ValueRepository {

    // Cache to store Value instances by their type and key
    private final Map<CacheKey, Value<?>> valueCache = new ConcurrentHashMap<>();

    @Override
    public <T> Value<T> get(Class<T> clazz, String key) {
        CacheKey cacheKey = new CacheKey(clazz, key);

        @SuppressWarnings("unchecked")
        Value<T> value = (Value<T>) valueCache.computeIfAbsent(cacheKey, k ->
                createValue(clazz, key)
        );

        return value;
    }

    @Override
    public <T> Value<T> get(Class<T> clazz, String key, T initValue) {
        Value<T> value = get(clazz, key);
        if (!value.isPresent()) {
            value.set(initValue);
        }
        return value;
    }

    /**
     * Creates a new Value instance for the specified class and key.
     * <p>
     * This method is called by the get method when a Value instance is not found in the cache.
     * Subclasses must implement this method to create a Value instance appropriate for their
     * specific storage mechanism.
     *
     * @param <T>   The type of the value
     * @param clazz The class representing the type of the value
     * @param key   The key that identifies this value in the store
     * @return A new Value instance
     */
    protected abstract <T> Value<T> createValue(Class<T> clazz, String key);

    /**
     * Key for caching Value instances based on their class and key.
     */
    protected static class CacheKey {
        private final Class<?> clazz;
        private final String key;

        public CacheKey(Class<?> clazz, String key) {
            this.clazz = clazz;
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CacheKey cacheKey = (CacheKey) o;

            if (!clazz.equals(cacheKey.clazz)) return false;
            return key.equals(cacheKey.key);
        }

        @Override
        public int hashCode() {
            int result = clazz.hashCode();
            result = 31 * result + key.hashCode();
            return result;
        }
    }
}
