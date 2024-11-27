package com.dusti.core;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.dusti.events.BufferEvent;
import com.dusti.models.ScreenElementModel;
import com.dusti.models.ScreenModel;
import com.dusti.views.ScreenView;

public class ScreenManagerTest {
    private ScreenBuffer screenBuffer;
    private ScreenManager screenManager;

    @Test
    public void testScreenManagerInitialization() {
        screenBuffer = new ScreenBuffer(10, 10);
        screenManager = new ScreenManager(screenBuffer);
        assertNotNull(screenManager);
    }

    @Test
    public void testSetActiveScreen() {
        screenBuffer = new ScreenBuffer(10, 10);
        screenManager = new ScreenManager(screenBuffer);
        
        ScreenView screenView = spy(new ScreenView(screenBuffer));
        screenBuffer.addListener(screenView);

        List<ScreenElementModel> elems = new ArrayList<>();
        elems.add(new ScreenElementModel("Test", "test", 0, 0));
        
        screenManager.getScreenMap().put("test", new ScreenModel("testScreen", "Test Screen", elems));
        screenManager.setActiveScreen("test");

        // Verify the ScreenPanel repaint method is called
        verify(screenView, times(1)).onBufferChange(any(BufferEvent.class));
        verify(screenView, times(1)).repaint();
    }
}
