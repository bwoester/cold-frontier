package de.bwoester.coldfrontier;

public interface TickComponent {

    void handleInput();
    void handleResourceUpdates();
    void handleFinishedUnits();
    void handleQueuedUnits();
    void handleMovement();
    void handleConflicts();
    void handleVisibilityUpdates();
    void handlePersistence();
    void handleCleanup();

}
