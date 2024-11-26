package com.dusti.events;

import com.dusti.interfaces.BufferChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BufferEventTest {
    private Array2DCharProperty array2dCharProperty;

    @BeforeEach
    public void setup() {
        array2dCharProperty = new Array2DCharProperty(3, 3);
    }

    @Test
    public void testBufferEventSource() {
        BufferEventSource source = new BufferEventSource(2, 3);
        assertEquals(2, source.getRow());
        assertEquals(3, source.getCol());
    }

    @Test
    public void testBufferEvent() {
        BufferEventSource source = new BufferEventSource(1, 1);
        BufferEvent<Character> event = new BufferEvent<>(source, 'A', 'B');

        assertEquals(source, event.getSource());
        assertEquals('A', event.getOldValue());
        assertEquals('B', event.getNewValue());
    }

    @Test
    public void testArray2DProperty_NotifyListener() {
        List<BufferEvent<Character>> recievedEvents = new ArrayList<>();
        BufferChangeListener<Character> listener = recievedEvents::add;
        array2dCharProperty.addListener(listener);

        array2dCharProperty.setValueAt(0, 0, 'X');

        assertEquals(1, recievedEvents.size());
        BufferEvent<Character> event = recievedEvents.get(0);
        assertEquals(0, event.getSource().getRow());
        assertEquals(0, event.getSource().getCol());
        assertNull(event.getOldValue());
        assertEquals('X', event.getNewValue());
    }

    @Test
    public void testArray2DProperty_AddRemoveListener() {
        BufferChangeListener<Character> listener = event -> {};
        array2dCharProperty.addListener(listener);
        assertEquals(1, array2dCharProperty.getListeners().size());

        array2dCharProperty.removeListener(listener);
        assertEquals(0, array2dCharProperty.getListeners().size());
    }

    @Test
    public void testArray2DProperty_DispatchNewBufferArrayEvent() {
        List<BufferEvent<Character>> receivedEvents = new ArrayList<>();
        BufferChangeListener<Character> listener = receivedEvents::add;
        array2dCharProperty.addListener(listener);

        array2dCharProperty.dispatchNewBufferArrayEvent();
        
        assertEquals(1, receivedEvents.size());
        BufferEvent<Character> event = receivedEvents.get(0);
        assertNull(event.getSource());
        assertNull(event.getOldValue());
        assertNull(event.getNewValue());
    }

    @Test
    public void testArray2DProperty_InvalidArrayDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Array2DProperty<>(2, 3, () -> new Character[3][2]));
    }
}
