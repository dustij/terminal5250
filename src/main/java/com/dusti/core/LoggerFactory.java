package com.dusti.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerFactory {
    private static volatile boolean isInitialized = false;
    private static final Object LOCK = new Object();
    private static final Logger LOGGER = Logger.getLogger(LoggerFactory.class.getName());

    public static Logger getLogger(String name) {
        if (!isInitialized) {
            synchronized (LOCK) {
                inititalize();
                isInitialized = true;
            }
        }
        return Logger.getLogger(name);
    }

    private static void inititalize() {
        try (InputStream configFile = LoggerFactory.class.getResourceAsStream("/logging")) {
            if (configFile != null) {
                LogManager.getLogManager().readConfiguration(configFile);
                LOGGER.info("Logging configuration loaded successfully.");
            } else {
                LOGGER.warning("Could not load logging configuration file");
            }
        } catch (IOException e) {
            LOGGER.severe("Error setting up logger: " + e.getMessage());
            throw new RuntimeException("Failed to initialize logging configuration", e);
        }
    }

}
