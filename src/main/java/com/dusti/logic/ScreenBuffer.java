package com.dusti.logic;

import java.awt.Color;
import com.dusti.config.Theme;
import com.dusti.models.Cell;
import com.dusti.models.Field;
import com.dusti.models.Position;
import com.dusti.models.Screen;
import com.dusti.ui.Renderer;

public class ScreenBuffer {
    private final int rows;
    private final int cols;
    private final Cell[][] buffer;
    private final boolean[][] protectedCells;
    private final Color[][] fgColors;
    private final Color[][] bgColors;

    private final Renderer renderer;

    private final Theme theme;
    private int cursorRow;
    private int cursorCol;

    public ScreenBuffer(int rows, int cols, Theme theme) {
        this.rows = rows;
        this.cols = cols;
        this.buffer = new Cell[rows][cols];
        this.protectedCells = new boolean[rows][cols];
        this.fgColors = new Color[rows][cols];
        this.bgColors = new Color[rows][cols];
        this.renderer = new Renderer(this);
        this.theme = theme;

        clearBuffer();
    }

    public void clearBuffer() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                buffer[row][col] = new Cell(' ', new Position(row, col));
                protectedCells[row][col] = true;
                fgColors[row][col] = theme.getFieldColor();
                bgColors[row][col] = theme.getScreenBackgroundColor();
            }
        }
    }

    public void loadScreen(Screen screen) {
        clearBuffer();
        for (Field field : screen.getFields()) {
            insertField(field);
        }       
    }

    public void insertField(Field field) {
        var position = field.getStartPosition();
        var row = position.getRow();
        var col = position.getCol();

        for (int i = 0; i < field.length(); i++) {
            var cell = buffer[row][col + i];

            if (col + i >= field.getInputPosition().getCol()) {
                protectedCells[row][col + i] = false;
                fgColors[row][col + i] = theme.getInputColor();
                cell.addListener(field);
            }

            // Set initial value (useful for fields that edit existing data)
            cell.setValue(field.getCharAt(i));                                      // TODO: not getting field data correctly, work on implementation
        }
    }
    
    public Renderer getRenderer() {
        return renderer;
    }

    public Cell[][] getBuffer() {
        return buffer;
    }

    public void moveCursor(int i, int j) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveCursor'");
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public char getCharAt(int row, int col) {
        return buffer[row][col].getValue();
    }

    public void setCharAt(char value, int row, int col) {
        getCellAt(row, col).setValue(value);
    }

    public Cell getCellAt(int row, int col) {
        return buffer[row][col];
    }

    public Color getForegroundColorAt(int row, int col) {
        return fgColors[row][col];
    }

    public Color getBackgroundColorAt(int row, int col) {
        return bgColors[row][col];
    }
}
