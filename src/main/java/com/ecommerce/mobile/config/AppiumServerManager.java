package com.ecommerce.mobile.config;

import com.ecommerce.core.config.ConfigReader;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Starts and stops the Appium server for us, so a tester does not need to
 * open a separate terminal and type "appium" before running tests.
 * Set appium.server.autostart=false in config.properties to disable this
 * and use your own already-running Appium server instead.
 */
public class AppiumServerManager {

    private static final Logger LOGGER = LogManager.getLogger(AppiumServerManager.class);
    private static AppiumDriverLocalService service;

    public void startServer() {
        if (!ConfigReader.getBoolean("appium.server.autostart")) {
            LOGGER.info("appium.server.autostart=false -> assuming an Appium server is already running.");
            return;
        }
        if (service != null && service.isRunning()) {
            return;
        }
        try {
            LOGGER.info("Starting Appium server...");
            // Captures the driver's own step-by-step session log (device init,
            // UiAutomator2 server install/launch, app launch...) so a slow
            // startup can be diagnosed by timestamp instead of guessed at.
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .withIPAddress(ConfigReader.get("appium.server.ip"))
                    .usingPort(ConfigReader.getInt("appium.server.port"))
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withLogFile(new File("logs/appium-server.log"));

            service = AppiumDriverLocalService.buildService(builder);
            service.start();
            LOGGER.info("Appium server started on {}", getServerUrl());
        } catch (Exception e) {
            LOGGER.fatal("Could not start the Appium server. Is another Appium instance already running on this port?", e);
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        if (service != null && service.isRunning()) {
            LOGGER.info("Stopping Appium server...");
            service.stop();
        }
    }

    public String getServerUrl() {
        try {
            return "http://" + InetAddress.getByName(ConfigReader.get("appium.server.ip")).getHostAddress()
                    + ":" + ConfigReader.get("appium.server.port") + "/";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
