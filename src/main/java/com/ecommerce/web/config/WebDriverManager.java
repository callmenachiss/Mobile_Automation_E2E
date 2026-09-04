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

public class WebDriverManager {

    private static final Logger LOGGER =
            LogManager.getLogger(WebDriverManager.class);

    private static final ThreadLocal<WebDriver> DRIVER =
            new ThreadLocal<>();

    private WebDriverManager() {
        // Prevent object creation
    }

    /**
     * Initialize WebDriver based on configured browser.
     */
    public static void initializeDriver(Capabilities options) {

        LOGGER.info(
                "Launching browser with capabilities: {}",
                options
        );

        WebDriver driver = createDriver(options);

        if (ConfigReader.getBoolean("web.window.maximize")) {
            driver.manage().window().maximize();
        }

        DRIVER.set(driver);

        LOGGER.info(
                "Browser launched and driver session started successfully."
        );
    }

    /**
     * Create browser driver using Boni Garcia WebDriverManager.
     */
    private static WebDriver createDriver(Capabilities options) {

        if (options instanceof ChromeOptions) {

            LOGGER.info("Starting Chrome browser.");

            io.github.bonigarcia.wdm.WebDriverManager
                    .chromedriver()
                    .setup();

            return new ChromeDriver(
                    (ChromeOptions) options
            );
        }

        if (options instanceof EdgeOptions) {

            LOGGER.info("Starting Edge browser.");

            io.github.bonigarcia.wdm.WebDriverManager
                    .edgedriver()
                    .setup();

            return new EdgeDriver(
                    (EdgeOptions) options
            );
        }

        if (options instanceof FirefoxOptions) {

            LOGGER.info("Starting Firefox browser.");

            io.github.bonigarcia.wdm.WebDriverManager
                    .firefoxdriver()
                    .setup();

            return new FirefoxDriver(
                    (FirefoxOptions) options
            );
        }

        throw new IllegalArgumentException(
                "Unsupported browser capabilities: "
                        + options.getClass().getName()
        );
    }

    /**
     * Get the current thread's WebDriver.
     */
    public static WebDriver getDriver() {

        WebDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "Driver has not been initialized. "
                            + "Is the @BeforeAll hook running?"
            );
        }

        return driver;
    }

    /**
     * Reset browser state and navigate to base URL.
     */
    public static void resetState() {

        WebDriver driver = getDriver();

        LOGGER.info("Resetting browser state.");

        driver.manage().deleteAllCookies();

        driver.get(
                ConfigReader.get("web.baseUrl")
        );
    }

    /**
     * Quit browser and remove ThreadLocal driver.
     */
    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            LOGGER.info("Closing browser session.");
            try {
                driver.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }
}