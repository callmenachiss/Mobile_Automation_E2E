package com.ecommerce.web.hooks;

import com.ecommerce.web.config.WebCapabilityManager;
import com.ecommerce.web.config.WebDriverManager;
import com.ecommerce.web.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class WebHooks {

    private static final Logger LOGGER =
            LogManager.getLogger(WebHooks.class);

    @BeforeAll
    public static void setUp() {

        long start = System.currentTimeMillis();

        WebDriverManager.initializeDriver(
                WebCapabilityManager.buildCapabilities()
        );

        LOGGER.info(
                "Browser ready in {} ms",
                System.currentTimeMillis() - start
        );
    }

    @AfterAll
    public static void tearDownAll() {

        WebDriverManager.quitDriver();
    }

    @Before
    public void resetState(Scenario scenario) {

        LOGGER.info(
                "========== Starting scenario: {} ==========",
                scenario.getName()
        );

        WebDriverManager.resetState();
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {

            LOGGER.error(
                    "Scenario FAILED: {}",
                    scenario.getName()
            );

            WebDriver driver =
                    WebDriverManager.getDriver();

            byte[] screenshot =
                    ScreenshotUtil.captureAndSave(
                            (TakesScreenshot) driver,
                            scenario.getName()
                    );

            scenario.attach(
                    screenshot,
                    "image/png",
                    scenario.getName()
            );

        } else {

            LOGGER.info(
                    "Scenario PASSED: {}",
                    scenario.getName()
            );
        }

        LOGGER.info(
                "========== Finished scenario: {} ==========",
                scenario.getName()
        );
    }
}