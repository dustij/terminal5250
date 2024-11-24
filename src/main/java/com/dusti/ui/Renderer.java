package com.dusti.ui;

import java.awt.Graphics;
import com.dusti.logic.ScreenBuffer;
import com.dusti.util.HelperMethods;

public class Renderer {
    public final ScreenBuffer screenBuffer;

    public Renderer(ScreenBuffer screenBuffer) {
        this.screenBuffer = screenBuffer;
    }

    public void renderScreen(Graphics g) {
        var charHeight = HelperMethods.getInstance().getCharHeight();
        var charWidth = HelperMethods.getInstance().getCharWidth();
        var metrics = HelperMethods.getInstance().getMetrics();
        
        // Draw the buffer (bottom up, right to left, because issues drawing field underlines)
        for (int row = screenBuffer.getRows() - 1; row >= 0; row--) {
            for (int col = screenBuffer.getCols() - 1; col >= 0; col--) {
                var ch = screenBuffer.getCharAt(row, col);
                var fg = screenBuffer.getForegroundColorAt(row, col);
                var bg = screenBuffer.getBackgroundColorAt(row, col);

                // Draw background
                g.setColor(bg);
                int x = col * charWidth;
                int y = row * charHeight;
                g.fillRect(x, y, charWidth, charHeight);

                // Draw character aligned to left of cell and baseline at bottom of cell
                g.setColor(fg);
                g.drawString(String.valueOf(ch), x, (row + 1) * charHeight - metrics.getDescent());
            }
        }
    }
}
