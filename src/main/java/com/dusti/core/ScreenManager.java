package com.dusti.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.dusti.models.ScreenModel;

public class ScreenManager {
    private final Logger logger = LoggerFactory.getLogger(ScreenManager.class.getName());
    private Map<String, ScreenModel> screens;
    private ScreenModel activeScreen;
    private ScreenBuffer screenBuffer;

    public ScreenManager(ScreenBuffer screenBuffer) {
        this.screenBuffer = screenBuffer;

        initScreens();

        logger.info("Screen Manager initialized successfully.");
    }

    private void initScreens() {
        // TODO: implement resource loader using strategy design (JSON, XML, etc.)
        screens = new HashMap<>();
        setActiveScreen("home");
    }

    public void setActiveScreen(String name) {
        activeScreen = screens.get(name);
        screenBuffer.updateFromModel(activeScreen);
    }

    public Map<String, ScreenModel> getScreenMap() {
        return screens;
    }


}
