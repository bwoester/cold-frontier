package de.bwoester.coldfrontier.messaging;

public interface Publisher {

    void publish(String subject, Object o);

}
