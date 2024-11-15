package com.dusti.t5250;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class CharTerminal extends JPanel{
    private int cols;
    private int rows;
    private Font termnalFont;

    public CharTerminal(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        termnalFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
        setFont(termnalFont);
        setBackground(Color.BLACK);
        setForeground(Color.GREEN);
        setPreferredSize(calcPreferredSize());
    }

    private Dimension calcPreferredSize() {
        FontMetrics metrics = getFontMetrics(termnalFont);
        int charWidth = metrics.charWidth('W');
        int charHeight = metrics.getHeight();
        return new Dimension(cols *charWidth, rows * charHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Clear background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // Set font color
        g.setColor(getForeground());
        g.setFont(termnalFont);

        // Draw char grid (optional for debugging)
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        FontMetrics metrics = getFontMetrics(termnalFont);
        int charWidth = metrics.charWidth('W');
        int charHeight = metrics.getHeight();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g.drawRect(col *charWidth, row * charHeight, charWidth, charHeight);
            }
        }
    }
    
}
