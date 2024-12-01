package com.dusti.views.themes;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import com.dusti.core.Config;

public class Theme {
    private final Config config;
    private final Font font; 
    private final Map<String, Color> colorGetterMap;

    public Theme() {
        this.config = Config.getInstance();
        String fontName = config.getProperty("fontName", "Monospaced");
        int fontStyle = config.getIntProperty("fontStyle", Font.PLAIN);
        int fontSize = config.getIntProperty("fontSize", 18);
        this.font = new Font(fontName, fontStyle, fontSize > 30 ? 30 : fontSize);
        this.colorGetterMap = Map.of(
            "field", getFieldColor(),
            "input", getInputColor(),
            "statusIndicator", getStatusIndicatorsColor(),
            "informationIndicator", getInformationIndicatorsColor(),
            "attentionIndicator", getAttentionIndicatorsColor(),
            "errorIndicators", getErrorIndicatorsColor(),
            "screenBackground", getScreenBackgroundColor(),
            "columnSeparator", getColumnSeparatorColor()
        );
    }

    public Font getFont() {
        return font;
    }

    public Color getFieldColor() {
        return config.getColorProperty("field", Color.WHITE);
    }

    public Color getInputColor() {
        return config.getColorProperty("input", Color.WHITE);
    }

    public Color getStatusIndicatorsColor() {
        return config.getColorProperty("statusIndicators", Color.WHITE);
    }

    public Color getInformationIndicatorsColor() {
        return config.getColorProperty("informationIndicators", Color.WHITE);
    }

    public Color getAttentionIndicatorsColor() {
        return config.getColorProperty("attentionIndicators", Color.WHITE);
    }

    public Color getErrorIndicatorsColor() {
        return config.getColorProperty("errorIndicators", Color.WHITE);
    }

    public Color getScreenBackgroundColor() {
        return config.getColorProperty("screenBackground", Color.WHITE);
    }

    public Color getColumnSeparatorColor() {
        return config.getColorProperty("columnSeparator", Color.WHITE);
    }

    public Color getColorByElementType(String type) {
        return colorGetterMap.getOrDefault(type, getFieldColor());
    }
}
