package com.dusti.t5250;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.Timer;

public class Cursor {
    private int row;
    private int col;
    private ScreenBuffer screenBuffer;
    private T5250Panel panel;

    // Blinking cursor
    private boolean visible;
    private Timer blinkTimer;

    public Cursor(ScreenBuffer screenBuffer) {
        this.screenBuffer = screenBuffer;
        this.row = 0;
        this.col = 0;
        this.visible = true;
        startBlinking();
    }

    public void setPanel(T5250Panel panel) {
        this.panel = panel;
        row = screenBuffer.getFieldCell(panel.getCurrentFieldIndex()).getRow();
        col = screenBuffer.getFieldCell(panel.getCurrentFieldIndex()).getCol();
    }

    private void startBlinking() {
        blinkTimer = new Timer(500, e -> toggleVisiblity());
        blinkTimer.start();
    }

    private void toggleVisiblity() {
        visible = !visible;
        if (panel != null) {
            panel.repaint();
        }
    }

    private void repaintCursor() {
        if (panel != null) {
            panel.repaint();
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public T5250Panel getPanel() {
        return panel;
    }

    public void moveToXY(int x, int y) {
        int cellWidth = panel.getCharWidth();
        int cellHeight = panel.getCharHeight();
        moveTo(y / cellHeight, x / cellWidth);
    }

    public void moveTo(int row, int col) {
        if (row >= 0 && row < screenBuffer.getRows() && col >= 0 && col < screenBuffer.getCols()) {
            this.row = row;
            this.col = col;
            visible = true;
            repaintCursor();
        }
    }

    public void moveBy(int dRow, int dCol) {
        moveTo(row + dRow, col + dCol);
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

    public void render(Graphics g, FontMetrics fm, int cellWidth, int cellHieght) {
        if (!visible) return;

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

