package de.bwoester.coldfrontier.data;

/**
 * Represents an immutable event in the messaging system.
 * 
 * @param <T> The type of the payload this event carries
 */
public record Event<T>(
        /**
         * The tick (time point) when this event occurred
         */
        long tick,

        /**
         * The actual payload data of the event
         */
        T payload) {
}
