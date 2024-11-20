package com.dusti.t5250;

import java.awt.Color;
import java.util.Arrays;
import java.util.logging.Logger;

public class ScreenBuffer {
    private final int rows;
    private final int cols;
    private final char[][] screenBuffer;
    private final boolean[][] protectedArea;
    private final Color[][] fgColor;
    private final Color[][] bgColor;

    private final Logger LOGGER;

    public ScreenBuffer(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        screenBuffer = new char[rows][cols];
        protectedArea = new boolean[rows][cols];
        fgColor = new Color[rows][cols];
        bgColor = new Color[rows][cols];

        // Default entire screen as protected
        entireScreenToProtected();

        // Default green on black
        setDefaultColors();

        LOGGER = MyLogger.getLogger(this.getClass().getName());
        clear();

        // TODO: last row in the buffer is the message display, implement display proctected area message when typing in protected area

        // Testing, remove this later
        insertField(new Field("Test", 20, 10), 1, 1);
        insertField(new Field("Testing", 20, 10), 2, 1);
    }

    public void insertField(Field field, int row, int col) {
        for (int i = 0; i < field.length(); i++) {
            screenBuffer[row][col+i] = field.getCharAt(i);
            if (i >= field.inputStartPoint()) {
                // Input area is unprotected
                protectedArea[row][col+i] = false;
            }
        }
    }

    public void writeChar(Character c, int row, int col) throws IndexOutOfBoundsException {
        if (row >= 0 && row < rows && col >= 0 & col < cols) {
            screenBuffer[row][col] = c;
        } else {
            LOGGER.warning("Tried to write char to invalid position (row or col is out of bounds)");
            throw new IndexOutOfBoundsException();
        }
    }

    public void writeText(String text, int row, int col) {
        for (int i = 0; i < text.length(); i++) {
            try {
                writeChar(text.charAt(i), row, col + i);
            } catch (IndexOutOfBoundsException e) {
                // Stop writing text if it goes off screen, prevents excessive logging
                break;
            }
        }
    }

    public void clearCell(int row, int col) {
        screenBuffer[row][col] = ' ';
    }

    public void clear() {
        for (char[] row : screenBuffer) {
            Arrays.fill(row, ' ');
        }
    }

    public boolean isProtectedCell(int row, int col) {
        return protectedArea[row][col];
    }

    public void entireScreenToProtected() {
        for (boolean[] row : protectedArea) {
            Arrays.fill(row, true);
        }
    }

    public void setDefaultColors() {
        for (int row = 0; row < rows; row++) {
            Arrays.fill(fgColor[row], Color.GREEN);
            Arrays.fill(bgColor[row], Color.BLACK);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char getCharAt(int row, int col) {
        return screenBuffer[row][col];
    }

    public Color getForegroundColorAt(int row, int col) {
        return fgColor[row][col];
    }

    public Color getBackgroundColorAt(int row, int col) {
        return bgColor[row][col];
    }

    public char[][] getScreenBuffer() {
        return screenBuffer;
    }

    public boolean[][] getProtectedArea() {
        return protectedArea;
    }

    
}
