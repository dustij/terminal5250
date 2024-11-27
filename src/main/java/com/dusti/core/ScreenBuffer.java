package com.dusti.core;

import java.util.logging.Logger;
import com.dusti.events.MatrixCharProperty;
import com.dusti.interfaces.BufferChangeListener;
import com.dusti.interfaces.ScreenElement;
import com.dusti.models.ScreenModel;

public class ScreenBuffer {
    private static final Logger logger = LoggerFactory.getLogger(ScreenBuffer.class.getName());
    private MatrixCharProperty bufferProperty;
    private final int rows;
    private final int cols;

    public ScreenBuffer(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.bufferProperty = new MatrixCharProperty(rows, cols);
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

        // Initialize a new matrix
        Character[][] matrix = new Character[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                matrix[row][col] = ' ';
            }
        }

        // Place elements in matrix by character
        for (ScreenElement elem : model.getElements()) {
            char[] chars = elem.getVisualRepr().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int col = elem.getCol() + i;
                if (col > matrix[elem.getRow()].length - 1) {
                    logger.warning(String.format("\"%s\" does not fit in matrix with %d rows and %d",elem.getVisualRepr(), rows, cols));
                    break;
                }
                matrix[elem.getRow()][col] = chars[i];
            }
        }

        // Reset bufferProperty and add listeners
        bufferProperty = new MatrixCharProperty(matrix);
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
