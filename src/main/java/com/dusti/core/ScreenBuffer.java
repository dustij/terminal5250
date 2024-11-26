package com.dusti.core;

import com.dusti.events.Array2DCharProperty;
import com.dusti.interfaces.BufferChangeListener;

public class ScreenBuffer {
    private Array2DCharProperty bufferProperty;
    private final int rows;
    private final int cols;

    public ScreenBuffer(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.bufferProperty = new Array2DCharProperty(rows, cols);
        initializeBuffer();
    }

    private void initializeBuffer() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                bufferProperty.setValueAt(row, col, ' ');
            }
        }
    }

    public void replaceBuffer(Character[][] array2D) {
        // Add all previous listeners to this new buffer
        var listeners = bufferProperty.getListeners();
        bufferProperty = new Array2DCharProperty(array2D);
        for (var listener : listeners) {
            bufferProperty.addListener(listener);
        }
        bufferProperty.dispatchNewBufferArrayEvent();
    }

    public void addListener(BufferChangeListener<Character> listener) {
        bufferProperty.addListener(listener);
    }

    public void removeListener(BufferChangeListener<Character> listener) {
        bufferProperty.removeListener(listener);
    }

    public char getCharAt(int row, int col) {
        return bufferProperty.getValueAt(row, col);
    }

    public void setCharAt(int row, int col, char value) {
        bufferProperty.setValueAt(row, col, value);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
