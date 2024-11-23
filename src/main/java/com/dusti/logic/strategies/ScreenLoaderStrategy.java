package com.dusti.logic.strategies;

import java.nio.file.Path;
import java.util.List;
import com.dusti.models.Screen;

public abstract class ScreenLoaderStrategy {
    public List<Path> getFiles() {
        return gatherFiles();
    }

    protected abstract List<Path> gatherFiles();
    public abstract Screen loadScreen(String filename);

}
