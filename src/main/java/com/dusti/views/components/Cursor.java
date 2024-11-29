package com.dusti.views.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.Timer;
import com.dusti.views.ScreenView;

public class Cursor {
    private ScreenView screenView;
    private boolean insertMode;
    private int row;
    private int col;
    private boolean visible;
    private Timer blinkTimer;

    public Cursor(int row, int col, ScreenView screenView) {
        this.row = row;
        this.col = col;
        this.screenView = screenView;
        insertMode = false;
        visible = true;
        startBlinking();
    }

    public void render(Graphics g, FontMetrics fm) {
        if (!visible) return;

        var x = getX();
        var y = getY();
        var width = getWidth();
        var height = getHeight();

        var originalFgColor = g.getColor();
        var originalBgColor = Color.BLACK;
        var invertedFgColor = invertColor(originalFgColor);
        var invertedBgColor = invertColor(originalBgColor);

        // Draw rectangle cursor
        g.setColor(invertedBgColor);
        g.fillRect(x, y, width, height);

        // Draw foreground content
        var ch = screenView.getScreenBuffer().getCharAt(row, col);
        x = screenView.relX(col);
        y = screenView.relCharY(row);
        g.setColor(invertedFgColor);
        g.drawString(String.valueOf(ch), x, y);

        // Restore colors
        g.setColor(originalFgColor);
    }

    private void startBlinking() {
        blinkTimer = new Timer(500, e -> {
            visible = !visible;
            repaint();
        });
        blinkTimer.start();
    }

    public void repaint() {
        if (screenView != null) {
            screenView.repaint();
        }
    }

    public boolean isInsertMode() {
        return insertMode;
    }

    public void setInsertMode(boolean insertMode) {
        this.insertMode = insertMode;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getX() {
        return col * getWidth();
    }

    public int getY() {
        return row * getHeight();
    }

    public int getWidth() {
        return screenView.getCharWidth();
    }

    public int getHeight() {
        return screenView.getCharHeight();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getScreenRect() {
        return new Rectangle(screenView.getScreenBuffer().getCols(), screenView.getScreenBuffer().getRows());
    }
    
    private Color invertColor(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }

}
