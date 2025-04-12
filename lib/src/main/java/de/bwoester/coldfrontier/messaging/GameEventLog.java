package de.bwoester.coldfrontier.messaging;

import java.util.List;

public interface GameEventLog<T> {

    boolean isEmpty();

    T getLatest();

    void add(T latest);

    List<T> getAll();
}
