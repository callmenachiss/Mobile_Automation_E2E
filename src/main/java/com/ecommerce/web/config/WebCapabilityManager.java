package com.ecommerce.web.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebCapabilityManager {

    private static final Logger LOGGER =
            LogManager.getLogger(WebCapabilityManager.class);

    public MutableCapabilities getCapabilities() {

        String browser = ConfigReader
                .get("web.browser", "chrome")
                .toLowerCase();

        boolean headless = ConfigReader.getBoolean("web.headless");

        LOGGER.info(
                "Building web capabilities: browser={}, headless={}",
                browser,
                headless
        );

        switch (browser) {

            case "firefox": {

                FirefoxOptions options = new FirefoxOptions();

                if (headless) {
                    options.addArguments("--headless");
                }

                options.addArguments("--window-size=1920,1080");

                return options;
            }

            case "edge": {

                EdgeOptions options = new EdgeOptions();

                if (headless) {
                    options.addArguments("--headless=new");
                }

                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");

                return options;
            }

            case "chrome":
            default: {

                ChromeOptions options = new ChromeOptions();

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
        }
    }
}