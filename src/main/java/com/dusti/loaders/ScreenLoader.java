package com.dusti.loaders;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import com.dusti.core.LoggerFactory;
import com.dusti.models.ScreenModel;

public class ScreenLoader {
    private static final Logger logger = LoggerFactory.getLogger(ScreenLoader.class.getName());
    private final ScreenLoaderStrategy strategy;
    private List<Path> files;
    
    public ScreenLoader(ScreenLoaderStrategy strategy) {
        this.strategy = strategy;
    }

    public Map<String, ScreenModel> getScreens() {
        logger.info("ScreenLoader uses " + strategy.getClass().getSimpleName());
        Map<String, ScreenModel> screens = new HashMap<>();

        files = strategy.getFiles();
        for (Path file : files) {
            String fileName = file.getFileName().toString();
            logger.info("Loading " + fileName);

            var screen = strategy.loadScreen(fileName);
            screens.put(screen.getName(), screen);
        }

        return screens;
    }
}
