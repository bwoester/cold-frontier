package de.bwoester.coldfrontier.prototype.testing;

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
import de.bwoester.coldfrontier.buildings.BuildingService;
import de.bwoester.coldfrontier.buildings.ConstructionQueueMsg;
import de.bwoester.coldfrontier.data.EventFactory;
import de.bwoester.coldfrontier.data.Value;
import de.bwoester.coldfrontier.data.memory.InMemoryDataAccess;
import de.bwoester.coldfrontier.data.memory.InMemoryValueRepository;
import de.bwoester.coldfrontier.input.InputQueueMsg;
import de.bwoester.coldfrontier.input.InputService;
import de.bwoester.coldfrontier.planet.Planet;
import de.bwoester.coldfrontier.planet.PlanetMsg;
import de.bwoester.coldfrontier.progress.ProgressMsg;
import de.bwoester.coldfrontier.progress.ProgressService;
import de.bwoester.coldfrontier.production.ProductionService;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Map;

public record InMemoryPlanetSimulation(TickCoordinator ticker, Planet planet, PlanetAccount account,
                                       UserAccount userAccount, BuildingService buildingService,
                                       ProgressService progressService,
                                       Value<ResourceSetMsg> remainder,
                                       Value<Long> tick) {
    public static InMemoryPlanetSimulation create(Duration tickDuration, BuildingDataProvider buildings,
                                                  Map<Building, Long> completedBuildings,
                                                  PlanetResourceSetMsg initialBalance, ResourceSetMsg initialRemainder) {
        String planetId = "test-planet";
        InMemoryDataAccess data = new InMemoryDataAccess();
        InMemoryValueRepository values = new InMemoryValueRepository(data.getData(), new EventFactory(() -> 0L));
        Value<PlanetResourceSetMsg> balance = values.get(
                PlanetResourceSetMsg.class, "balance", initialBalance);
        PlanetAccount account = new PlanetAccount(balance);
        UserAccount userAccount = new UserAccount(values.get(Long.class, "credits", 0L));
        Ledger ledger = new Ledger(userAccount, Map.of(planetId, account));
        AccountingService accounting = new AccountingService(ledger,
                values.get(TransactionMsg.class, "transactions"));

        BuildingService buildingService = new BuildingService(values.get(
                BuildingCountersMsg.class, "buildings",
                new BuildingCountersMsg(completedBuildings)),
                values.get(ConstructionQueueMsg.class, "queue", new ConstructionQueueMsg(new ArrayDeque<>())),
                buildings);

        InputQueueMsg emptyInput = new InputQueueMsg(new ArrayDeque<>());
        InputService input = new InputService(
                values.get(InputQueueMsg.class, "new-input", emptyInput),
                values.get(InputQueueMsg.class, "started-input", emptyInput),
                values.get(InputQueueMsg.class, "finished-input", emptyInput),
                values.get(InputQueueMsg.class, "failed-input", emptyInput));
        ProgressService progress = new ProgressService(values.get(ProgressMsg.class, "progress"), buildings);

        Value<Long> tick = values.get(Long.class, "tick", 0L);
        TickCoordinator ticker = new TickCoordinator(tick, tickDuration);
        Value<ResourceSetMsg> remainder = values.get(
                ResourceSetMsg.class, "remainder", initialRemainder);
        var planet = new Planet(values.get(PlanetMsg.class, "planet",
                new PlanetMsg(planetId, null, null, null, null)),
                input, buildingService, accounting, new ProductionService(buildings, tickDuration), remainder, progress);
        ticker.add(planet);
        return new InMemoryPlanetSimulation(ticker, planet, account, userAccount, buildingService, progress,
                remainder, tick);
    }

    public Duration gameTime() {
        return ticker.tickDuration().multipliedBy(tick.get());
    }
}
