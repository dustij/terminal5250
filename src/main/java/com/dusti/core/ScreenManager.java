package com.dusti.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.dusti.models.ScreenModel;

public class ScreenManager {
    private static final Logger logger = LoggerFactory.getLogger(ScreenManager.class.getName());
    private static Map<String, ScreenModel> screens;
    private static ScreenModel activeScreen;
    private static boolean isInitialized = false;

    public static void initialize() {
        screens = new HashMap<>();
        activeScreen = screens.get("home");
        isInitialized = true;
        logger.info("Screen Manager initialized successfully.");
    }

    public ScreenModel getActiveScreen() {
        if (isInitialized) return activeScreen;
        logger.warning("Screen Manager has not be initialized. No active screen to retrieve.");
        return null;
    }
}
