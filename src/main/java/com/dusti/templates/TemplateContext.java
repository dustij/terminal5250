package com.dusti.templates;

import java.util.HashMap;
import java.util.Map;

public class TemplateContext {
    private static final Map<String, Map<String, String>> CONTEXT_MAP = new HashMap<>();
    private static final Map<String, String> HOME = new HashMap<>();

    static {
        // Add application specific context
        HOME.put("date", "Sun, Nov 17 2024");
        HOME.put("time", "7:26:15pm");

        // Set context map (screen names to context)
        CONTEXT_MAP.put("home", HOME);
    }

    public static Map<String, Map<String, String>> getContextMap() {
        return CONTEXT_MAP;
    }
}
