package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.data.Value;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents buildings of one planet.
 */
public class BuildingService {

    private final Value<BuildingCountersMsg> buildings;
    private final Value<ConstructionQueueMsg> constructionQueue;
    private final BuildingDataProvider buildingDataProvider;

    public BuildingService(Value<BuildingCountersMsg> buildings, Value<ConstructionQueueMsg> constructionQueue, BuildingDataProvider buildingDataProvider) {
        this.buildings = buildings;
        this.constructionQueue = constructionQueue;
        this.buildingDataProvider = buildingDataProvider;
    }

    public BuildingCountersMsg getBuildings() {
        return buildings.get();
    }

    public void addAll(BuildingCountersMsg buildings) {
        BuildingCountersMsg oldBuildingCounters = this.buildings.get();
        BuildingCountersMsg newBuildingCounters = oldBuildingCounters.add(buildings);
        this.buildings.set(newBuildingCounters);
    }

    public long getConstructionQueueSize() {
        return constructionQueue.get().queuedBuildings().size();
    }

    public void addToConstructionQueue(Building building) {
        ConstructionQueueMsg oldConstructionQueue = constructionQueue.get();
        ConstructionQueueEntryMsg newEntry = new ConstructionQueueEntryMsg(building, calculateConstructionQueueFactor());
        ConstructionQueueMsg newConstructionQueue = oldConstructionQueue.add(newEntry);
        constructionQueue.set(newConstructionQueue);
    }

    public Optional<ConstructionQueueEntryMsg> pollConstructionQueue() {
        ConstructionQueueMsg oldConstructionQueue = constructionQueue.get();
        AtomicReference<ConstructionQueueEntryMsg> polledEntry = new AtomicReference<>();
        ConstructionQueueMsg newConstructionQueue = oldConstructionQueue.poll(polledEntry::set);
        Optional<ConstructionQueueEntryMsg> result = Optional.ofNullable(polledEntry.get());
        result.ifPresent(r -> {
            constructionQueue.set(newConstructionQueue);
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
