package com.dusti.util;

import java.awt.FontMetrics;
import javax.swing.JComponent;
import com.dusti.config.Theme;

public class HelperMethods {
    private static HelperMethods instance;
    private static boolean initialized = false;
    private static JComponent baseComponent;
    

    private HelperMethods() {
        if (!initialized) {
            throw new RuntimeException("Must call initialize() on HelperMethods class the first time it's instantiated");
        }
    }

    public static HelperMethods getInstance() {
        if (instance == null) {
            instance = new HelperMethods();
        }
        return instance;
    }

    public static void initialize(JComponent component) {
        baseComponent = component;
        initialized = true;
    }

    public int getCharWidth() {
        var theme = new Theme();
        var metrics = baseComponent.getFontMetrics(theme.getFont());
        var charWidth = metrics.charWidth('W');
        return charWidth;
    }

    public int getCharHeight() {
        var theme = new Theme();
        var metrics = baseComponent.getFontMetrics(theme.getFont());
        var charHeight = metrics.getHeight();
        return charHeight;
    }

    public FontMetrics getMetrics() {
        return baseComponent.getFontMetrics(new Theme().getFont());
    }
}
