package com.dusti.t5250;

import java.awt.Color;

public class CharacterTerminalBuffer {
    private final int columns;
    private final int rows;
    private final char[][] charBuffer;
    private final Color[][] colorBuffer;

    public CharacterTerminalBuffer(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        charBuffer = new char[rows][columns];
        colorBuffer = new Color[rows][columns];
        clear();
    }

    public void clear() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                charBuffer[row][col] = ' ';
                colorBuffer[row][col] = Color.GREEN;
            }
        }
    }

    public void setChar(int col, int row, char c) {
        setChar(col, row, c,  Color.GREEN);
    }

    public void setChar(int col, int row, char c, Color color) {
        if (col >= 0 && col < columns && row >= 0 && row < rows) {
            charBuffer[row][col] = c;
            colorBuffer[row][col] = color;
        }
    }

    public char getChar(int col, int row) {
        return charBuffer[row][col];
    }

    public Color getColor(int col, int row) {
        return colorBuffer[row][col];
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void writeString(int col, int row, String text) {
        writeString(col, row, text, Color.GREEN);
    }

    /**
     * Writes a string to the buffer, starting at the specified column and row.
     * If the string exceeds the current row's width, it wraps to the next row.
     *
     * @param col  Starting column
     * @param row  Starting row
     * @param text String to write
     * @param color Color of the text
     */
    public void writeString(int col, int row, String text, Color color) {
        int currentCol = col;
        int currentRow = row;

        for (char c : text.toCharArray()) {
            if (currentCol >= columns) { // Wrap to the next row
                currentCol = 0;
                currentRow++;
            }

            if (currentRow >= rows) { // Stop writing if rows exceed buffer
                break;
            }

            setChar(currentCol, currentRow, c, color);
            currentCol++;
        }
    }
}
