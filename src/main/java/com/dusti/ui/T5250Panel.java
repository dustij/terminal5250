package com.dusti.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.dusti.config.Theme;
import com.dusti.logic.ScreenManager;

public class T5250Panel extends JPanel{
    private final ScreenManager screenManager;
    private final Theme theme;

    public T5250Panel(ScreenManager screenManager, Theme theme) {
        this.screenManager = screenManager;
        this.theme = theme;

        // Apply theme
        this.setFont(theme.getFont());
        this.setBackground(theme.getScreenBackgroundColor());
        this.setPreferredSize(screenManager.getScreenSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(theme.getFont());
        screenManager.getRenderer().renderScreen(g);
        
        // Optionally draw a grid for debugging
        // drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        // For debugging purposes
        g.setColor(Color.GRAY);
        Dimension size = this.getSize();
        for (int row = 0; row < size.height; row += screenManager.getCharHeight()) {
            for (int col = 0; col < size.width; col += screenManager.getCharWidth()) {
                g.drawRect(col, row, screenManager.getCharWidth(), screenManager.getCharHeight());
            }
        }
    }
}
