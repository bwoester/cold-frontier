package de.bwoester.coldfrontier.data;

public interface ValueFactory {

    String GAME_EVENTS_BUCKET = "EVENTS";

    <T> Value<T> create(Class<T> clazz, String key);

}
