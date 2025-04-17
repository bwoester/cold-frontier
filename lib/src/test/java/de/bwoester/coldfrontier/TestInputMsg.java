package de.bwoester.coldfrontier;

import de.bwoester.coldfrontier.input.InputMsg;

import java.util.UUID;

/**
 * Test implementation of InputMsg for unit testing
 */
public class TestInputMsg implements InputMsg {
    private final long tick;
    private final UUID uuid;
    private final String planetId;

    public TestInputMsg(long tick) {
        this(tick, "default-planet");
    }

    public TestInputMsg(long tick, String planetId) {
        this.tick = tick;
        this.uuid = UUID.randomUUID();
        this.planetId = planetId;
    }

    @Override
    public long inputTick() {
        return tick;
    }

    @Override
    public UUID inputUuid() {
        return uuid;
    }

    public String planetId() {
        return planetId;
    }
}
