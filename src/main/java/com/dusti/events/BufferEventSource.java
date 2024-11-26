package com.dusti.events;

public class BufferEventSource {
    private final int row;
    private final int col;
    
    public BufferEventSource(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    
}
