package de.bwoester.coldfrontier.progress;

import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;

import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Supplier;

public class ProgressService {

    private final NavigableMap<Long, ProgressMsg> history = new TreeMap<>();

    private final Supplier<Long> tickSupplier;
    private BuildingCountersMsg completedBuildings;

    public ProgressService(Supplier<Long> tickSupplier) {
        this.tickSupplier = tickSupplier;
        this.completedBuildings = null;
    }

    public void tick() {
        long tick = tickSupplier.get();
        Map.Entry<Long, ProgressMsg> lastKnownEntry = history.lowerEntry(tick);

        // default: no buildings completed this tick
        completedBuildings = new BuildingCountersMsg(Collections.emptyMap());

        // we only need to handle ProgressMsg from the last tick, which have progress in range [0, 1[
        if (lastKnownEntry != null) {
            long lastKnownTick = lastKnownEntry.getKey();
            ProgressMsg lastKnownMsg = lastKnownEntry.getValue();
            float lastKnownProgress = lastKnownMsg.progress();
            if (lastKnownTick == tick - 1 && 0 <= lastKnownProgress && lastKnownProgress < 1) {
                float tickProgress = 1.0f / switch (lastKnownMsg) {
                    case CreateBuildingProgressMsg i -> i.building().getData().ticksToBuild();
                };
                float newProgress = lastKnownProgress * tickProgress;
                ProgressMsg newMsg = switch (lastKnownMsg) {
                    case CreateBuildingProgressMsg i -> new CreateBuildingProgressMsg(i.building(), i.timeToBuildMultiplier(), newProgress);
                };
                history.put(tick, newMsg);
                if (newProgress >= 1) {
                    completedBuildings = switch (lastKnownMsg) {
                        case CreateBuildingProgressMsg i -> new BuildingCountersMsg(Map.of(i.building(), 1L));
                    };
                }
            }
        }
    }

    public BuildingCountersMsg getCompletedBuildings() {
        return completedBuildings;
    }

    public void startBuilding(Building building, float timeToBuildMultiplier) {
        // TODO once we might create more than one progress per tick (e.g. building & ship, or multiple planets)
        //  all of them must be put in history in one go?
        CreateBuildingProgressMsg progressMsg = new CreateBuildingProgressMsg(building, timeToBuildMultiplier, 0f);
        history.put(tickSupplier.get(), progressMsg);
    }
}
