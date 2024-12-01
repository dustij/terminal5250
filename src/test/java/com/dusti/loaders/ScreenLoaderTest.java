package com.dusti.loaders;

import com.dusti.models.ScreenModel;
import com.dusti.templates.ScreenLoader;
import com.dusti.templates.ScreenLoaderStrategy;
import com.dusti.templates.strategies.JsonScreenLoaderStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScreenLoaderTest {
    private ScreenLoaderStrategy mockStrategy;
    private ScreenLoader screenLoader;

    @BeforeEach
    public void setup() {
        mockStrategy = mock(JsonScreenLoaderStrategy.class);
        screenLoader = new ScreenLoader(mockStrategy);
    }

    @Test
    public void testGetScreens_WithValidFiles() {
        // Arrange
        var mocktPath1 = mock(Path.class);
        var mocktPath2 = mock(Path.class);
        when(mocktPath1.getFileName()).thenReturn(Path.of("screen1.json"));
        when(mocktPath2.getFileName()).thenReturn(Path.of("screen2.json"));
        when(mockStrategy.getFiles()).thenReturn(List.of(mocktPath1, mocktPath2));

        var screen1 = new ScreenModel("Screen1", "Description1", new ArrayList<>());
        var screen2 = new ScreenModel("Screen2", "Description2", new ArrayList<>());
        when(mockStrategy.loadScreen("screen1.json")).thenReturn(screen1);
        when(mockStrategy.loadScreen("screen2.json")).thenReturn(screen2);

        // Act
        var screens = screenLoader.getScreens();

        // Assert
        assertEquals(2, screens.size());
        assertEquals(screen1, screens.get("Screen1"));
        assertEquals(screen2, screens.get("Screen2"));
        verify(mockStrategy, times(1)).getFiles();
        verify(mockStrategy, times(1)).loadScreen("screen1.json");
        verify(mockStrategy, times(1)).loadScreen("screen2.json");
    }

    @Test
    public void testGetScreens_WithNoFiles() {
        // Arrange
        when(mockStrategy.getFiles()).thenReturn(List.of());

        // Act
        var screens = screenLoader.getScreens();

        // Assert
        assertEquals(0, screens.size());
        verify(mockStrategy, times(1)).getFiles();
        verify(mockStrategy, never()).loadScreen(anyString());
    }

    @Test
    public void testGetSCreens_WithInvalidFile() {
        // Arrange
        var mockPath = mock(Path.class);
        when(mockPath.getFileName()).thenReturn(Path.of("invalid.json"));
        when(mockStrategy.getFiles()).thenReturn(List.of(mockPath));
        when(mockStrategy.loadScreen("invalid.json")).thenThrow(new RuntimeException("Could not load from JSON"));

        // Act & Assert
        try {
            screenLoader.getScreens();
        } catch (RuntimeException e) {
            assertEquals("Could not load from JSON", e.getMessage());
        }

        verify(mockStrategy, times(1)).getFiles();
        verify(mockStrategy, times(1)).loadScreen("invalid.json");
    }
}
