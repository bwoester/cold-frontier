package de.bwoester.coldfrontier.production;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.accounting.AccountingService;
import de.bwoester.coldfrontier.accounting.Ledger;
import de.bwoester.coldfrontier.accounting.PlanetAccount;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.accounting.TransactionMsg;
import de.bwoester.coldfrontier.accounting.UserAccount;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;
import de.bwoester.coldfrontier.buildings.BuildingDataProvider;
import de.bwoester.coldfrontier.buildings.BuildingMsg;
import de.bwoester.coldfrontier.buildings.BuildingService;
import de.bwoester.coldfrontier.buildings.ConstructionQueueMsg;
import de.bwoester.coldfrontier.data.EventFactory;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.memory.InMemoryDataAccess;
import de.bwoester.coldfrontier.data.memory.InMemoryValueRepository;
import de.bwoester.coldfrontier.input.InputService;
import de.bwoester.coldfrontier.planet.Planet;
import de.bwoester.coldfrontier.planet.PlanetMsg;
import de.bwoester.coldfrontier.progress.ProgressService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayDeque;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ProductionTickTest {

    @ParameterizedTest(name = "advance in stages: {0}")
    @ValueSource(booleans = {false, true})
    void rejectsTheEntireProductionBatchWhenEnergyIsInsufficient(boolean advanceInStages) {
        String planetId = "test-planet";
        InMemoryDataAccess data = new InMemoryDataAccess();
        InMemoryValueRepository values = new InMemoryValueRepository(data.getData(), new EventFactory(() -> 0L));
        Value<PlanetResourceSetMsg> balance = values.get(
                PlanetResourceSetMsg.class, "balance", new PlanetResourceSetMsg(0, 2, 0));
        PlanetAccount account = new PlanetAccount(balance);
        Ledger ledger = new Ledger(new UserAccount(values.get(Long.class, "credits", 0L)),
                Map.of(planetId, account));
        AccountingService accounting = new AccountingService(ledger,
                values.get(TransactionMsg.class, "transactions"));

        BuildingDataProvider buildings = building -> {
            PlanetResourceSetMsg production = switch (building) {
                case IRON_MINE -> new PlanetResourceSetMsg(1, -2, 0);
                case SMALL_HOUSE -> new PlanetResourceSetMsg(0, 0, 1);
                default -> throw new IllegalArgumentException("Unexpected building: " + building);
            };
            return new BuildingMsg(building.name(), 1, ResourceSetMsg.createDefault(),
                    new ResourceSetMsg(production, 0));
        };
        BuildingService buildingService = new BuildingService(values.get(
                BuildingCountersMsg.class, "buildings",
                new BuildingCountersMsg(Map.of(Building.IRON_MINE, 1L, Building.SMALL_HOUSE, 1L))),
                values.get(ConstructionQueueMsg.class, "queue", new ConstructionQueueMsg(new ArrayDeque<>())),
                buildings);

        TickCoordinator ticker = new TickCoordinator(values.get(Long.class, "tick", 0L));
        ticker.add(new Planet(values.get(PlanetMsg.class, "planet",
                new PlanetMsg(planetId, null, null, null, null)),
                mock(InputService.class), buildingService, accounting,
                new ProductionService(buildings), mock(ProgressService.class)));

        if (advanceInStages) {
            ticker.advanceTo(1);
            assertEquals(new PlanetResourceSetMsg(1, 0, 1), account.getBalance());
        }

        ticker.advanceTo(3);

        // The energy shortage also blocks the house's independent output.
        assertEquals(new PlanetResourceSetMsg(1, 0, 1), account.getBalance());
    }
}
