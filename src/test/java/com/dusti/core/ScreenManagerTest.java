package com.dusti.core;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.dusti.events.BufferEvent;
import com.dusti.models.ScreenModel;
import com.dusti.views.ScreenView;

public class ScreenManagerTest {
    private ScreenBuffer screenBuffer;
    private ScreenManager screenManager;

    @BeforeEach
    public void setup() {
        screenBuffer = mock(ScreenBuffer.class);
        screenManager = new ScreenManager(screenBuffer);
    }

    @Test
    public void testScreenManagerInitialization() {
        assertNotNull(screenManager);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetBufferFromModel() {
        // Verify that dispatchNewBufferArrayEvent() is invoked when changing active screen
        screenBuffer = new ScreenBuffer(3, 3);
        screenManager = new ScreenManager(screenBuffer);
        
        ScreenView screenPanel = spy(new ScreenView(screenBuffer));
        screenBuffer.addListener(screenPanel);
        
        screenManager.getScreenMap().put("test", new ScreenModel());
        screenManager.setActiveScreen("test");

        // Verify the ScreenPanel repaint method is called
        verify(screenPanel, times(1)).onBufferChange(any(BufferEvent.class));
        verify(screenPanel, times(1)).repaint();
    }
}
