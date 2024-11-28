package com.dusti.controllers;

import com.dusti.core.ScreenBuffer;
import com.dusti.views.components.Cursor;

public class CursorController {
    private Cursor cursor;

    public CursorController(Cursor cursor) {
        this.cursor = cursor;
    }

    public void moveLeft() {
        moveBy(0, -1);
    }

    public void moveRight() {
        moveBy(0, 1);
    }
    
    public void moveUp() {
        moveBy(-1, 0);
    }
    
    public void moveDown() {
        moveBy(1, 0);
    }
    
    public void moveBy(int dRow, int dCol) {
        moveTo(cursor.getRow() + dRow, cursor.getCol() + dCol);
    }

    public void moveTo(int row, int col) {
        int screenRows = (int) cursor.getScreenRect().getHeight();
        int screenCols = (int) cursor.getScreenRect().getWidth();

        // Wrap around (from top, from left)
        if (row < 0) row = screenRows - 1;
        if (col < 0) col = screenCols - 1;

        // Wrap around (from bottom, from right)
        row = row % screenRows;
        col = col % screenCols;

        cursor.setRow(row);
        cursor.setCol(col);
        cursor.setVisible(true);

        cursor.repaint();
    }
}
