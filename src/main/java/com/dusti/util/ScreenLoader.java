package com.dusti.util;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import com.dusti.core.LoggerFactory;
import com.dusti.logic.strategies.JsonScreenLoaderStrategy;
import com.dusti.logic.strategies.ScreenLoaderStrategy;
import com.dusti.models.Screen;

public class ScreenLoader {
    private static final Logger logger = LoggerFactory.getLogger(ScreenLoader.class.getName());
    private static final Map<String, ScreenLoaderStrategy> strategies = new HashMap<>();
    private String strategy;
    private List<Path> files;

    static {
        strategies.put("json", new JsonScreenLoaderStrategy());
    }

    public ScreenLoader(String strategy) {
        logger.info("ScreenLoader strategy: " + strategy);
        this.strategy = strategy;
        this.files = strategies.get(strategy).getFiles();
    }

    public ScreenLoaderStrategy getStrategy() {
        return strategies.get(strategy);
    }

    public Map<String, Screen> getScreens() {
        // Use this loader's strategy to load the screens
        Map<String, Screen> screens = new HashMap<>();

        for (Path file : files) {
            String fileName = file.getFileName().toString();
            int indexOfExtension = fileName.lastIndexOf(".");
            fileName = indexOfExtension == -1 ? fileName : fileName.substring(0, indexOfExtension);
            logger.info("Loading " + fileName);

            var screen = getStrategy().loadScreen(fileName);
            screens.put(screen.getName(), screen);
        }
        return screens;
    }
}


