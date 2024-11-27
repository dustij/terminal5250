package com.dusti.loaders;

import java.nio.file.Path;
import java.util.List;
import com.dusti.models.ScreenModel;

public interface ScreenLoaderStrategy {
    ScreenModel loadScreen(String filename);
    List<Path> getFiles();
}
