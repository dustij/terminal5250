package com.dusti.templates.strategies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;
import com.dusti.core.LoggerFactory;
import com.dusti.models.ScreenModel;
import com.dusti.templates.ScreenLoaderStrategy;
import com.dusti.templates.TemplateContext;
import com.dusti.templates.TemplateRenderer;
import com.google.gson.Gson;

public class JsonScreenLoaderStrategy implements ScreenLoaderStrategy {
    private final static Logger logger = LoggerFactory.getLogger(JsonScreenLoaderStrategy.class.getName());

    @Override
    public List<Path> getFiles() {
        logger.info("Gathering JSON files.");
        try (Stream<Path> files = Files.list(Path.of(JsonScreenLoaderStrategy.class.getResource("/screens/json").toURI()))) {
            return files.filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".json")).toList();
        } catch (IOException | URISyntaxException e) {
            logger.warning("An error occured while getting json files in the menus resource directory.");
            return new ArrayList<>();
        }
    }

    @Override
    public ScreenModel loadScreen(String filename) {
        try (var stream = JsonScreenLoaderStrategy.class.getResourceAsStream("/screens/json/" + filename);
            var reader = new BufferedReader(new InputStreamReader(stream))
        ) {
            var gson = new Gson();
            var model = gson.fromJson(reader, ScreenModel.class);
            // Render templates
            for (var elem : model.getElements()) {
                var template = elem.getVisualRepr();
                var context = TemplateContext.getContextMap().get(model.getName());
                elem.setVisualRepr(TemplateRenderer.renderTemplate(template, context));
            }
            return model;
        } catch (Exception e) {
            throw new RuntimeException("Could not load from JSON -> ", e);
        }
    }
    
}
