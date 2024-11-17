package com.dusti.t5250;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Cursor {
    private int row;
    private int col;
    private T5250ScreenBuffer screenBuffer;

    public Cursor(T5250ScreenBuffer screenBuffer) {
        this.screenBuffer = screenBuffer;
        this.row = 0;
        this.col = 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void moveTo(int row, int col) {
        if (row >= 0 && row < screenBuffer.getRows() && col >= 0 && col < screenBuffer.getCols()) {
            this.row = row;
            this.col = col;
        }
    }

    public void moveBy(int dRow, int dCol) {
        moveTo(row + dRow, col + dCol);
    }

    public void render(Graphics g, FontMetrics fm, int cellWidth, int cellHieght) {
        int x = col * cellWidth;
        int y = row * cellHieght;

        Color origninalFg = g.getColor();
        Color originalBg = screenBuffer.getBackgroundColorAt(row, col);
        Color invertedFg = invertColor(origninalFg);
        Color invertedBg = invertColor(originalBg);

        // Draw block cursor
        g.setColor(invertedBg);
        g.fillRect(x, y, cellWidth, cellHieght);
        char ch = screenBuffer.getCharAt(row, col);
        g.setColor(invertedFg);
        g.drawString(String.valueOf(ch), col * cellWidth, (row + 1) * cellHieght - fm.getDescent());

        // Restore graphics colors
        g.setColor(origninalFg);
    }

    private Color invertColor(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }
}

