package com.dusti.interfaces;

import com.dusti.events.BufferEvent;

@FunctionalInterface
public interface BufferChangeListener<T> {
    void onBufferChange(BufferEvent<T> event);
}
