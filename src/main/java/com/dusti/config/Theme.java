package com.dusti.config;

import java.awt.Color;
import java.awt.Font;

public class Theme {
    private final ConfigLoader configLoader;

    public Theme() {
        this.configLoader = ConfigLoader.getInstance();
    }

    public Font getFont() {
        String fontName = configLoader.getProperty("fontName", "Monospaced");
        int fontStyle = configLoader.getIntProperty("fontStyle", Font.PLAIN);
        int fontSize = configLoader.getIntProperty("fontSize", 26);
        return new Font(fontName, fontStyle, fontSize);
    }

    public Color getBaseForeground() {
        return configLoader.getColorProperty("baseForeground", Color.GREEN);
    }

    public Color getBaseBackground() {
        return configLoader.getColorProperty("baseBackground", Color.BLACK);
    }

    public Color getInputForeground() {
        return configLoader.getColorProperty("inputForeground", Color.CYAN);
    }

    public Color getInputBackground() {
        return configLoader.getColorProperty("inputBackground", Color.BLACK);
    }

    public Color getMessageForeground() {
        return configLoader.getColorProperty("messageForeground", Color.WHITE);
    }

    public Color getMessageBackground() {
        return configLoader.getColorProperty("messageBackground", Color.BLACK);
    }

    public Color getInputUnderline() {
        return configLoader.getColorProperty("inputUnderline", Color.ORANGE);
    }

}
