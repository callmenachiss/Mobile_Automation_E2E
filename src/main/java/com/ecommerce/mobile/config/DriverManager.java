package com.ecommerce.mobile.config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Owns the AndroidDriver (our remote control for the app on the device).
 * Kept as a ThreadLocal so this same framework can later run tests in
 * parallel across multiple devices without one test stealing another's driver.
 *
 * The driver session is created once for the whole suite and reused across
 * scenarios (see Hooks) - creating a new UiAutomator2 session per scenario is
 * far more expensive than restarting the app process within an existing one.
 */
public class DriverManager {

    private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    public static AndroidDriver initializeDriver(URL appiumServerUrl, UiAutomator2Options options) {
        LOGGER.info("Initializing AndroidDriver against Appium server at {}", appiumServerUrl);
        AndroidDriver driver = new AndroidDriver(appiumServerUrl, options);
        DRIVER.set(driver);
        LOGGER.info("App launched and driver session started successfully.");
        return driver;
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialized. Is the @BeforeAll hook running?");
        }
        return driver;
    }

    /**
     * Restarts the app process (not the driver session) so each scenario starts
     * from a clean screen without paying the cost of a brand-new Appium session.
     */
    public static void resetApp() {
        AndroidDriver driver = getDriver();
        String appPackage = ConfigReader.get("appPackage");
        String appActivity = ConfigReader.get("appActivity");
        LOGGER.info("Resetting app state by restarting {}", appPackage);
        driver.terminateApp(appPackage);
        // activateApp(appPackage) makes Appium re-resolve the launchable activity
        // via the device's package manager on every call, which intermittently
        // fails with "Unable to resolve the launchable activity... No activity
        // found" and takes down every scenario since this runs in @Before. Naming
        // the known activity directly skips that resolution step entirely.
        Map<String, Object> startActivityArgs = new HashMap<>();
        startActivityArgs.put("appPackage", appPackage);
        startActivityArgs.put("appActivity", appActivity);
        driver.executeScript("mobile: startActivity", startActivityArgs);
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver != null) {
            LOGGER.info("Closing app session.");
            driver.quit();
            DRIVER.remove();
        }
    }
}
