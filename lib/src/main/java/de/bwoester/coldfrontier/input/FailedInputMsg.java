package de.bwoester.coldfrontier.input;

import java.util.UUID;

public record FailedInputMsg(long inputTick, InputMsg inputMsg, Throwable reason) implements InputMsg {

    @Override
    public UUID inputUuid() {
        return inputMsg.inputUuid();
    }

}
