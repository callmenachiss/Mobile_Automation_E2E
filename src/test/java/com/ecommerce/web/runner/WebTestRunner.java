package com.ecommerce.web.runner;

import com.ecommerce.web.listeners.RetryAnalyzer;
import com.ecommerce.web.listeners.RetryListener;
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
        // Fallback default if this class is ever run without a testng.xml
        // <parameter> (e.g. IntelliJ's right-click Run on this class
        // directly). testng-web.xml's own "cucumber.filter.tags"
        // <parameter> - the same mechanism testng-smoke.xml/
        // testng-regression.xml use for mobile - is read by
        // cucumber-testng too (TestNGCucumberRunner layers it on top of
        // this annotation, so it wins whenever both are present) and is
        // the actual filter in effect for a normal `mvn test
        // -DsuiteXmlFile=testng-web.xml` run.
        tags = "@web",
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class WebTestRunner extends AbstractTestNGCucumberTests {

    static {
        // Keeps web's logs and archived Extent report snapshots separate
        // from mobile's, without touching extent.properties/log4j2.xml
        // defaults that mobile relies on - both log4j2's ${sys:platform}
        // lookup and ExtentService's basefolder.name resolution check
        // System properties before falling back to their file/config
        // default, and this static block runs before anything else in
        // the JVM touches either one.
        //
        // NOTE: extent.reporter.spark.out/spark.config can NOT be
        // overridden this way, despite looking like they should be -
        // the adapter resolves those two specific keys straight from the
        // loaded extent.properties file (see initSpark/getOutputPath in
        // ExtentService, verified by decompiling the adapter jar),
        // ignoring any system property for them. Don't re-add overrides
        // for those two keys here; they're silently no-ops. See
        // extent.properties for how mobile/web reports actually get told
        // apart instead (by basefolder.name below, not by filename).
        System.setProperty("platform", "web");
        System.setProperty("basefolder.name", "test-output/WebExtentReport");
    }

    @Override
    @Test(dataProvider = "scenarios", retryAnalyzer = RetryAnalyzer.class)
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }
}
