package com.dusti.t5250;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class T5250Logger {
    private static boolean isInitialized = false;
    private static final Logger LOGGER = Logger.getLogger(T5250Logger.class.getName());

    private T5250Logger() {
        // Should not be instantiated
    }

    public static Logger getLogger(String name) {
        if (!isInitialized) {
            inititalize();
            isInitialized = true;
        }
        return Logger.getLogger(name);
    } 

    private static void inititalize() {
        try (InputStream configFile = T5250Logger.class.getResourceAsStream("/logging")) {
            if (configFile != null) {
                LogManager.getLogManager().readConfiguration(configFile);
            } else {
                LOGGER.warning("Could not load logging configuration");
            }
        } catch (IOException e) {
            LOGGER.severe("Error setting up logger");
            throw new RuntimeException(e);
        }
    }
}
