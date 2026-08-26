package com.ecommerce.mobile.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads config.properties once and hands out values by key, for the
 * mobile framework. Web has its own independent copy
 * (com.ecommerce.web.config.ConfigReader) - both read the same
 * config.properties file, but neither package depends on the other's
 * classes, so mobile and web can build and run in complete isolation.
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
