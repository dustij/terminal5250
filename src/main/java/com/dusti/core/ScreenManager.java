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
        setBufferFromModel();

        logger.info("Screen Manager initialized successfully.");
    }

    private void initScreens() {
        // TODO: implement resource loader using strategy design (JSON, XML, etc.)
        screens = new HashMap<>();
        activeScreen = screens.get("home");
        setBufferFromModel();
    }

    private void setBufferFromModel() {
        // TODO: remove this, only for testing
        Character[][] screen2DArray = new Character[27][80]; 
        activeScreen = new ScreenModel(); // to avoid null pointer while testing
        for (int i = 0; i < 27; i++) {
            Arrays.fill(screen2DArray[i], 'A');
        }

        if (activeScreen == null)
            return;


        screenBuffer.replaceBuffer(screen2DArray);
    }

    public void setActiveScreen(String name) {
        activeScreen = screens.get(name);
        setBufferFromModel();
    }

    public Map<String, ScreenModel> getScreenMap() {
        return screens;
    }


}
