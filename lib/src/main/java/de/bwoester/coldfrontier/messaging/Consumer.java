package de.bwoester.coldfrontier.messaging;

public interface Consumer {

    <T> T replayLatest(String subject);

}
