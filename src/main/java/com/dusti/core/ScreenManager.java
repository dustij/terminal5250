package com.dusti.core;

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
        // TODO: implement resource loader using strategy (JSON, XML, etc.)
        screens = new HashMap<>();
        activeScreen = screens.get("home");
    }

    private void setBufferFromModel() {
        if (activeScreen == null)
            return;
        Character[][] screen2DArray = new Character[27][80]; // TODO: replace this with array from screen model data
        screenBuffer.replaceBuffer(screen2DArray);
    }


}
