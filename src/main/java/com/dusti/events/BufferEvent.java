package com.dusti.events;

public class BufferEvent<T> {
    private final BufferEventSource source;
    private final T oldValue;
    private final T newValue;

    public BufferEvent(BufferEventSource source, T oldValue, T newValue) {
        this.source = source;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public BufferEventSource getSource() {
        return source;
    }
}
