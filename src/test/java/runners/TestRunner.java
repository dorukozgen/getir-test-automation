package runners;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.junit.platform.engine.Constants;
import managers.DriverManagement;
import org.junit.platform.suite.api.*;
import types.DriverTypes;

import java.io.IOException;
import java.net.MalformedURLException;

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameters({
        @ConfigurationParameter(
                key = Constants.GLUE_PROPERTY_NAME,
                value = "steps,runners"
        ),
        @ConfigurationParameter(
                key = Constants.FEATURES_PROPERTY_NAME,
                value = "src/test/resources/features"
        ),
        @ConfigurationParameter(
                key = Constants.PLUGIN_PROPERTY_NAME,
                value = "pretty,html:test-output/cucumber-report.html,json:test-output/cucumber-report.json"
        ),
        @ConfigurationParameter(
                key = Constants.FILTER_TAGS_PROPERTY_NAME,
                value = "@desktop or @mobile"
        )
        // @ConfigurationParameter(
        //         key = Constants.PLUGIN_PROPERTY_NAME,
        //         value = "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        // )
})
public class TestRunner {

    @Before
    public void before(Scenario scenario) throws MalformedURLException {
        if (scenario.getSourceTagNames().contains("@desktop")) {
            DriverManagement.setDriver(DriverTypes.DESKTOP_CHROME);
        } else if (scenario.getSourceTagNames().contains("@mobile")) {
            DriverManagement.setDriver(DriverTypes.MOBILE_ANDROID);
        }
    }

    @After
    public void afterAll(Scenario scenario) throws IOException {
        if (scenario.getSourceTagNames().contains("@desktop")) {
            DriverManagement.teardown(DriverTypes.DESKTOP_CHROME);
        } else if (scenario.getSourceTagNames().contains("@mobile")) {
            DriverManagement.teardown(DriverTypes.MOBILE_ANDROID);
        }
    }

}
