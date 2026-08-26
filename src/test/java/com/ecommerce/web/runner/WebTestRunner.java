package com.ecommerce.web.runner;

import com.ecommerce.mobile.listeners.RetryAnalyzer;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.Test;

/**
 * The web counterpart of the mobile TestRunner: ties Cucumber to the web
 * feature files and web step definitions, and retries any failed scenario
 * once (via the same RetryAnalyzer mobile uses) before marking it failed.
 */
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = {"com.ecommerce.web.hooks", "com.ecommerce.web.stepdefinitions"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class WebTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @Test(dataProvider = "scenarios", retryAnalyzer = RetryAnalyzer.class)
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }
}
