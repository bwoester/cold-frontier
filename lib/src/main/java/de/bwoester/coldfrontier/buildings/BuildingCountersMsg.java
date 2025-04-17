package de.bwoester.coldfrontier.buildings;

import java.util.HashMap;
import java.util.Map;

/**
 * Message containing building counters.
 */
public record BuildingCountersMsg(Map<Building, Long> counters) {

    public BuildingCountersMsg {
        // Create an unmodifiable copy of the counters map
        counters = Map.copyOf(counters);
    }

    /**
     * Empty building counters constructor.
     */
    public BuildingCountersMsg() {
        this(Map.of());
    }

    /**
     * Add another BuildingCountersMsg to this one.
     *
     * @param other The BuildingCountersMsg to add
     * @return A new BuildingCountersMsg with combined counters
     */
    public BuildingCountersMsg add(BuildingCountersMsg other) {
        Map<Building, Long> newCounters = new HashMap<>(this.counters);

        for (Map.Entry<Building, Long> entry : other.counters.entrySet()) {
            Building building = entry.getKey();
            Long count = entry.getValue();

            newCounters.put(building, newCounters.getOrDefault(building, 0L) + count);
        }

        return new BuildingCountersMsg(newCounters);
    }
}
