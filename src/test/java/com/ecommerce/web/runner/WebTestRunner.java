package com.ecommerce.web.runner;

import com.ecommerce.core.listeners.RetryAnalyzer;
import com.ecommerce.core.listeners.RetryListener;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * The web counterpart of the mobile TestRunner: ties Cucumber to the web
 * feature files and web step definitions, retries any failed scenario
 * once (via the same RetryAnalyzer mobile uses) before marking it
 * failed, and cleans up the "skipped" artifact retry leaves behind on an
 * eventual pass (via RetryListener).
 */
@Listeners(RetryListener.class)
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = {"com.ecommerce.web.hooks", "com.ecommerce.web.stepdefinitions"},
        // testng-web.xml's own "cucumber.filter.tags" <parameter> is a
        // TestNG concept, not a Cucumber one - cucumber-testng never reads
        // it, so it silently filtered nothing and every scenario under
        // features/web (including untagged/unrelated ones) ran on every
        // "web" suite execution. Declaring the filter here is the only
        // place cucumber-testng actually honors it.
        tags = "@web",
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class WebTestRunner extends AbstractTestNGCucumberTests {

    static {
        // Keeps the web run's Extent report, its archived snapshots and
        // its logs completely separate from mobile's, without touching
        // extent.properties / log4j2.xml defaults that mobile relies on:
        // both ExtentService and log4j2's ${sys:platform} lookup check
        // System properties before falling back to their own defaults,
        // and this static block runs before anything else in the JVM
        // touches either one.
        System.setProperty("platform", "web");
        System.setProperty("extent.reporter.spark.out", "reports/EcommerceWebAutomationReport.html");
        System.setProperty("extent.reporter.spark.config", "src/test/resources/extent-config-web.xml");
        System.setProperty("basefolder.name", "test-output/WebExtentReport");
    }

    @Override
    @Test(dataProvider = "scenarios", retryAnalyzer = RetryAnalyzer.class)
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }
}
