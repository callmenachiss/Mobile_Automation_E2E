package com.ecommerce.mobile.runner;

import com.ecommerce.mobile.listeners.EmailReportListener;
import com.ecommerce.mobile.listeners.RetryAnalyzer;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * The entry point that ties everything together: it tells Cucumber where
 * the .feature files and step definitions are, tells TestNG to retry any
 * failed scenario once (via RetryAnalyzer) before marking it failed, and
 * emails a pass/fail summary once the whole run finishes (via
 * EmailReportListener). @Listeners is a class-level annotation, so this
 * fires no matter how the class is run - mvn test, testng.xml, or
 * IntelliJ's right-click Run/Debug.
 */
@Listeners(EmailReportListener.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.ecommerce.mobile.hooks", "com.ecommerce.mobile.stepdefinitions"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @Test(dataProvider = "scenarios", retryAnalyzer = RetryAnalyzer.class)
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }
}
