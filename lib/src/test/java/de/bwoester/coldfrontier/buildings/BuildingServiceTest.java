package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.TestValues;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class BuildingServiceTest {

    TestValues testValues;
    Value<BuildingCountersMsg> buildingCounters;
    Value<ConstructionQueueMsg> constructionQueue;

    BuildingDataProvider buildingDataProvider;
    BuildingService buildingService;
    private static final ResourceSetMsg IRON_MINE_COSTS = ResourceSetMsg.createOne().multiply(10);

    @BeforeEach
    void setUp() {
        testValues = new TestValues();
        buildingCounters = testValues.util.get(BuildingCountersMsg.class,
                Keys.Building.counters("planet-1"));
        constructionQueue = testValues.util.get(ConstructionQueueMsg.class,
                Keys.Building.queue("planet-1"));
        buildingDataProvider = mock(BuildingDataProvider.class);
        buildingService = new BuildingService(buildingCounters, constructionQueue, buildingDataProvider);

        buildingCounters.set(new BuildingCountersMsg(Map.of(
                Building.IRON_MINE, 1L
        )));
        Queue<ConstructionQueueEntryMsg> constructionQueue = new LinkedList<>();
        this.constructionQueue.set(new ConstructionQueueMsg(constructionQueue));

        when(buildingDataProvider.getData(eq(Building.IRON_MINE))).thenReturn(new BuildingMsg(
                Building.IRON_MINE.toString(),
                1,
                IRON_MINE_COSTS,
                ResourceSetMsg.createOne().multiply(10)
        ));
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", testValues.util.prettyPrint());
    }

    @Test
    void get() {
        BuildingCountersMsg buildings = buildingService.getBuildings();
        Assertions.assertNotNull(buildings);

        Map<Building, Long> counters = buildings.counters();
        Assertions.assertNotNull(counters);

        Assertions.assertTrue(counters.containsKey(Building.IRON_MINE));
        Assertions.assertEquals(1, counters.get(Building.IRON_MINE));
    }

    @Test
    void addAll() {
        Assertions.assertEquals(1, buildingService.getBuildings().counters().get(Building.IRON_MINE));
        buildingService.addAll(new BuildingCountersMsg(Map.of(
                Building.IRON_MINE, 1L
        )));
        Assertions.assertEquals(2, buildingService.getBuildings().counters().get(Building.IRON_MINE));
    }


    @Test
    void getConstructionQueueSize() {
        Assertions.assertEquals(0, buildingService.getConstructionQueueSize());
    }

    @Test
    void addToConstructionQueue() {
        Assertions.assertEquals(1, buildingService.getBuildings().counters().get(Building.IRON_MINE));
        Assertions.assertEquals(0, buildingService.getConstructionQueueSize());
        buildingService.addToConstructionQueue(Building.IRON_MINE);
        Assertions.assertEquals(1, buildingService.getBuildings().counters().get(Building.IRON_MINE));
        Assertions.assertEquals(1, buildingService.getConstructionQueueSize());
    }

    @Test
    void pollConstructionQueue() {
        // polling empty queue returns empty optional
        Assertions.assertEquals(0, buildingService.getConstructionQueueSize());
        Assertions.assertTrue(buildingService.pollConstructionQueue().isEmpty());

        // polling filled queue returns item and shortens queue
        buildingService.addToConstructionQueue(Building.IRON_MINE);
        Assertions.assertEquals(1, buildingService.getConstructionQueueSize());
        Assertions.assertTrue(buildingService.pollConstructionQueue().isPresent());
        Assertions.assertEquals(0, buildingService.getConstructionQueueSize());
    }


    @Test
    void calculateCosts() {
        // empty construction queue means regular costs
        Assertions.assertEquals(0, buildingService.getConstructionQueueSize());
        Assertions.assertEquals(IRON_MINE_COSTS, buildingService.calculateCosts(Building.IRON_MINE));

        // filled construction queue means increased costs
        buildingService.addToConstructionQueue(Building.IRON_MINE);
        Assertions.assertEquals(1, buildingService.getConstructionQueueSize());
        ResourceSetMsg increasedCosts = buildingService.calculateCosts(Building.IRON_MINE);
        Assertions.assertTrue(increasedCosts.credits() > IRON_MINE_COSTS.credits());
        Assertions.assertTrue(increasedCosts.planetResources().energy() > IRON_MINE_COSTS.planetResources().energy());
        Assertions.assertTrue(increasedCosts.planetResources().iron() > IRON_MINE_COSTS.planetResources().iron());
        Assertions.assertTrue(increasedCosts.planetResources().population() > IRON_MINE_COSTS.planetResources().population());
    }
}