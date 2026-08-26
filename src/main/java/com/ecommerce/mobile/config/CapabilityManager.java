package com.ecommerce.mobile.config;

import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.Duration;

/**
 * Builds the Appium capabilities.
 *
 * app.path is OPTIONAL:
 * - If app.path is provided, Appium uses the APK.
 * - If app.path is blank, Appium launches the already-installed
 *   application using appPackage and appActivity.
 */
public class CapabilityManager {

    private static final Logger LOGGER = LogManager.getLogger(CapabilityManager.class);

    public UiAutomator2Options getCapabilities() {
        LOGGER.info("Building Appium capabilities from config.properties");

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName(ConfigReader.get("platformName"));
        options.setAutomationName(ConfigReader.get("automationName"));

        // ============================================================
        // APP PATH - OPTIONAL
        // ============================================================
        String appPath = ConfigReader.get("app.path", "");

        if (!appPath.isBlank()) {

            File appFile = new File(appPath);

            if (!appFile.exists()) {
                throw new RuntimeException(
                        "APK not found at '" + appFile.getAbsolutePath() + "'. " +
                                "Check the 'app.path' value in config.properties."
                );
            }

            options.setApp(appFile.getAbsolutePath());

            LOGGER.info("Using APK: {}", appFile.getAbsolutePath());

        } else {

            LOGGER.info(
                    "app.path is blank. Using already-installed application."
            );
        }

        // ============================================================
        // DEVICE
        // ============================================================

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

        // ============================================================
        // APPLICATION
        // ============================================================

        options.setAppPackage(
                requireValue("appPackage")
        );

        options.setAppActivity(
                requireValue("appActivity")
        );

        // ============================================================
        // OTHER SETTINGS
        // ============================================================

        options.setNoReset(
                ConfigReader.getBoolean("app.noReset")
        );

        options.setAutoGrantPermissions(
                ConfigReader.getBoolean("app.autoGrantPermissions")
        );

        options.setNewCommandTimeout(
                Duration.ofSeconds(
                        ConfigReader.getInt("app.newCommandTimeout")
                )
        );

        // ============================================================
        // SESSION STARTUP SPEED
        // These target the "installing/starting UiAutomator2 on the
        // device" phase that runs once before the first scenario -
        // separate from how fast the app itself renders.
        // ============================================================

        options.setSkipDeviceInitialization(
                ConfigReader.getBoolean("appium.skipDeviceInitialization")
        );

        options.setDisableWindowAnimation(
                ConfigReader.getBoolean("appium.disableWindowAnimation")
        );

        // Only safe once the UiAutomator2 server APKs are already installed
        // on this device from a prior run - see config.properties comment.
        options.setSkipServerInstallation(
                ConfigReader.getBoolean("appium.skipServerInstallation")
        );

        // Don't let session creation itself launch the app and wait for it
        // to gain focus - Hooks.resetAppState() already does exactly that
        // (terminateApp + activateApp) in @Before for every scenario,
        // including the first. Without this, the very first scenario pays
        // for a full launch, then an immediate terminate + relaunch.
        options.setCapability("appium:autoLaunch", false);

        return options;
    }

    /**
     * Used only for settings that are genuinely required.
     */
    private String requireValue(String key) {

        String value = ConfigReader.get(key, "");

        if (value.isEmpty()) {
            throw new RuntimeException(
                    "'" + key + "' is blank in config.properties. " +
                            "Please provide the required value."
            );
        }

        return value;
    }
}