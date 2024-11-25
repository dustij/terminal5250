package com.dusti.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.dusti.config.Theme;
import com.dusti.core.ScreenBuffer;

public class MainFrame extends JFrame {
    private JPanel screenPanel;

    public MainFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize theme
        Theme theme = new Theme();

        // Apply theme to frame
        this.getContentPane().setBackground(theme.getScreenBackgroundColor());

        // Setup
        setupUI();
    }

    private void setupUI() {
        this.screenPanel = new ScreenPanel(new ScreenBuffer(27, 80));
        this.setSize(screenPanel.getPreferredSize());
        this.setLayout(new BorderLayout());
        this.add(screenPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }
}
