package com.dusti.t5250;

import java.awt.Color;
import java.awt.Font;

public class Config {
    private static Font font = new Font(Font.MONOSPACED, Font.PLAIN, 26);

    public static Font getFont() {
        return font;
    }

    public static Color getBaseForeround() {
        return Color.GREEN;
    }

    public static Color getBaseBackground() {
        return Color.BLACK;
    }

    public static Color getInputForeground() {
        return Color.CYAN;
    }

    public static Color getInputBackground() {
        return Color.BLACK;
    }

    public static Color getMessageForeground() {
        return Color.WHITE;
    }

    public static Color getMessageBackground() {
        return Color.BLACK;
    }

    public static Color getInputUnderline() {
        return Color.ORANGE;
    }
}

