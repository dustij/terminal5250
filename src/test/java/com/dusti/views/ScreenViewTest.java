package com.dusti.views;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.dusti.core.ScreenBuffer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ScreenViewTest {
    private ScreenBuffer screenBuffer;
    private ScreenView screenPanel;

    @BeforeEach
    public void setup() {
        screenBuffer = mock(ScreenBuffer.class);
        when(screenBuffer.getRows()).thenReturn(3);
        when(screenBuffer.getCols()).thenReturn(3);
        screenPanel = new ScreenView(screenBuffer);
    }

    @Test
    public void testScreenPanelInitialization() {
        assertNotNull(screenPanel);
        assertEquals(3, screenPanel.getPreferredSize().width / screenPanel.getFontMetrics(screenPanel.getFont()).charWidth('W'));
        assertEquals(3, screenPanel.getPreferredSize().height / screenPanel.getFontMetrics(screenPanel.getFont()).getHeight());
    }

    @Test
    public void testPaintComponent() {
        Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
        screenPanel.paintComponent(g);
        // We can't verify the real Graphics object, but we can ensure no exceptions were thrown during rendering
        // This checks that the rendering logic can execute without null references or other issues
        assertNotNull(g);
    }

    @Test
    public void testScreenPanelDimensions() {
        int expectedWidth = 3 * screenPanel.getFontMetrics(screenPanel.getFont()).charWidth('W');
        int expectedHeight = 3 * screenPanel.getFontMetrics(screenPanel.getFont()).getHeight();

        Dimension preferredSize = screenPanel.getPreferredSize();
        assertEquals(expectedWidth, preferredSize.width);
        assertEquals(expectedHeight, preferredSize.height);
    }

    @Test
    public void testOnBufferChange() {
        // Use a real ScreenBuffer instance
        screenBuffer = new ScreenBuffer(3, 3);
        screenPanel = spy(new ScreenView(screenBuffer));

        // Add screenPanel as a listener to screenBuffer
        screenBuffer.addListener(screenPanel);

        // Call setCharAt() to trigger the buffer change event
        screenBuffer.setCharAt(1, 1, 'A');

        // Verify that the onBufferChange() method was called
        verify(screenPanel, times(1)).onBufferChange(any());

        // Verify that the repaint() method was called
        verify(screenPanel, times(1)).repaint();

        
    }
}
