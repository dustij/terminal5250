package com.dusti.core;

import java.util.Map;
import java.util.logging.Logger;
import com.dusti.loaders.ScreenLoader;
import com.dusti.models.ScreenModel;
import com.dusti.loaders.strategies.JsonScreenLoaderStrategy;

public class ScreenManager {
    private final Logger logger = LoggerFactory.getLogger(ScreenManager.class.getName());
    private Map<String, ScreenModel> screens;
    private ScreenModel activeScreen;
    private ScreenBuffer screenBuffer;

    public ScreenManager(ScreenBuffer screenBuffer) {
        logger.info("Initializing ScreenManager");
        this.screenBuffer = screenBuffer;
        initScreens();
    }

    private void initScreens() {
        screens = new ScreenLoader(new JsonScreenLoaderStrategy()).getScreens();
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
