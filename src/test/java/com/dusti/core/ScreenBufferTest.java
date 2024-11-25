package com.dusti.core;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.dusti.events.BufferEvent;
import com.dusti.interfaces.BufferChangeListener;

public class ScreenBufferTest {
    private ScreenBuffer screenBuffer;

    @BeforeEach
    void setup() {
        screenBuffer = new ScreenBuffer(10, 10);
    }

    @Test
    void testBufferInits() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                assertEquals(' ', screenBuffer.getCharAt(row, col),
                        String.format("Expected space at (%d, %d)", row, col));
            }
        }
    }

    @Test
    void testSetCharAt() {
        screenBuffer.setCharAt(5, 5, 'A');
        assertEquals('A', screenBuffer.getCharAt(5, 5));
    }

    @Test
    void testAddListenerAndNotify() {
        AtomicReference<BufferEvent<Character>> capturedEvent = new AtomicReference<>();

        BufferChangeListener<Character> listener = capturedEvent::set;
        screenBuffer.addListener(listener);

        screenBuffer.setCharAt(3, 3, 'B');

        BufferEvent<Character> event = capturedEvent.get();
        assertNotNull(event, "Listener should capture event");
        assertEquals(3, event.getRow(), "Event row should be 3");
        assertEquals(3, event.getCol(), "Event col should be 3");
        assertEquals(' ', event.getOldValue(), "Old value should be ' '");
        assertEquals('B', event.getNewValue(), "New value should be 'B'");
    }

    @Test
    void testRemoveListener() {
        AtomicReference<BufferEvent<Character>> capturedEvent = new AtomicReference<>();
        BufferChangeListener<Character> listener = capturedEvent::set;

        screenBuffer.addListener(listener);
        screenBuffer.removeListener(listener);

        screenBuffer.setCharAt(2, 2, 'C');

        assertNull(capturedEvent.get(), "Listener should not be notified after removal");
    }

    @Test
    void testMultipleListeners() {
        AtomicReference<BufferEvent<Character>> capturedEvent1 = new AtomicReference<>();
        AtomicReference<BufferEvent<Character>> capturedEvent2 = new AtomicReference<>();

        BufferChangeListener<Character> listener1 = capturedEvent1::set;
        BufferChangeListener<Character> listener2 = capturedEvent2::set;

        screenBuffer.addListener(listener1);
        screenBuffer.addListener(listener2);

        screenBuffer.setCharAt(4, 4, 'D');

        assertNotNull(capturedEvent1.get(), "Listener 1 should be notified");
        assertNotNull(capturedEvent2.get(), "Listener 2 should be notified");
        assertEquals('D', capturedEvent1.get().getNewValue(), "Listener 1 new value should be 'D'");
        assertEquals('D', capturedEvent2.get().getNewValue(), "Listener 2 new value should be 'D'");
    }
}
