package com.ecommerce.mobile.hooks;

import com.ecommerce.mobile.config.AppiumServerManager;
import com.ecommerce.mobile.config.CapabilityManager;
import com.ecommerce.mobile.config.DriverManager;
import com.ecommerce.mobile.utils.ScreenshotUtil;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Runs before/after every single scenario.
 *  - Before: starts the app on the device.
 *  - After: if the scenario failed, saves a screenshot to /screenshots
 *           and attaches it to the HTML report, then closes the app.
 *
 * The Appium server itself is started once for the whole test run
 * (@BeforeAll/@AfterAll), not once per scenario.
 */
public class Hooks {

    private static final Logger LOGGER = LogManager.getLogger(Hooks.class);
    private static final AppiumServerManager SERVER_MANAGER = new AppiumServerManager();
    private final DriverManager driverManager = new DriverManager();

    @BeforeAll
    public static void startAppiumServer() {
        SERVER_MANAGER.startServer();
    }

    @AfterAll
    public static void stopAppiumServer() {
        SERVER_MANAGER.stopServer();
    }

    @Before
    public void launchApp(Scenario scenario) throws MalformedURLException {
        LOGGER.info("========== Starting scenario: {} ==========", scenario.getName());
        URL serverUrl = new URL(SERVER_MANAGER.getServerUrl());
        driverManager.initializeDriver(serverUrl, new CapabilityManager().getCapabilities());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            LOGGER.error("Scenario FAILED: {}", scenario.getName());
            AndroidDriver driver = DriverManager.getDriver();
            byte[] screenshot = ScreenshotUtil.captureAndSave(driver, scenario.getName());
            scenario.attach(screenshot, "image/png", scenario.getName());
        } else {
            LOGGER.info("Scenario PASSED: {}", scenario.getName());
        }
        driverManager.quitDriver();
        LOGGER.info("========== Finished scenario: {} ==========", scenario.getName());
    }
}
