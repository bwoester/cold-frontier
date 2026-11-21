package de.bwoester.coldfrontier.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductionProgressSteps {
    private final ExtractionTestEngine engine = ServiceLoader.load(ExtractionTestEngine.class)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No extraction acceptance adapter configured"));
    private long initialIron;
    private long ironPerHour;
    private long storageCapacity;
    private Duration startTime;
    private Duration completionTime;
    private BigDecimal initialProgress;

    @Given("a colony has {long} units of iron and no other producing buildings")
    public void initialIron(long iron) {
        initialIron = iron;
    }

    @Given("a test mine produces iron at a constant rate of {long} units per hour while operational")
    public void extractionRate(long rate) {
        ironPerHour = rate;
    }

    @Given("iron storage has capacity for {long} units")
    public void storageCapacity(long capacity) {
        storageCapacity = capacity;
    }

    @Given("the simulation starts at minute {long}")
    public void startTime(long minute) {
        startTime = Duration.ofMinutes(minute);
    }

    @Given("production progress starts at {bigdecimal} units")
    public void initialProgress(BigDecimal iron) {
        initialProgress = iron;
    }

    @Given("^(?:the test mine requires no input resources for production|"
            + "no actions consume or transfer iron during the scenario|"
            + "all operating conditions remain satisfied throughout the scenario)$")
    public void isolatedExtraction() {
        // The mine fixture has no inputs, competing actions, or operating constraints.
    }

    @Given("the test mine is already completed and operational at minute {long}")
    public void completedMine(long minute) {
        assertEquals(startTime, Duration.ofMinutes(minute), "completed mine must be operational at the start");
        engine.startWithCompletedMine(mineFixture());
    }

    @Given("the test mine is under construction with effective completion at minute {long}")
    public void mineUnderConstruction(long minute) {
        completionTime = Duration.ofMinutes(minute);
    }

    @Given("construction costs have already been paid")
    public void constructionCostsPaid() {
        // The in-progress fixture starts after payment.
    }

    @Given("construction completes at minute {long} without further resource consumption")
    public void constructionCompletion(long minute) {
        assertEquals(completionTime, Duration.ofMinutes(minute), "scheduled completion time");
        engine.startWithMineUnderConstruction(mineFixture(), completionTime);
    }

    private ExtractionTestEngine.MineFixture mineFixture() {
        return new ExtractionTestEngine.MineFixture(startTime, initialIron, initialProgress, ironPerHour, storageCapacity);
    }

    @When("the simulation advances through minutes {string}")
    public void advanceThrough(String checkpoints) {
        for (String checkpoint : checkpoints.split(",")) {
            engine.advanceTo(Duration.ofMinutes(Long.parseLong(checkpoint.trim())));
        }
    }

    @Then("at minute {long} the colony has exactly {long} whole units of iron available")
    public void availableIron(long minute, long expected) {
        ExtractionTestEngine.IronState actual = engine.readIron();
        assertEquals(Duration.ofMinutes(minute), actual.gameTime(), "elapsed game time");
        assertEquals(expected, actual.available(), "available iron");
    }

    @Then("the colony retains shared iron extraction progress of {bigdecimal} iron units")
    public void retainedIron(BigDecimal expected) {
        assertEquals(expected.stripTrailingZeros(), engine.readIron().retained().stripTrailingZeros(),
                "retained iron");
    }

    @Then("the test mine is {string}")
    public void mineStatus(String status) {
        ExtractionTestEngine.MineStatus expected = switch (status) {
            case "under construction" -> ExtractionTestEngine.MineStatus.UNDER_CONSTRUCTION;
            case "operational" -> ExtractionTestEngine.MineStatus.OPERATIONAL;
            default -> throw new IllegalArgumentException("Unknown mine status: " + status);
        };
        assertEquals(expected, engine.readMineStatus(), "mine status");
    }
}
