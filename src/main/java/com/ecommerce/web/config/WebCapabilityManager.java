package com.ecommerce.web.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WebCapabilityManager {

    private static final Logger LOGGER =
            LogManager.getLogger(WebCapabilityManager.class);

    private WebCapabilityManager() {
        // Utility class
    }

    public static Capabilities buildCapabilities() {

        String browser = ConfigReader.get("web.browser");
        boolean headless = ConfigReader.getBoolean("web.headless");

        if (browser == null || browser.isBlank()) {
            throw new IllegalArgumentException(
                    "web.browser is not configured."
            );
        }

        LOGGER.info(
                "Building web capabilities: browser={}, headless={}",
                browser,
                headless
        );

        switch (browser.toLowerCase(Locale.ROOT)) {

            case "chrome":
                return buildChromeCapabilities(headless);

            case "edge":
                return buildEdgeCapabilities(headless);

            case "firefox":
                return buildFirefoxCapabilities(headless);

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                                + ". Supported browsers: chrome, edge, firefox"
                );
        }
    }

    private static ChromeOptions buildChromeCapabilities(
            boolean headless) {

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> preferences = new HashMap<>();

        // 1 = Allow
        // 2 = Block
        preferences.put(
                "profile.default_content_setting_values.geolocation",
                2
        );

        options.setExperimentalOption(
                "prefs",
                preferences
        );

        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        return options;
    }

    private static EdgeOptions buildEdgeCapabilities(
            boolean headless) {

        EdgeOptions options = new EdgeOptions();

        Map<String, Object> preferences = new HashMap<>();

        // Block location permission popup
        preferences.put(
                "profile.default_content_setting_values.geolocation",
                2
        );

        options.setExperimentalOption(
                "prefs",
                preferences
        );

        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        return options;
    }

    private static FirefoxOptions buildFirefoxCapabilities(
            boolean headless) {

        FirefoxOptions options = new FirefoxOptions();

        // Block location permission
        options.addPreference(
                "permissions.default.geo",
                2
        );

        if (headless) {
            options.addArguments("--headless");
        }

        return options;
    }
}