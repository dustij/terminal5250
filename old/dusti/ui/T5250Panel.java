package com.dusti.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.dusti.config.Theme;
import com.dusti.logic.ScreenManager;
import com.dusti.util.HelperMethods;

public class T5250Panel extends JPanel{
    private final ScreenManager screenManager;
    private final Theme theme;

    public T5250Panel(ScreenManager screenManager, Theme theme) {
        this.screenManager = screenManager;
        this.theme = theme;

        // Initialize HelperMethods to use this panel
        HelperMethods.initialize(this);

        // Apply theme
        this.setFont(theme.getFont());
        this.setBackground(theme.getScreenBackgroundColor());
        this.setPreferredSize(getScreenSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(theme.getFont());
        screenManager.getRenderer().renderScreen(g);  // TODO: implement renderScreen
        
        // Optionally draw a grid for debugging
        // drawGrid(g);
    }

    public Dimension getScreenSize() {
        var screenBuffer = screenManager.getScreenBuffer();
        return new Dimension(screenBuffer.getCols() * HelperMethods.getInstance().getCharWidth(),
                             screenBuffer.getRows() * HelperMethods.getInstance().getCharHeight());
    }

    private void drawGrid(Graphics g) {
        // For debugging purposes
        g.setColor(Color.GRAY);
        var size = this.getSize();
        var charHeight = HelperMethods.getInstance().getCharHeight();
        var charWidth = HelperMethods.getInstance().getCharWidth();
        for (int row = 0; row < size.height; row += charHeight) {
            for (int col = 0; col < size.width; col += charWidth) {
                g.drawRect(col, row, charWidth, charHeight);
            }
        }
    }


}
