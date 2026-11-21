package de.bwoester.coldfrontier.prototype.characterization;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.JUNIT_PLATFORM_NAMING_STRATEGY_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("prototype/characterization/production-tick.feature")
@ConfigurationParameter(key = JUNIT_PLATFORM_NAMING_STRATEGY_PROPERTY_NAME, value = "long")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "de.bwoester.coldfrontier.prototype.characterization")
public class ProductionTickCharacterizationTest {
}
