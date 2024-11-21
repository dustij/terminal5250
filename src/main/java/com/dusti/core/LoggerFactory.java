package com.dusti.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import com.dusti.util.MyLogger;

public class LoggerFactory {
        private static boolean isInitialized = false;
    private static final Logger LOGGER = Logger.getLogger(MyLogger.class.getName());

    private LoggerFactory() {
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
        try (InputStream configFile = MyLogger.class.getResourceAsStream("/logging")) {
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
