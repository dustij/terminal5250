package com.dusti.core;

import java.util.Arrays;
import java.util.logging.Logger;
import com.dusti.events.Array2DCharProperty;
import com.dusti.interfaces.BufferChangeListener;
import com.dusti.models.ScreenModel;

public class ScreenBuffer {
    private static final Logger logger = LoggerFactory.getLogger(ScreenBuffer.class.getName());
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

    public void updateFromModel(ScreenModel model) {
        // If no model then do nothing
        if (model == null) {
            logger.warning("Tried to update buffer from a null model.");
            return;
        }

        // Get listeners so that we can add them to new bufferProperty
        var listeners = bufferProperty.getListeners();

        // TODO: remove this, only for testing
        Character[][] array2D = new Character[27][80];
        for (int i = 0; i < 27; i++) {
            Arrays.fill(array2D[i], 'A');
        }
        bufferProperty = new Array2DCharProperty(array2D);


        for (var listener : listeners) {
            bufferProperty.addListener(listener);
        }
        bufferProperty.dispatchUpdateEvent();
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
