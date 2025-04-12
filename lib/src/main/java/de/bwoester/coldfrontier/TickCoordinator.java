package de.bwoester.coldfrontier;

import java.util.List;

public class TickCoordinator {

    private final List<TickComponent> components;
    private long currentTick;

    public TickCoordinator(List<TickComponent> components, long currentTick) {
        this.components = components;
        this.currentTick = currentTick;
    }

    public void tick() {
        currentTick++;

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
        while (currentTick < tick) {
            tick();
        }
    }

}
