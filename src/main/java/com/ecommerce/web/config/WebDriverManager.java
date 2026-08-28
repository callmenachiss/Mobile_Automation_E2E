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
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverManager {

    private static final Logger LOGGER = LogManager.getLogger(WebDriverManager.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static WebDriver initializeDriver(Capabilities options) throws MalformedURLException {
        boolean remote = ConfigReader.getBoolean("web.remote.enabled");
        LOGGER.info("Launching browser with capabilities: {} (remote={})", options, remote);

        WebDriver driver = remote ? newRemoteDriver(options) : newLocalDriver(options);

        if (ConfigReader.getBoolean("web.window.maximize")) {
            driver.manage().window().maximize();
        }

        DRIVER.set(driver);
        LOGGER.info("Browser launched and driver session started successfully.");
        return driver;
    }

    private static WebDriver newLocalDriver(Capabilities options) {
        if (options instanceof FirefoxOptions) {
            return new FirefoxDriver((FirefoxOptions) options);
        }
        if (options instanceof EdgeOptions) {
            return new EdgeDriver((EdgeOptions) options);
        }
        return new ChromeDriver((ChromeOptions) options);
    }

    private static WebDriver newRemoteDriver(Capabilities options) throws MalformedURLException {
        URL gridUrl = new URL(ConfigReader.get("web.remote.url"));
        LOGGER.info("Connecting to remote Selenium server at {}", gridUrl);
        return new RemoteWebDriver(gridUrl, options);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialized. Is the @BeforeAll hook running?");
        }
        return driver;
    }

    /**
     * Returns to a clean, known state between scenarios by navigating back
     * to the base URL and clearing cookies - much cheaper than closing and
     * relaunching the browser (the web equivalent of the mobile
     * DriverManager.resetApp()).
     */
    public static void resetState() {
        WebDriver driver = getDriver();
        driver.manage().deleteAllCookies();
        driver.get(ConfigReader.get("web.baseUrl"));
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            LOGGER.info("Closing browser session.");
            driver.quit();
            DRIVER.remove();
        }
    }
}
