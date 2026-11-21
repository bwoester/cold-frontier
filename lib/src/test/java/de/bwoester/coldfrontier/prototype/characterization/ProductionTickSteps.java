package de.bwoester.coldfrontier.prototype.characterization;

import de.bwoester.coldfrontier.accounting.PlanetResourceSetMsg;
import de.bwoester.coldfrontier.accounting.ResourceSetMsg;
import de.bwoester.coldfrontier.buildings.Building;
import de.bwoester.coldfrontier.buildings.StaticBuildingDataProvider;
import de.bwoester.coldfrontier.prototype.testing.InMemoryPlanetSimulation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductionTickSteps {

    private InMemoryPlanetSimulation simulation;

    @Given("a prototype planet has {long} energy and one iron mine")
    public void preparePlanet(long energy) {
        preparePlanet(energy, Duration.ofSeconds(1), true);
    }

    @Given("a prototype planet has {long} energy and one iron mine at a tick duration of {long} ms")
    public void preparePlanetWithTickDuration(long energy, long milliseconds) {
        preparePlanet(energy, Duration.ofMillis(milliseconds), true);
    }

    @Given("a prototype planet has {long} energy and a queued iron mine at a tick duration of {long} ms")
    public void preparePlanetWithQueuedMine(long energy, long milliseconds) {
        preparePlanet(energy, Duration.ofMillis(milliseconds), false);
        simulation.buildingService().addToConstructionQueue(Building.IRON_MINE);
    }

    private void preparePlanet(long energy, Duration tickDuration, boolean completedMine) {
        simulation = InMemoryPlanetSimulation.create(tickDuration, new StaticBuildingDataProvider(),
                completedMine ? Map.of(Building.IRON_MINE, 1L) : Map.of(),
                new PlanetResourceSetMsg(0, energy, 0), ResourceSetMsg.createDefault());
    }

    @When("the ticker advances through ticks {string}")
    public void advanceThroughTicks(String checkpoints) {
        for (String checkpoint : checkpoints.split(",")) {
            simulation.ticker().advanceTo(Long.parseLong(checkpoint.trim()));
        }
    }

    @When("the ticker advances to game time {long} ms")
    public void advanceToGameTime(long milliseconds) {
        simulation.ticker().advanceTo(Duration.ofMillis(milliseconds));
    }

    @Then("advancing to game time {long} ms is rejected")
    public void rejectGameTime(long milliseconds) {
        assertThrows(IllegalArgumentException.class, () -> advanceToGameTime(milliseconds));
    }

    @And("the planet has {long} completed iron mines")
    public void verifyBuildingCount(long count) {
        assertEquals(count, simulation.buildingService().getBuildings().counters().getOrDefault(Building.IRON_MINE, 0L));
    }

    @Then("the planet has {long} iron and {long} energy")
    public void verifyBalance(long iron, long energy) {
        PlanetResourceSetMsg balance = simulation.account().getBalance();
        assertEquals(iron, balance.iron());
        assertEquals(energy, balance.energy());
    }
}
