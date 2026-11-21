package de.bwoester.coldfrontier.production;

import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;
import de.bwoester.coldfrontier.buildings.BuildingDataProvider;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {

    @Mock
    private BuildingDataProvider buildingDataProvider;

    private ProductionService productionService;

    @BeforeEach
    void setUp() {
        productionService = new ProductionService(buildingDataProvider, Duration.ofSeconds(1));
    }

    @Test
    void calculateProductionPerHour_shouldReturnSumOfAllBuildingsProduction() {
        // Given
        Building buildingA = Building.SOLAR_CELL;
        Building buildingB = Building.IRON_MINE;

        ResourceSetMsg productionA = ResourceSetMsg.createOne(); // Assuming this creates a resource set with value 1
        ResourceSetMsg productionB = ResourceSetMsg.createOne().multiply(2); // Production with value 2

        BuildingMsg buildingMsgA = new BuildingMsg(Building.SOLAR_CELL.toString(), 1, ResourceSetMsg.createDefault(), productionA);
        BuildingMsg buildingMsgB = new BuildingMsg(Building.IRON_MINE.toString(), 1, ResourceSetMsg.createDefault(), productionB);

        // Mock building data provider
        when(buildingDataProvider.getData(buildingA)).thenReturn(buildingMsgA);
        when(buildingDataProvider.getData(buildingB)).thenReturn(buildingMsgB);

        // Create building counters with 3 of building A and 2 of building B
        Map<Building, Long> counters = new HashMap<>();
        counters.put(buildingA, 3L);
        counters.put(buildingB, 2L);
        BuildingCountersMsg buildingCounters = new BuildingCountersMsg(counters);

        // When
        ResourceSetMsg result = productionService.calculateProductionPerHour(buildingCounters);

        // Then
        // Expected: 3 * productionA + 2 * productionB = 3*1 + 2*2 = 7
        ResourceSetMsg expected = productionA.multiply(3).add(productionB.multiply(2));
        assertEquals(expected, result);
    }

    @Test
    void calculateProductionPerHour_withEmptyCounters_shouldReturnDefaultResources() {
        // Given
        BuildingCountersMsg emptyCounters = new BuildingCountersMsg(new HashMap<>());

        // When
        ResourceSetMsg result = productionService.calculateProductionPerHour(emptyCounters);

        // Then
        assertEquals(ResourceSetMsg.createDefault(), result);
    }

    @Test
    void retainsNonTerminatingFractionsUntilAWholeUnitCanBeBooked() {
        Building building = Building.IRON_MINE;
        ResourceSetMsg hourlyRate = new ResourceSetMsg(new PlanetResourceSetMsg(3, -3, 0), 0);
        when(buildingDataProvider.getData(building)).thenReturn(
                new BuildingMsg(building.toString(), 2, ResourceSetMsg.createDefault(), hourlyRate));
        BuildingCountersMsg buildings = new BuildingCountersMsg(Map.of(building, 1L));
        ResourceSetMsg remainder = ResourceSetMsg.createDefault();
        for (int tick = 1; tick < 1200; tick++) {
            ProductionService.ProductionStep step = productionService.calculateTickProduction(buildings, remainder);
            assertEquals(ResourceSetMsg.createDefault(), step.wholeUnits());
            remainder = step.remainder();
        }

        ProductionService.ProductionStep step = productionService.calculateTickProduction(buildings, remainder);
        assertEquals(new ResourceSetMsg(new PlanetResourceSetMsg(1, -1, 0), 0), step.wholeUnits());
        assertEquals(ResourceSetMsg.createDefault(), step.remainder());
    }

}
