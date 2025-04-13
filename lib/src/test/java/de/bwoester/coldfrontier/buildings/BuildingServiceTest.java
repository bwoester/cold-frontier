package de.bwoester.coldfrontier.buildings;

import de.bwoester.coldfrontier.EventLogStub;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.messaging.GameEventLog;
import de.bwoester.coldfrontier.messaging.GameEventSubject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Slf4j
class BuildingServiceTest {

    EventLogStub eventLogStub;
    GameEventLog<BuildingCountersMsg> buildingCountersLog;
    GameEventLog<ConstructionQueueMsg> constructionQueueLog;

    BuildingService buildingService;

    @BeforeEach
    void setUp() {
        eventLogStub = new EventLogStub();
        buildingCountersLog = eventLogStub.inMemoryGameEventLog.viewOfType(BuildingCountersMsg.class,
                GameEventSubject.Building.counters("planet-1"));
        constructionQueueLog = eventLogStub.inMemoryGameEventLog.viewOfType(ConstructionQueueMsg.class,
                GameEventSubject.Building.queue("planet-1"));
        buildingService = new BuildingService(buildingCountersLog, constructionQueueLog);

        buildingCountersLog.add(new BuildingCountersMsg(Map.of(
                Building.IRON_MINE, 1L
        )));
        Queue<ConstructionQueueEntryMsg> constructionQueue = new LinkedList<>();
        constructionQueueLog.add(new ConstructionQueueMsg(constructionQueue));
    }

    @AfterEach
    void tearDown() {
        log.info("event log:\n{}", eventLogStub.inMemoryGameEventLog.prettyPrint());
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
        ResourceSetMsg regularCosts = Building.IRON_MINE.getData().cost();
        Assertions.assertEquals(regularCosts, buildingService.calculateCosts(Building.IRON_MINE));

        // filled construction queue means increased costs
        // TODO effect only visible with greater queue or  greater building costs
        //  maybe mock?
        buildingService.addToConstructionQueue(Building.IRON_MINE);
        buildingService.addToConstructionQueue(Building.IRON_MINE);
        buildingService.addToConstructionQueue(Building.IRON_MINE);
        buildingService.addToConstructionQueue(Building.IRON_MINE);
        buildingService.addToConstructionQueue(Building.IRON_MINE);
        Assertions.assertEquals(5, buildingService.getConstructionQueueSize());
        ResourceSetMsg actualCosts = buildingService.calculateCosts(Building.IRON_MINE);
        Assertions.assertTrue(actualCosts.credits() > regularCosts.credits());
        Assertions.assertTrue(actualCosts.planetResources().energy() > regularCosts.planetResources().energy());
        Assertions.assertTrue(actualCosts.planetResources().iron() > regularCosts.planetResources().iron());
        Assertions.assertTrue(actualCosts.planetResources().population() > regularCosts.planetResources().population());
    }
}