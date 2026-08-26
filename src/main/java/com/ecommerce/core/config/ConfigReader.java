package com.ecommerce.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads config.properties once and hands out values by key.
 * Every setting a beginner needs to change (APK path, package name,
 * device name, timeouts...) lives in that one file, not in Java code.
 * Shared by both the mobile and web frameworks - lives outside both
 * mobile.* and web.* so log lines from it don't misleadingly read as
 * one platform's package when the other platform is what's running.
 */
public class ConfigReader {

    private static final Logger LOGGER = LogManager.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IOException(CONFIG_FILE + " was not found on the classpath (src/test/resources).");
            }
            PROPERTIES.load(inputStream);
            LOGGER.info("Loaded configuration from {}", CONFIG_FILE);
        } catch (IOException e) {
            LOGGER.fatal("Could not load {}. Aborting.", CONFIG_FILE, e);
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing key '" + key + "' in " + CONFIG_FILE);
        }
        return value.trim();
    }

    public static String get(String key, String defaultValue) {
        String value = PROPERTIES.getProperty(key);
        return (value == null || value.trim().isEmpty()) ? defaultValue : value.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
