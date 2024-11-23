package com.dusti.util;

import javax.swing.JComponent;
import com.dusti.config.Theme;

public class HelperMethods {
        public static int getCharWidth(JComponent component) {
        var theme = new Theme();
        var metrics = component.getFontMetrics(theme.getFont());
        var charWidth = metrics.charWidth('W');
        return charWidth;
    }

    public static int getCharHeight(JComponent component) {
        var theme = new Theme();
        var metrics = component.getFontMetrics(theme.getFont());
        var charHeight = metrics.getHeight();
        return charHeight;
    }
}
