package de.bwoester.coldfrontier.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;
import java.util.ServiceLoader;

import static de.bwoester.coldfrontier.acceptance.FixedUpkeepTestEngine.BuildingStatus.OPERATIONAL;
import static de.bwoester.coldfrontier.acceptance.FixedUpkeepTestEngine.BuildingStatus.UNDERSUPPLIED;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedUpkeepSteps {
    private final FixedUpkeepTestEngine engine = ServiceLoader.load(FixedUpkeepTestEngine.class)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No fixed upkeep acceptance adapter configured"));
    private Duration completionTime;
    private long fuelPerHour;
    private long energyPerHour;

    @Given("a building is due to complete at game minute {long}")
    public void completionTime(long minute) {
        completionTime = Duration.ofMinutes(minute);
    }

    @Given("it needs {long} units of fuel and {long} unit of energy per game hour while operational")
    public void upkeepRates(long fuel, long energy) {
        fuelPerHour = fuel;
        energyPerHour = energy;
    }

    @Given("the colony has {long} unit(s) of fuel and {long} unit(s) of energy before completion")
    public void initialStock(long fuel, long energy) {
        engine.initialize(completionTime, fuelPerHour, energyPerHour, fuel, energy);
    }

    @Given("^no other process changes those stocks (?:at|through) game minute \\d+$")
    public void isolatedStock() {
        // The fixture has no other stock-changing processes.
    }

    @When("the simulation advances to game minute {long}")
    public void advanceTo(long minute) {
        engine.advanceTo(Duration.ofMinutes(minute));
    }

    @Then("^the building is (?:still )?operational$")
    public void operational() {
        assertEquals(OPERATIONAL, engine.readStatus());
    }

    @Then("^the building (?:is|has completed and is) undersupplied$")
    public void undersupplied() {
        assertEquals(UNDERSUPPLIED, engine.readStatus());
    }

    @Then("the colony has {long} unit(s) of fuel and {long} unit(s) of energy")
    public void stock(long fuel, long energy) {
        assertEquals(new FixedUpkeepTestEngine.Stock(fuel, energy), engine.readStock());
    }

    @Then("the upkeep audit at game minute {long} records {long} fuel and {long} energy due and no payment")
    public void unpaidDemand(long minute, long fuel, long energy) {
        assertEquals(new FixedUpkeepTestEngine.Payment(fuel, energy, false),
                engine.readPayment(Duration.ofMinutes(minute)).orElseThrow());
    }
}
