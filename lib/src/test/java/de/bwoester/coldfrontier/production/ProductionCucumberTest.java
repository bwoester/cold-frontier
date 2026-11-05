package de.bwoester.coldfrontier.production;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("characterization/production-tick.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "de.bwoester.coldfrontier.production")
public class ProductionCucumberTest {
}
