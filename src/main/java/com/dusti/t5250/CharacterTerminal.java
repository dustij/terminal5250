package com.dusti.t5250;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class CharacterTerminal extends JPanel{
    private final CharacterTerminalBuffer buffer;
    private int cols;
    private int rows;
    private Font termnalFont;

    public CharacterTerminal(CharacterTerminalBuffer buffer) {
        this.buffer = buffer;
        cols = buffer.getColumns();
        rows = buffer.getRows();
        termnalFont = new Font(Font.MONOSPACED, Font.PLAIN, 16);
        setFont(termnalFont);
        setBackground(Color.BLACK);
        setForeground(Color.GREEN);
        setPreferredSize(calcPreferredSize());
    }

    private Dimension calcPreferredSize() {
        return new Dimension(cols * getCharWidth(), rows * getCharHeight());
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

        FontMetrics metrics = getFontMetrics(termnalFont);
        for (int row = 0; row < buffer.getRows(); row++) {
            for (int col = 0; col < buffer.getColumns(); col++) {
                char c = buffer.getChar(col, row);
                Color color = buffer.getColor(col, row);
                g.setColor(color);
                g.drawString(String.valueOf(c), col * getCharWidth(), (row + 1) * getCharHeight() - metrics.getDescent());
            }
        }

        // Draw char grid (optional for debugging)
        // drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.GRAY);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g.drawRect(col * getCharWidth(), row * getCharHeight(), getCharWidth(), getCharHeight());
            }
        }
    }

    private int getCharWidth() {
        FontMetrics metrics = getFontMetrics(termnalFont);
        int charWidth = metrics.charWidth('W');
        return charWidth;
    }

    private int getCharHeight() {
        FontMetrics metrics = getFontMetrics(termnalFont);
        int charHeight = metrics.getHeight();
        return charHeight;
    }
    
}
