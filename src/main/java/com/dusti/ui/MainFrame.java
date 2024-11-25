package com.dusti.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.dusti.config.Theme;
import com.dusti.core.ScreenBuffer;
import com.dusti.core.ScreenManager;

public class MainFrame extends JFrame {
    private final JPanel screenPanel;
    private final ScreenBuffer screenBuffer;
    private final ScreenManager screenManager;

    public MainFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize theme
        Theme theme = new Theme();

        // Apply theme to frame
        this.getContentPane().setBackground(theme.getScreenBackgroundColor());

        this.screenBuffer = new ScreenBuffer(27, 80);
        this.screenManager = new ScreenManager(screenBuffer);
        this.screenPanel = new ScreenPanel(screenBuffer);
        this.setSize(screenPanel.getPreferredSize());
        this.setLayout(new BorderLayout());
        this.add(screenPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    // TODO: implement key handlers, they will interactor with screenManager when switching screens
}
