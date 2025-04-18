package de.bwoester.coldfrontier;

/**
 * Represents a component that participates in the game's tick-based processing cycle.
 * <p>
 * The TickComponent interface defines a set of methods that are called by the 
 * {@link TickCoordinator} during different phases of a game tick. Implementations
 * of this interface should handle their specific logic for each phase of the tick.
 * <p>
 * The methods are called in sequence for all components during each tick, providing
 * a structured approach to game state updates.
 */
public interface TickComponent {

    /**
     * Handles player or system input processing at the beginning of a tick.
     * <p>
     * This is the first phase in the tick cycle.
     */
    void handleInput();

    /**
     * Processes updates to game resources such as iron, energy, and population.
     * <p>
     * This is called after input handling.
     */
    void handleResourceUpdates();

    /**
     * Processes units that have completed their construction.
     * <p>
     * This is called after resource updates.
     */
    void handleFinishedUnits();

    /**
     * Processes units that are queued for production.
     * <p>
     * This is called after handling finished units.
     */
    void handleQueuedUnits();

    /**
     * Processes movement of units and other game entities.
     * <p>
     * This is called after processing queued units.
     */
    void handleMovement();

    /**
     * Resolves conflicts between units, such as combat.
     * <p>
     * This is called after movement processing.
     */
    void handleConflicts();

    /**
     * Updates visibility and fog of war for players based on unit positions.
     * <p>
     * This is called after conflict resolution.
     */
    void handleVisibilityUpdates();

    /**
     * Handles saving or updating persistent game state.
     * <p>
     * This is called after visibility updates.
     */
    void handlePersistence();

    /**
     * Performs cleanup operations at the end of a tick, such as removing temporary effects.
     * <p>
     * This is the final phase in the tick cycle.
     */
    void handleCleanup();

}
