package com.dusti.util;

import com.dusti.logic.strategies.JsonScreenLoaderStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    public void testGetScreens_withValidFiles_ShouldLoadScreens() {
        // IMPORTANT: The screens resource should have at least mainScreen
        //            and mainScreen should have a least selection field
        var screens = screenLoader.getScreens();
        var mainScreen = screens.get("mainScreen");
        var fields = mainScreen.getFields();
        var selection = fields[0];
        assertEquals(mainScreen.getName(), "mainScreen");
        assertEquals(selection.getName(), "selection");

        // NOTE: Could be useful to test screens as I add them
    }
}
