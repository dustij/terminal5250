package com.dusti.core;

import java.util.Map;
import java.util.logging.Logger;
import com.dusti.models.ScreenModel;
import com.dusti.templates.ScreenLoader;
import com.dusti.templates.strategies.JsonScreenLoaderStrategy;

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
        validateScreens();
    }

    private void validateScreens() {
        for (var screenEntry : screens.entrySet()) {
            if (!validate(screenEntry.getValue())) {
                logger.warning(String.format("%s screen has element(s) that do not fit in buffer.", screenEntry.getKey()));
            }
        }
    }

    private boolean validate(ScreenModel screen) {
        var maxRows = screenBuffer.getRows();
        var maxCols = screenBuffer.getCols();
        for (var elem : screen.getElements()) {
            if (elem.getRow() < 0) return false;
            if (elem.getCol() < 0) return false;
            if (elem.getRow() > maxRows) return false;
            if (elem.getCol() + elem.getVisualRepr().length() > maxCols) return false;
        }
        return true;
    }

    public void setActiveScreen(String name) {
        activeScreen = screens.get(name);
        screenBuffer.updateFromModel(activeScreen);
    }

    public Map<String, ScreenModel> getScreenMap() {
        return screens;
    }


}
