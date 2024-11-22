package com.dusti.logic;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.dusti.config.Theme;
import com.dusti.core.LoggerFactory;
import com.dusti.models.Screen;
import com.dusti.ui.Renderer;

public class ScreenManager {
    private static final Logger logger = LoggerFactory.getLogger(ScreenManager.class.getName());

    private final ScreenBuffer screenBuffer;
    private final Theme theme;
    private Screen activeScreen;

    private final Map<String, Screen> screens;

    public ScreenManager() {
        this.theme = new Theme();
        this.screenBuffer = new ScreenBuffer(24, 80, theme);
        this.screens = new HashMap<>();
        initializeScreens();
        setActiveScreen("mainMenu");
    }

    private void initializeScreens() {
        // Load screens from JSON

        // TODO make screens
    }

    public void addScreen(String name, Screen screen) {
        this.screens.put(name, screen);
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

    public Dimension getScreenSize() {
        return new Dimension(screenBuffer.getCols() * activeScreen.getCharWidth(),
                             screenBuffer.getRows() * activeScreen.getCharHeight());
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
