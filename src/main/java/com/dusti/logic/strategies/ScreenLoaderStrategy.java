package com.dusti.logic.strategies;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import com.dusti.models.Screen;

public abstract class ScreenLoaderStrategy {
    private List<Path> files;

    protected ScreenLoaderStrategy() {
        this.files = gatherFiles();
    }

    protected abstract List<Path> gatherFiles();

    public abstract Screen loadScreen(String filename);

    public List<Path> getFiles() {
        return files;
    }
}
