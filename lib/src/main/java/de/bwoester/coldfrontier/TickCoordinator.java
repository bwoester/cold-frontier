package de.bwoester.coldfrontier;

import de.bwoester.coldfrontier.data.Value;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TickCoordinator {

    public static final Duration DEFAULT_TICK_DURATION = Duration.ofSeconds(1);

    private final List<TickComponent> components = new ArrayList<>();
    private final Value<Long> tick;
    private final Duration tickDuration;

    public TickCoordinator(Value<Long> tick) {
        this(tick, DEFAULT_TICK_DURATION);
    }

    public TickCoordinator(Value<Long> tick, Duration tickDuration) {
        if (tickDuration.isZero() || tickDuration.isNegative()) {
            throw new IllegalArgumentException("Tick duration must be positive");
        }
        this.tick = tick;
        this.tickDuration = tickDuration;
    }

    public Duration tickDuration() {
        return tickDuration;
    }

    public synchronized void tick() {
        long oldTick = tick.get();
        tick.set(Math.incrementExact(oldTick));

        for (TickComponent component : components) {
            component.handleResourceUpdates(tickDuration);
        }
        for (TickComponent component : components) {
            component.handleInput();
        }
        for (TickComponent component : components) {
            component.handleFinishedUnits(tickDuration);
        }
        for (TickComponent component : components) {
            component.handleQueuedUnits();
        }
        for (TickComponent component : components) {
            component.handleResourceStarts();
        }
        for (TickComponent component : components) {
            component.handleMovement();
        }
        for (TickComponent component : components) {
            component.handleConflicts();
        }
        for (TickComponent component : components) {
            component.handleVisibilityUpdates();
        }
        for (TickComponent component : components) {
            component.handlePersistence();
        }
        for (TickComponent component : components) {
            component.handleCleanup();
        }
    }

    /** Advances to an exact point in game time, measured from tick zero. */
    public void advanceTo(Duration gameTime) {
        if (gameTime.isNegative()) {
            throw new IllegalArgumentException("Game time must be nonnegative");
        }
        long targetTick = gameTime.dividedBy(tickDuration);
        if (!tickDuration.multipliedBy(targetTick).equals(gameTime)) {
            throw new IllegalArgumentException("Game time must fall on a tick boundary: " + gameTime);
        }
        advanceTo(targetTick);
    }

    public synchronized void advanceTo(long tick) {
        while (this.tick.get() < tick) {
            tick();
        }
    }

    public synchronized void add(TickComponent tickComponent) {
        components.add(tickComponent);
    }
}
