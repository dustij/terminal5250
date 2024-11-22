package com.dusti.models;

import javax.swing.JPanel;
import com.dusti.config.Theme;

public class Screen extends JPanel {

    public Field[] getFields() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFields'");
    }

    public int getCharWidth() {
        var theme = new Theme();
        var metrics = this.getFontMetrics(theme.getFont());
        var charWidth = metrics.charWidth('W');
        return charWidth;
    }

    public int getCharHeight() {
        var theme = new Theme();
        var metrics = this.getFontMetrics(theme.getFont());
        var charHeight = metrics.getHeight();
        return charHeight;
    }
}
