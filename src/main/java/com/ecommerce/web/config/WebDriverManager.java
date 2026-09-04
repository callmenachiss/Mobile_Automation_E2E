package com.ecommerce.web.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WebDriverManager {

    private static final Logger LOGGER =
            LogManager.getLogger(WebDriverManager.class);

    private static final ThreadLocal<WebDriver> DRIVER =
            new ThreadLocal<>();

    public static WebDriver initializeDriver(Capabilities options)
            throws MalformedURLException {

        boolean remote =
                ConfigReader.getBoolean("web.remote.enabled");

        LOGGER.info(
                "Launching browser with capabilities: {} (remote={})",
                options,
                remote
        );

        WebDriver driver =
                remote
                        ? newRemoteDriver(options)
                        : newLocalDriver(options);

        if (ConfigReader.getBoolean("web.window.maximize")) {
            driver.manage().window().maximize();
        }

        DRIVER.set(driver);

        LOGGER.info(
                "Browser launched and driver session started successfully."
        );

        return driver;
    }

    /**
     * Creates a local browser driver.
     */
    private static WebDriver newLocalDriver(Capabilities options) {

        // Firefox
        if (options instanceof FirefoxOptions) {
            return new FirefoxDriver((FirefoxOptions) options);
        }

        // Edge
        if (options instanceof EdgeOptions) {
            return new EdgeDriver((EdgeOptions) options);
        }

        // Chrome
        ChromeOptions chromeOptions =
                (ChromeOptions) options;

        Map<String, Object> preferences =
                new HashMap<>();

        /*
         * Chrome Geolocation Permission
         *
         * 1 = Allow
         * 2 = Block
         *
         * Block location so the browser does not show
         * the location permission prompt during automation.
         */
        preferences.put(
                "profile.default_content_setting_values.geolocation",
                2
        );

        chromeOptions.setExperimentalOption(
                "prefs",
                preferences
        );

        return new ChromeDriver(chromeOptions);
    }

    /**
     * Creates a remote browser driver.
     */
    private static WebDriver newRemoteDriver(Capabilities options)
            throws MalformedURLException {

        URL gridUrl =
                new URL(ConfigReader.get("web.remote.url"));

        LOGGER.info(
                "Connecting to remote Selenium server at {}",
                gridUrl
        );

        return new RemoteWebDriver(
                gridUrl,
                options
        );
    }

    /**
     * Returns the current WebDriver instance.
     */
    public static WebDriver getDriver() {

        WebDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "Driver has not been initialized. " +
                            "Is the @BeforeAll hook running?"
            );
        }

        return driver;
    }

    /**
     * Resets the browser state between scenarios.
     */
    public static void resetState() {

        WebDriver driver = getDriver();

        driver.manage().deleteAllCookies();

        driver.get(
                ConfigReader.get("web.baseUrl")
        );
    }

    /**
     * Closes the browser and removes the ThreadLocal driver.
     */
    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            LOGGER.info("Closing browser session.");
            driver.quit();
            DRIVER.remove();
        }
    }
}