package com.dusti.t5250;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class T5250Frame extends JFrame {
    private T5250ScreenBuffer screenBuffer;
    private T5250Panel panel;

    public T5250Frame(String title) {
        super(title);

        screenBuffer = new T5250ScreenBuffer(24, 80);
        panel = new T5250Panel(screenBuffer);

        System.out.println(screenBuffer.getProtectedArea()[0][0]);

        // Default frame to fill entire screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        this.setMinimumSize(panel.getMinimumSize());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);

        this.add(panel, new GridBagConstraints());
    }
}
