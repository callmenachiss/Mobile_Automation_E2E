package com.ecommerce.core.utils;

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
 * Saves a screenshot (device screen or browser window) into
 * /screenshots/&lt;platform&gt; whenever a scenario fails, so anyone can
 * open the file and see exactly what the app looked like at the moment
 * of failure. Shared by both mobile (AndroidDriver) and web (WebDriver) -
 * both implement TakesScreenshot - but each platform's failures land in
 * their own subfolder so a web run's screenshots aren't mixed in with
 * mobile's (and vice versa).
 */
public class ScreenshotUtil {

    private static final Logger LOGGER = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_FOLDER = "screenshots";

    /**
     * @param driver       the active driver (AndroidDriver or WebDriver)
     * @param scenarioName name of the failed scenario, used to name the file
     * @param platform     subfolder to save under, e.g. "mobile" or "web"
     * @return the bytes of the screenshot (also used to attach it to the HTML report)
     */
    public static byte[] captureAndSave(TakesScreenshot driver, String scenarioName, String platform) {
        byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
        try {
            Path folder = Paths.get(SCREENSHOT_FOLDER, platform);
            Files.createDirectories(folder);

            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
            String timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss").format(new Date());
            Path screenshotFile = folder.resolve(safeName + "_" + timestamp + ".png");

            Files.write(screenshotFile, screenshotBytes);
            LOGGER.info("Failure screenshot saved: {}", screenshotFile.toAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Could not save the failure screenshot to the /screenshots/{} folder.", platform, e);
        }
        return screenshotBytes;
    }
}
