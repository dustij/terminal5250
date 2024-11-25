package com.dusti.config;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import com.dusti.core.LoggerFactory;

public class ConfigLoader {
    private static ConfigLoader instance;
    private final Properties properties;
    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class.getName());

    private ConfigLoader() {
        properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/config")) {
            if (input != null) {
                properties.load(input);
            } else {
                logger.warning("Configuration file not found. Using defaults.");
            }
        } catch (IOException e) {
            logger.severe("Failed to load configuration.");
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public Color getColorProperty(String key, Color defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                String hexCode = properties.getProperty(value);
                return Color.decode(hexCode);
            } catch (NumberFormatException e) {
                logger.warning(String.format("Invalid color format for %s: %s", key, value));
            }
        }
        return defaultValue;
    }
}
