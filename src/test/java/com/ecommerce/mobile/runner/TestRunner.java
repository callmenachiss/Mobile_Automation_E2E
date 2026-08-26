package com.ecommerce.mobile.runner;

import com.ecommerce.mobile.listeners.EmailReportListener;
import com.ecommerce.mobile.listeners.RetryAnalyzer;
import com.ecommerce.mobile.listeners.RetryListener;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * The entry point that ties everything together: it tells Cucumber where
 * the .feature files and step definitions are, tells TestNG to retry any
 * failed scenario once (via RetryAnalyzer) before marking it failed,
 * cleans up the "skipped" artifact that retry leaves behind on an
 * eventual pass (via RetryListener), and emails a pass/fail summary once
 * the whole run finishes (via EmailReportListener). @Listeners is a
 * class-level annotation, so this fires no matter how the class is run -
 * mvn test, testng.xml, or IntelliJ's right-click Run/Debug.
 */
@Listeners({EmailReportListener.class, RetryListener.class})
@CucumberOptions(
        // Cucumber scans "features" recursively, so pointing this at the
        // parent "features" folder (which also contains "features/web")
        // used to sweep web-authored scenarios into the mobile run too -
        // mobile's glue can't satisfy their steps, so every mobile suite
        // (testng.xml, testng-smoke.xml, testng-regression.xml) failed
        // with UndefinedStepException as soon as a @smoke/@regression-
        // tagged web scenario existed. Scoping this to "features/mobile"
        // (mirroring "features/web") is what actually keeps the two
        // suites from depending on each other.
        features = "src/test/resources/features/mobile",
        glue = {"com.ecommerce.mobile.hooks", "com.ecommerce.mobile.stepdefinitions"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    static {
        // Keeps mobile's logs in logs/mobile-automation.log, separate from
        // web's - see log4j2.xml's ${sys:platform} fileName. Set here,
        // before anything else in the JVM touches Log4j2, so the very
        // first log line already lands in the right file.
        System.setProperty("platform", "mobile");
    }

    @Override
    @Test(dataProvider = "scenarios", retryAnalyzer = RetryAnalyzer.class)
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }
}
