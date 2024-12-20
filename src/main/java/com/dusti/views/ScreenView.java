package com.dusti.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.dusti.core.ScreenBuffer;
import com.dusti.events.BufferEvent;
import com.dusti.interfaces.BufferChangeListener;
import com.dusti.views.components.Cursor;
import com.dusti.views.themes.Theme;

public class ScreenView extends JPanel implements BufferChangeListener<Character> {
    private final Theme theme;
    private final ScreenBuffer screenBuffer;
    private final Cursor cursor;
    private final FontMetrics fm;
    private final int rows;
    private final int cols;
    private final int charWidth;
    private final int charHeight;
    private final int width;
    private final int height;

    public ScreenView(ScreenBuffer screenBuffer) {
        this.theme = new Theme();
        this.cursor = new Cursor(0, 0, this);
        this.screenBuffer = screenBuffer;
        this.rows = screenBuffer.getRows();
        this.cols = screenBuffer.getCols();

        this.setFont(theme.getFont());
        this.fm = this.getFontMetrics(theme.getFont());
        this.charWidth = fm.charWidth('W');
        this.charHeight = fm.getHeight();
        this.width = cols * charWidth;
        this.height = rows * charHeight;

        this.setBackground(theme.getScreenBackgroundColor());
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(this.getPreferredSize());

        this.screenBuffer.addListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw characters from buffer
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char ch = screenBuffer.getCharAt(row, col);
                Color fg = screenBuffer.getFgColorAt(row, col);
                Color bg = screenBuffer.getBgColorAt(row, col);

                // Cell background color
                g.setColor(bg);
                g.fillRect(relX(col), relY(row), getCharWidth(), getCharHeight());

                // Draw character
                g.setColor(fg);
                g.drawString(String.valueOf(ch), relX(col), relCharY(row));

                // Input field
                if (!screenBuffer.isProtectedCell(row, col)) {
                    // Draw column separators
                    g.setColor(theme.getColumnSeparatorColor());
                    // Left side
                    g.drawLine(relX(col),
                               relY(row), 
                               relX(col),
                               relY(row) - 2); // -2: Creates the small vertical tick up
                    // Right side
                    g.drawLine(relX(col) + getCharWidth(), 
                               relY(row) ,
                               relX(col) + getCharWidth(), 
                               relY(row) - 2);
                    // Color for input fields
                    g.setColor(theme.getInputColor());
                    // Draw line under input fields
                    g.drawLine(relX(col), relY(row), relX(col + 1), relY(row));
                }

            }
        }

        cursor.render(g, fm);

        // For testing purposes
        // drawGrid(g);
    }

    @Override
    public void onBufferChange(BufferEvent<Character> event) {
        repaint();
    }

    public int relX(int col) {
        return col * charWidth;
    }

    public int relY(int row) {
        return (row + 1) * charHeight;
    }

    public int relCharY(int row) {
        return relY(row) - fm.getDescent();
    }

    public ScreenBuffer getScreenBuffer() {
        return screenBuffer;
    }

    public Cursor getTextCursor() {
        return cursor;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCharWidth() {
        return charWidth;
    }

    public int getCharHeight() {
        return charHeight;
    }

    private void drawGrid(Graphics g) {
        // For testing purposes
        g.setColor(Color.GRAY);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row == rows - 1)
                    g.setColor(Color.MAGENTA);
                g.drawRect(col * charWidth, row * charHeight, charWidth, charHeight);
            }
        }
    }
}
