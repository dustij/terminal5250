package com.dusti.t5250;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class T5250Panel extends JPanel {
    private final ScreenBuffer screenBuffer;
    private final Cursor cursor;
    private int rows;
    private int cols;
    private Font font;

    public T5250Panel(ScreenBuffer screenBuffer, Cursor cursor) {
        this.screenBuffer = screenBuffer;
        this.cursor = cursor;

        rows = screenBuffer.getRows();
        cols = screenBuffer.getCols();

        font = new Font(Font.MONOSPACED, Font.PLAIN, 26);
        
        this.setFont(font);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(calcPreferredSize());
        this.setMinimumSize(calcPreferredSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the buffer
        FontMetrics metrics = getFontMetrics(font);
        for (int row = 0; row < screenBuffer.getRows(); row++) {
            for (int col = 0; col < screenBuffer.getCols(); col++) {
                char ch = screenBuffer.getCharAt(row, col);
                Color fg = screenBuffer.getForegroundColorAt(row, col);
                Color bg = screenBuffer.getBackgroundColorAt(row, col);

                // Draw background
                g.setColor(bg);
                int x = col * getCharWidth();
                int y = row * getCharHeight();
                g.fillRect(x, y, getCharWidth(), getCharHeight());

                // Draw character aligned to left of cell and baseline at bottom of cell
                g.setColor(fg);
                g.drawString(String.valueOf(ch), col * getCharWidth(), (row + 1) * getCharHeight() - metrics.getDescent());
            }
        }

        if (cursor.getPanel() == null) {
            cursor.setPanel(this);
        }
        cursor.render(g, metrics, getCharWidth(), getCharHeight());

        // For testing purposes
        drawGrid(g);
    }

    public Dimension calcPreferredSize() {
        return new Dimension(cols * getCharWidth(), rows * getCharHeight());
    }

    public int getCharWidth() {
        FontMetrics metrics = getFontMetrics(font);
        int charWidth = metrics.charWidth('W');
        return charWidth;
    }

    public int getCharHeight() {
        FontMetrics metrics = getFontMetrics(font);
        int charHeight = metrics.getHeight();
        return charHeight;
    }

    private void drawGrid(Graphics g) {
        // For testing purposes
        g.setColor(Color.GRAY);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g.drawRect(col * getCharWidth(), row * getCharHeight(), getCharWidth(), getCharHeight());
            }
        }
    }

    
}
