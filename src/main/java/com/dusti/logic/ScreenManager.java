package com.dusti.logic;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.dusti.config.Theme;
import com.dusti.core.LoggerFactory;
import com.dusti.models.Field;
import com.dusti.models.Screen;
import com.dusti.ui.Renderer;
import com.dusti.util.ScreenLoader;

public class ScreenManager {
    private static final Logger logger = LoggerFactory.getLogger(ScreenManager.class.getName());

    private final ScreenBuffer screenBuffer;
    private final Theme theme;
    private Screen activeScreen;

    private Map<String, Screen> screens;

    public ScreenManager() {
        this.theme = new Theme();
        this.screenBuffer = new ScreenBuffer(24, 80, theme);
        this.screens = new HashMap<>();
        initializeScreens();
        setActiveScreen("mainScreen");
    }

    private void initializeScreens() {
        var screenLoader = new ScreenLoader("json");
        screens = screenLoader.getScreens();
        validateScreens(screens);
    }

    private void validateScreens(Map<String, Screen> screens) {
        // Makes sure each screen can fit within the buffer bounds
        for (Screen screen : screens.values()) {
            if (!validateScreen(screen)) {
                String msg = String.format("Screen '%s' does not fit inside buffer.", screen.getName());
                logger.severe(msg);
                throw new IllegalArgumentException(msg);
            }
        }
    }

    private boolean validateScreen(Screen screen) {
        // This screen's fields should fit within the buffer
        var bufferRows = screenBuffer.getRows();
        var bufferCols = screenBuffer.getCols();
        for (Field field : screen.getFields()) {
            if (field.getStartPosition().getRow() < 0) return false;
            if (field.getStartPosition().getCol() < 0) return false;
            if (field.getEndPosition().getRow() > bufferRows) return false;
            if (field.getEndPosition().getCol() > bufferCols) return false;
        }
        return true;
    }

    public void setActiveScreen(String name) {
        if (screens.containsKey(name)) {
            activeScreen = screens.get(name);
            screenBuffer.loadScreen(activeScreen);
        } else {
            logger.severe("Screen not found: " + name);
            throw new IllegalArgumentException("Screen not found: " + name);
        }
    }

    public Screen getActiveScreen() {
        return activeScreen;
    }

    public ScreenBuffer getScreenBuffer() {
        return screenBuffer;
    }

    public Theme getTheme() {
        return theme;
    }

    public void moveCursorUp() {
        screenBuffer.moveCursor(-1, 0);
    }

    public void moveCursorDown() {
        screenBuffer.moveCursor(1, 0);
    }

    public void moveCursorLeft() {
        screenBuffer.moveCursor(0, -1);
    }

    public void moveCursorRight() {
        screenBuffer.moveCursor(0, 1);
    }

    public Renderer getRenderer() {
        return screenBuffer.getRenderer();
    }

    public void handleTabPressed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleTabPressed'");
    }

    public void handleShftTabPressed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleShftTabPressed'");
    }
    
    public void handleBackspacePressed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBackspacePressed'");
    }

    public void handleDeletePressed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleDeletePressed'");
    }

    public void handleCharPressed(char ch) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleCharPressed'");
    }
}
