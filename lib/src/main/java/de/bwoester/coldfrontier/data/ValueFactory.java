package de.bwoester.coldfrontier.data;

public interface ValueFactory {

    <T> Value<T> create(Class<T> clazz, String key);

}
