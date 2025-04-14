package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.messaging.GameEventLog;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents buildings of one planet.
 */
public class BuildingService {

    private final GameEventLog<BuildingCountersMsg> buildings;
    private final GameEventLog<ConstructionQueueMsg> constructionQueue;
    private final BuildingDataProvider buildingDataProvider;

    public BuildingService(GameEventLog<BuildingCountersMsg> buildings, GameEventLog<ConstructionQueueMsg> constructionQueue, BuildingDataProvider buildingDataProvider) {
        this.buildings = buildings;
        this.constructionQueue = constructionQueue;
        this.buildingDataProvider = buildingDataProvider;
    }

    public BuildingCountersMsg getBuildings() {
        return buildings.getLatest();
    }

    public void addAll(BuildingCountersMsg buildings) {
        BuildingCountersMsg oldBuildingCounters = this.buildings.getLatest();
        BuildingCountersMsg newBuildingCounters = oldBuildingCounters.add(buildings);
        this.buildings.add(newBuildingCounters);
    }

    public long getConstructionQueueSize() {
        return constructionQueue.getLatest().queuedBuildings().size();
    }

    public void addToConstructionQueue(Building building) {
        ConstructionQueueMsg oldConstructionQueue = constructionQueue.getLatest();
        ConstructionQueueEntryMsg newEntry = new ConstructionQueueEntryMsg(building, calculateConstructionQueueFactor());
        ConstructionQueueMsg newConstructionQueue = oldConstructionQueue.add(newEntry);
        constructionQueue.add(newConstructionQueue);
    }

    public Optional<ConstructionQueueEntryMsg> pollConstructionQueue() {
        ConstructionQueueMsg oldConstructionQueue = constructionQueue.getLatest();
        AtomicReference<ConstructionQueueEntryMsg> polledEntry = new AtomicReference<>();
        ConstructionQueueMsg newConstructionQueue = oldConstructionQueue.poll(polledEntry::set);
        Optional<ConstructionQueueEntryMsg> result = Optional.ofNullable(polledEntry.get());
        result.ifPresent(r -> {
            constructionQueue.add(newConstructionQueue);
        });
        return result;
    }

    public ResourceSetMsg calculateCosts(Building building) {
        BuildingMsg data = buildingDataProvider.getData(building);
        return data.costs().multiply(calculateConstructionQueueFactor());
    }

    private double calculateConstructionQueueFactor() {
        // TODO take other factors into account:
        //  - planet type
        //  - player genetics
        return Math.pow(1.2, getConstructionQueueSize());
    }
}
