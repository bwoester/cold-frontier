package de.bwoester.coldfrontier;

import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.planet.Planet;

import java.util.ArrayList;
import java.util.List;

public class TickCoordinator {

    private final List<TickComponent> components = new ArrayList<>();
    private Value<Long> tick;

    public TickCoordinator(Value<Long> tick) {
        this.tick = tick;
    }

    public void tick() {
        long oldTick = tick.get();
        tick.set(oldTick + 1);

        for (TickComponent component : components) {
            component.handleInput();
        }
        for (TickComponent component : components) {
            component.handleResourceUpdates();
        }
        for (TickComponent component : components) {
            component.handleFinishedUnits();
        }
        for (TickComponent component : components) {
            component.handleQueuedUnits();
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

    public void advanceTo(long tick) {
        while (this.tick.get() < tick) {
            tick();
        }
    }

    public void add(TickComponent tickComponent) {
        components.add(tickComponent);
    }
}
