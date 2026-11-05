package de.bwoester.coldfrontier.production;

import de.bwoester.coldfrontier.TickCoordinator;
import de.bwoester.coldfrontier.accounting.AccountingService;
import de.bwoester.coldfrontier.accounting.Ledger;
import de.bwoester.coldfrontier.accounting.PlanetAccount;
import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.TransactionMsg;
import de.bwoester.coldfrontier.accounting.UserAccount;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.BuildingCountersMsg;
import de.bwoester.coldfrontier.buildings.BuildingDataProvider;
import de.bwoester.coldfrontier.buildings.BuildingService;
import de.bwoester.coldfrontier.buildings.ConstructionQueueMsg;
import de.bwoester.coldfrontier.buildings.StaticBuildingDataProvider;
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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayDeque;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductionTickSteps {

    private TickCoordinator ticker;
    private PlanetAccount account;

    @Given("a prototype planet has {long} energy and one iron mine")
    public void preparePlanet(long energy) {
        String planetId = "test-planet";
        InMemoryDataAccess data = new InMemoryDataAccess();
        InMemoryValueRepository values = new InMemoryValueRepository(data.getData(), new EventFactory(() -> 0L));
        Value<PlanetResourceSetMsg> balance = values.get(
                PlanetResourceSetMsg.class, "balance", new PlanetResourceSetMsg(0, energy, 0));
        account = new PlanetAccount(balance);
        Ledger ledger = new Ledger(new UserAccount(values.get(Long.class, "credits", 0L)),
                Map.of(planetId, account));
        AccountingService accounting = new AccountingService(ledger,
                values.get(TransactionMsg.class, "transactions"));

        BuildingDataProvider buildings = new StaticBuildingDataProvider();
        BuildingService buildingService = new BuildingService(values.get(
                BuildingCountersMsg.class, "buildings",
                new BuildingCountersMsg(Map.of(Building.IRON_MINE, 1L))),
                values.get(ConstructionQueueMsg.class, "queue", new ConstructionQueueMsg(new ArrayDeque<>())),
                buildings);

        InputQueueMsg emptyInput = new InputQueueMsg(new ArrayDeque<>());
        InputService input = new InputService(
                values.get(InputQueueMsg.class, "new-input", emptyInput),
                values.get(InputQueueMsg.class, "started-input", emptyInput),
                values.get(InputQueueMsg.class, "finished-input", emptyInput),
                values.get(InputQueueMsg.class, "failed-input", emptyInput));
        ProgressService progress = new ProgressService(values.get(ProgressMsg.class, "progress"), buildings);

        ticker = new TickCoordinator(values.get(Long.class, "tick", 0L));
        ticker.add(new Planet(values.get(PlanetMsg.class, "planet",
                new PlanetMsg(planetId, null, null, null, null)),
                input, buildingService, accounting, new ProductionService(buildings), progress));
    }

    @When("the ticker advances through ticks {string}")
    public void advanceThroughTicks(String checkpoints) {
        for (String checkpoint : checkpoints.split(",")) {
            ticker.advanceTo(Long.parseLong(checkpoint.trim()));
        }
    }

    @Then("the planet has {long} iron and {long} energy")
    public void verifyBalance(long iron, long energy) {
        PlanetResourceSetMsg balance = account.getBalance();
        assertEquals(iron, balance.iron());
        assertEquals(energy, balance.energy());
    }
}
