package de.bwoester.coldfrontier.production;

import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Supplier;

public class ProductionService {

    private final NavigableMap<Long, ResourceSetMsg> history = new TreeMap<>();

    private final Supplier<Long> tickSupplier;

    public ProductionService(Supplier<Long> tickSupplier) {
        this.tickSupplier = tickSupplier;
    }

    public void tick(BuildingCountersMsg buildingCounters) {
        ResourceSetMsg production = new ResourceSetMsg(new PlanetResourceSetMsg(0, 0, 0), 0);
        for (Map.Entry<Building, Long> counter : buildingCounters.counters().entrySet()) {
            Building building = counter.getKey();
            ResourceSetMsg oneBuildingProd = building.getData().production();
            ResourceSetMsg allBuildingsProd = oneBuildingProd.multiply(counter.getValue());
            // TODO take resource depletion into account
            production = production.add(allBuildingsProd);
        }
        history.put(tickSupplier.get(), production);
    }

    public ResourceSetMsg getProduction() {
        Map.Entry<Long, ResourceSetMsg> entry = history.lastEntry();
        if (!entry.getKey().equals(tickSupplier.get())) {
            throw new IllegalStateException("Last production entry does not correspond to current tick!");
        }
        return entry.getValue();
    }

}
