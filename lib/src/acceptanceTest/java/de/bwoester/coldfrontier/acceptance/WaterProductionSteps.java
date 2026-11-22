package de.bwoester.coldfrontier.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WaterProductionSteps {
    private final WaterProcessingTestEngine engine = ServiceLoader.load(WaterProcessingTestEngine.class)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No water processing acceptance adapter configured"));

    private long ice;
    private long energy;
    private long water;
    private long waterStorage;
    private long waterPerHour;
    private long icePerUnit;
    private long energyPerUnit;

    @Given("a planet is prepared with {long} units of ice and {long} units of water before production starts")
    public void prepareStock(long initialIce, long initialWater) {
        ice = initialIce;
        water = initialWater;
    }

    @Given("its water plant produces {long} units of water per hour when fully supplied")
    public void setProductionRate(long rate) {
        waterPerHour = rate;
    }

    @Given("each unit of water requires {long} unit(s) of ice and {long} unit(s) of energy")
    public void setInputs(long ice, long energy) {
        icePerUnit = ice;
        energyPerUnit = energy;
    }

    @Given("the planet has {long} units of energy before production starts")
    public void setInitialEnergy(long initialEnergy) {
        energy = initialEnergy;
    }

    @Given("water storage has capacity for {long} units")
    public void setWaterStorage(long capacity) {
        waterStorage = capacity;
    }

    @Given("no other processes change these stocks")
    public void useIsolatedColony() {
        // The fixture supplies only the water plant.
    }

    @Given("the simulation is initialized at minute 0")
    public void initialize() {
        engine.initialize(new WaterProcessingTestEngine.WaterFixture(
                ice, energy, water, waterStorage, waterPerHour, icePerUnit, energyPerUnit));
    }

    @Then("at minute 0 the pool contains {long} units of ice and no water")
    public void checkOpeningStock(long remainingIce) {
        var state = engine.readState();
        assertEquals(remainingIce, state.ice(), "remaining ice");
        assertEquals(0, state.water(), "available water");
    }

    @Then("the water plant has taken {long} units of energy from the pool")
    public void checkEnergyConsumed(long consumed) {
        assertEquals(energy - consumed, engine.readState().energy(), "remaining energy");
    }

    @Then("^(?:the first|a) unit of water is in production$")
    public void checkInProgress() {
        assertTrue(engine.readState().unitInProgress(), "a unit of water is in progress");
    }

    @When("water production advances to minute {long}")
    public void advanceTo(long minute) {
        engine.advanceTo(Duration.ofMinutes(minute));
    }

    @Given("a supply of {long} unit(s) of energy becomes available in the pool at minute {long}")
    public void scheduleEnergySupply(long units, long minute) {
        engine.scheduleEnergySupply(units, Duration.ofMinutes(minute));
    }

    @When("water production advances to minute {long} and second {long}")
    public void advanceTo(long minute, long second) {
        engine.advanceTo(Duration.ofMinutes(minute).plusSeconds(second));
    }

    @Then("the pool contains {long} unit(s) of water, {long} unit(s) of ice and {long} unit(s) of energy")
    public void checkStocks(long water, long ice, long energy) {
        var state = engine.readState();
        assertEquals(water, state.water(), "available water");
        assertEquals(ice, state.ice(), "remaining ice");
        assertEquals(energy, state.energy(), "remaining energy");
    }

    @Then("the pool contains {long} unit(s) of water")
    public void checkWater(long available) {
        assertEquals(available, engine.readState().water(), "available water");
    }

    @When("water production advances through minutes {string}")
    public void advanceThrough(String checkpoints) {
        for (String checkpoint : checkpoints.split(",")) {
            advanceTo(Long.parseLong(checkpoint.trim()));
        }
    }

    @Then("the pool contains {long} units of water and no ice")
    public void checkWaterAndEmptyIceStock(long available) {
        var state = engine.readState();
        assertEquals(available, state.water(), "available water");
        assertEquals(0, state.ice(), "remaining ice");
    }

    @Then("a unit of water is in production with {double} progress")
    public void checkProgress(double expected) {
        var progress = engine.readState().progressFraction();
        assertTrue(progress.isPresent(), "a unit of water is in progress");
        assertEquals(expected, progress.getAsDouble(), 1e-12, "fraction completed");
    }

    @Then("the water plant is no longer producing")
    public void checkStopped() {
        assertFalse(engine.readState().unitInProgress(), "no unit of water is in progress");
    }
}
