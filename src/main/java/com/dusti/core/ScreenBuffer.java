package com.dusti.core;

import com.dusti.events.Array2DCharProperty;
import com.dusti.interfaces.BufferChangeListener;

public class ScreenBuffer {
    private final Array2DCharProperty buffer;
    private final int rows;
    private final int cols;

    public ScreenBuffer(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.buffer = new Array2DCharProperty(rows, cols);
        initializeBuffer();
    }

    private void initializeBuffer() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                buffer.setValueAt(row, col, ' ');
            }
        }
    }

    public void addListener(BufferChangeListener<Character> listener) {
        buffer.addListener(listener);
    }

    public void removeListener(BufferChangeListener<Character> listener) {
        buffer.removeListener(listener);
    }

    public char getCharAt(int row, int col) {
        return buffer.getValueAt(row, col);
    }

    public void setCharAt(int row, int col, char value) {
        buffer.setValueAt(row, col, value);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
