package de.bwoester.coldfrontier.input;

import java.util.UUID;

public interface InputMsg {

    /**
     * Input that is received will be processed during the next input processing phase.
     *
     * @return tick of input reception
     */
    long inputTick();

    /**
     * @return uuid for the input, required for feedback
     */
    UUID inputUuid();

}
