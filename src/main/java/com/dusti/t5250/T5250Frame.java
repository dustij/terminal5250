package com.dusti.t5250;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class T5250Frame extends JFrame {
    private ScreenBuffer screenBuffer;
    private Cursor cursor;
    private T5250Panel panel;
    private KeyStroker keyStroker;

    public T5250Frame(String title) {
        super(title);
        this.getContentPane().setBackground(Config.getBaseBackground());
        
        screenBuffer = new ScreenBuffer(24, 80);
        cursor = new Cursor(screenBuffer);
        panel = new T5250Panel(screenBuffer, cursor);
        keyStroker = new KeyStroker(screenBuffer, cursor, panel);
        
        // Adjust minimum size
        Dimension panelMinSize = panel.getMinimumSize();
        
        // Ensures insets are available
        this.pack();
        Insets insets = this.getInsets();
        int frameMinWidth = panelMinSize.width + insets.left + insets.right;
        int frameMinHeight = panelMinSize.height + insets.top + insets.bottom;
        this.setMinimumSize(new Dimension(frameMinWidth, frameMinHeight));

        // Default frame to fill entire screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.add(panel, new GridBagConstraints());

        this.setLocationRelativeTo(null);

        this.setFocusable(true);
        this.requestFocusInWindow();
    }
}
