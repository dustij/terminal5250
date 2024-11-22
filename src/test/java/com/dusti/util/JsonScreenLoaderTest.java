package com.dusti.util;

import com.dusti.logic.strategies.JsonScreenLoaderStrategy;
import com.dusti.models.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JsonScreenLoaderTest {
    private ScreenLoader screenLoader;

    @BeforeEach
    public void setUp() {
        screenLoader = new ScreenLoader("json");
    }

    @Test
    public void testConstructor_WithValidStrategy_ShouldInitCorrectly() {
        assertNotNull(screenLoader.getStrategy());
        assertTrue(screenLoader.getStrategy() instanceof JsonScreenLoaderStrategy);
    }
}
