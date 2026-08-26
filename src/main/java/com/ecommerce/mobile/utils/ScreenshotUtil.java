package com.ecommerce.mobile.utils;

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
 * Saves a screenshot of the device screen into /screenshots/mobile
 * whenever a mobile scenario fails, so anyone can open the file and see
 * exactly what the app looked like at the moment of failure. Web has its
 * own independent copy (com.ecommerce.web.utils.ScreenshotUtil) that
 * saves under /screenshots/web instead, so a web run's failures never
 * land next to (or overwrite) mobile's.
 */
public class ScreenshotUtil {

    private static final Logger LOGGER = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_FOLDER = "screenshots/mobile";

    /**
     * @param driver       the active AndroidDriver
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
