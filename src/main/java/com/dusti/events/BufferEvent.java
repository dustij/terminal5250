package com.dusti.events;

public class BufferEvent<T> {
    private final int row;
    private final int col;
    private final T oldValue;
    private final T newValue;

    public BufferEvent(int row, int col, T oldValue, T newValue) {
        this.row = row;
        this.col = col;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    
}
