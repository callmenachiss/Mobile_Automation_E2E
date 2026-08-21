package com.ecommerce.mobile.config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

/**
 * Owns the AndroidDriver (our remote control for the app on the device).
 * Kept as a ThreadLocal so this same framework can later run tests in
 * parallel across multiple devices without one test stealing another's driver.
 */
public class DriverManager {

    private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    public AndroidDriver initializeDriver(URL appiumServerUrl, UiAutomator2Options options) {
        LOGGER.info("Initializing AndroidDriver against Appium server at {}", appiumServerUrl);
        AndroidDriver driver = new AndroidDriver(appiumServerUrl, options);
        DRIVER.set(driver);
        LOGGER.info("App launched and driver session started successfully.");
        return driver;
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialized. Is the @Before hook running?");
        }
        return driver;
    }

    public void quitDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver != null) {
            LOGGER.info("Closing app session.");
            driver.quit();
            DRIVER.remove();
        }
    }
}
