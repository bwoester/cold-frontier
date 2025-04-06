package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.input.CreateBuildingInputMsg;

import java.util.*;
import java.util.function.Supplier;

public class BuildingService {

    private final NavigableMap<Long, BuildingCountersMsg> history = new TreeMap<>();
    private final Queue<ConstructionQueueEntryMsg> constructionQueue = new LinkedList<>();

    private final Supplier<Long> tickSupplier;
    private BuildingCountersMsg buildings;

    public BuildingService(Supplier<Long> tickSupplier, BuildingCountersMsg buildings) {
        this.tickSupplier = tickSupplier;
        this.buildings = buildings;
        history.put(tickSupplier.get(), buildings);
    }

    public void tick() {
        long tick = tickSupplier.get();
        Map.Entry<Long, BuildingCountersMsg> lastKnown = history.floorEntry(tick);
        buildings = lastKnown.getValue();
    }

    public BuildingCountersMsg getBuildings() {
        return buildings;
    }

    public void addAll(BuildingCountersMsg buildings) {
        this.buildings = this.buildings.add(buildings);
        history.put(tickSupplier.get(), this.buildings);
    }

    public long getConstructionQueueSize() {
        return constructionQueue.size();
    }

    public void addToConstructionQueue(Building building) {
        constructionQueue.add(new ConstructionQueueEntryMsg(building, calculateConstructionQueueFactor()));
    }

    public Optional<ConstructionQueueEntryMsg> pollConstructionQueue() {
        return Optional.ofNullable(constructionQueue.poll());
    }

    public ResourceSetMsg calculateCosts(CreateBuildingInputMsg msg) {
        return msg.building().getData().cost().multiply(calculateConstructionQueueFactor());
    }

    private float calculateConstructionQueueFactor() {
        // TODO take other factors into account:
        //  - planet type
        //  - player genetics
        return 1 + (0.2f * constructionQueue.size());
    }
}
