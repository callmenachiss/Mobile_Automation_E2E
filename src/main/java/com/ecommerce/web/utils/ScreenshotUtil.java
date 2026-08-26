package com.ecommerce.web.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Saves a screenshot of the browser window into /screenshots/web
 * whenever a web scenario fails, so anyone can open the file and see
 * exactly what the site looked like at the moment of failure. Mobile has
 * its own independent copy (com.ecommerce.mobile.utils.ScreenshotUtil)
 * that saves under /screenshots/mobile instead, so a mobile run's
 * failures never land next to (or overwrite) web's.
 */
public class ScreenshotUtil {

    private static final Logger LOGGER = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_FOLDER = "screenshots/web";

    /**
     * @param driver       the active WebDriver
     * @param scenarioName name of the failed scenario, used to name the file
     * @return the bytes of the screenshot (also used to attach it to the HTML report)
     */
    public static byte[] captureAndSave(TakesScreenshot driver, String scenarioName) {
        byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
        try {
            Path folder = Paths.get(SCREENSHOT_FOLDER);
            Files.createDirectories(folder);

            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
            String timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss").format(new Date());
            Path screenshotFile = folder.resolve(safeName + "_" + timestamp + ".png");

            Files.write(screenshotFile, screenshotBytes);
            LOGGER.info("Failure screenshot saved: {}", screenshotFile.toAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Could not save the failure screenshot to the /{} folder.", SCREENSHOT_FOLDER, e);
        }
        return screenshotBytes;
    }
}
