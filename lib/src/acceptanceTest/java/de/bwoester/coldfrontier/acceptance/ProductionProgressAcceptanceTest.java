package de.bwoester.coldfrontier.acceptance;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.JUNIT_PLATFORM_SHORT_NAMING_STRATEGY_EXAMPLE_NAME_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("scenarios/production-progress.feature")
@ConfigurationParameter(key = JUNIT_PLATFORM_SHORT_NAMING_STRATEGY_EXAMPLE_NAME_PROPERTY_NAME, value = "pickle")
@IncludeTags({"operational-mine", "mine-construction"})
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "de.bwoester.coldfrontier.acceptance")
public class ProductionProgressAcceptanceTest {
}
