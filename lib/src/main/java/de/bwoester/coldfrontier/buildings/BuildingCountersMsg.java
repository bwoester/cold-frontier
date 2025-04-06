package de.bwoester.coldfrontier.buildings;

import java.util.HashMap;
import java.util.Map;

public record BuildingCountersMsg(Map<Building, Long> counters) {

    public BuildingCountersMsg add(BuildingCountersMsg other) {
        Map<Building, Long> mergedCounters = new HashMap<>(this.counters);

        for (Map.Entry<Building, Long> entry : other.counters.entrySet()) {
            mergedCounters.merge(entry.getKey(), entry.getValue(), Long::sum);
        }

        return new BuildingCountersMsg(Map.copyOf(mergedCounters));
    }

}
