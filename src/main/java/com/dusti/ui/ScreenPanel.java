package com.dusti.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.dusti.config.Theme;
import com.dusti.core.ScreenBuffer;
import com.dusti.core.ScreenManager;
import com.dusti.events.BufferEvent;
import com.dusti.interfaces.BufferChangeListener;

public class ScreenPanel extends JPanel implements BufferChangeListener<Character> {
    private Theme theme;
    private final ScreenBuffer screenBuffer;
    private final int rows;
    private final int cols;
    private final int charWidth;
    private final int charHeight;
    private final int width;
    private final int height;

    public ScreenPanel(ScreenBuffer screenBuffer) {
        this.theme = new Theme();
        this.screenBuffer = screenBuffer;
        this.rows = screenBuffer.getRows();
        this.cols = screenBuffer.getCols();
        this.charWidth = this.getFontMetrics(theme.getFont()).charWidth('W');
        this.charHeight = this.getFontMetrics(theme.getFont()).getHeight();
        this.width = cols * charWidth;
        this.height = rows * charHeight;

        this.setFont(theme.getFont());
        this.setBackground(theme.getScreenBackgroundColor());
        this.setPreferredSize(new Dimension(width, height));

        this.screenBuffer.addListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(theme.getFieldColor());

        // Draw characters from buffer
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char ch = screenBuffer.getCharAt(row, col);
                g.drawString(String.valueOf(ch), relX(col), relY(row));
            }
        }
    }

    @Override
    public void onBufferChange(BufferEvent<Character> event) {
        repaint();
    }

    private int relX(int col) {
        return col * charWidth;
    }

    private int relY(int row) {
        return (row + 1) * charHeight - this.getFontMetrics(theme.getFont()).getDescent();
    }

}
