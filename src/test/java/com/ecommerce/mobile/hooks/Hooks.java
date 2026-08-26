package com.ecommerce.mobile.hooks;

import com.ecommerce.core.utils.ScreenshotUtil;
import com.ecommerce.mobile.config.AppiumServerManager;
import com.ecommerce.mobile.config.CapabilityManager;
import com.ecommerce.mobile.config.DriverManager;
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
 * Runs once for the whole suite and before/after every scenario.
 *  - BeforeAll: starts the Appium server and the app driver session ONCE.
 *  - Before: resets the app to a clean state by restarting its process -
 *            much cheaper than creating a brand-new driver session.
 *  - After: if the scenario failed, saves a screenshot to /screenshots
 *           and attaches it to the HTML report.
 *  - AfterAll: closes the app session and stops the Appium server.
 *
 * The driver session is intentionally NOT recreated per scenario: a new
 * UiAutomator2 session is far more expensive than restarting the app process,
 * and app.noReset=true already means app data isn't cleared between scenarios
 * either way.
 */
public class Hooks {

    private static final Logger LOGGER = LogManager.getLogger(Hooks.class);
    private static final AppiumServerManager SERVER_MANAGER = new AppiumServerManager();

    @BeforeAll
    public static void setUp() throws MalformedURLException {
        long serverStart = System.currentTimeMillis();
        SERVER_MANAGER.startServer();
        long serverElapsedMs = System.currentTimeMillis() - serverStart;
        LOGGER.info("Appium server ready in {} ms", serverElapsedMs);

        URL serverUrl = new URL(SERVER_MANAGER.getServerUrl());
        long sessionStart = System.currentTimeMillis();
        DriverManager.initializeDriver(serverUrl, new CapabilityManager().getCapabilities());
        long sessionElapsedMs = System.currentTimeMillis() - sessionStart;
        LOGGER.info("Driver session (device init + UiAutomator2 install/launch + app launch) ready in {} ms", sessionElapsedMs);
    }

    @AfterAll
    public static void tearDownAll() {
        DriverManager.quitDriver();
        SERVER_MANAGER.stopServer();
    }

    @Before
    public void resetAppState(Scenario scenario) {
        LOGGER.info("========== Starting scenario: {} ==========", scenario.getName());
        DriverManager.resetApp();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            LOGGER.error("Scenario FAILED: {}", scenario.getName());
            AndroidDriver driver = DriverManager.getDriver();
            byte[] screenshot = ScreenshotUtil.captureAndSave(driver, scenario.getName(), "mobile");
            scenario.attach(screenshot, "image/png", scenario.getName());
        } else {
            LOGGER.info("Scenario PASSED: {}", scenario.getName());
        }
        LOGGER.info("========== Finished scenario: {} ==========", scenario.getName());
    }
}
