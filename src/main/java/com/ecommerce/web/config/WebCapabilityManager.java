package com.ecommerce.web.config;

import com.ecommerce.mobile.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.MutableCapabilities;

/**
 * Builds the browser options from config.properties, the same way
 * CapabilityManager builds Appium capabilities for mobile.
 *
 * web.browser picks the browser (chrome/firefox); web.headless runs it
 * without a visible window (handy for CI). No driver-binary management is
 * needed - Selenium Manager (bundled since Selenium 4.6) downloads the
 * matching chromedriver/geckodriver automatically.
 */
public class WebCapabilityManager {

    private static final Logger LOGGER = LogManager.getLogger(WebCapabilityManager.class);

    public MutableCapabilities getCapabilities() {
        String browser = ConfigReader.get("web.browser", "chrome").toLowerCase();
        boolean headless = ConfigReader.getBoolean("web.headless");

        LOGGER.info("Building web capabilities: browser={}, headless={}", browser, headless);

        switch (browser) {
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                return options;
            }
            case "chrome":
            default: {
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                options.addArguments("--remote-allow-origins=*");
                return options;
            }
        }
    }
}
