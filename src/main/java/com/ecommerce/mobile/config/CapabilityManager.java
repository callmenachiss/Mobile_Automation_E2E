package com.ecommerce.mobile.config;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.Duration;

/**
 * Builds the Appium "capabilities" - the instructions that tell Appium
 * which device to use and which app to install/launch.
 * All the actual values come from config.properties.
 */
public class CapabilityManager {

    private static final Logger LOGGER = LogManager.getLogger(CapabilityManager.class);

    public UiAutomator2Options getCapabilities() {
        LOGGER.info("Building Appium capabilities from config.properties");

        String appPath = requireValue("app.path");
        File appFile = new File(appPath);
        if (!appFile.exists()) {
            throw new RuntimeException(
                    "APK not found at '" + appFile.getAbsolutePath() + "'. " +
                    "Copy your .apk into the /apps folder and update 'app.path' in config.properties.");
        }

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(ConfigReader.get("platformName"));
        options.setAutomationName(ConfigReader.get("automationName"));

        // deviceName / udid / platformVersion are only added if filled in.
        // A single connected emulator/device works fine with all three blank;
        // udid becomes necessary once more than one device is connected.
        String deviceName = ConfigReader.get("deviceName", "");
        if (!deviceName.isEmpty()) {
            options.setDeviceName(deviceName);
        }

        String udid = ConfigReader.get("udid", "");
        if (!udid.isEmpty()) {
            options.setUdid(udid);
        }

        String platformVersion = ConfigReader.get("platformVersion", "");
        if (!platformVersion.isEmpty()) {
            options.setPlatformVersion(platformVersion);
        }

        options.setApp(appFile.getAbsolutePath());
        options.setAppPackage(requireValue("appPackage"));
        options.setAppActivity(requireValue("appActivity"));
        options.setNoReset(ConfigReader.getBoolean("app.noReset"));
        options.setAutoGrantPermissions(ConfigReader.getBoolean("app.autoGrantPermissions"));
        options.setNewCommandTimeout(Duration.ofSeconds(ConfigReader.getInt("app.newCommandTimeout")));

        return options;
    }

    private String requireValue(String key) {
        String value = ConfigReader.get(key, "");
        if (value.isEmpty()) {
            throw new RuntimeException(
                    "'" + key + "' is blank in config.properties. Fill it in with your real device/app " +
                    "details before running the tests - see README.md -> \"Setting up YOUR app\".");
        }
        return value;
    }
}
