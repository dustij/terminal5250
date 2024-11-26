package com.dusti.core;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.dusti.views.ScreenView;
import com.dusti.views.themes.Theme;

public class MainFrame extends JFrame {
    private final JPanel screenView;
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
        this.screenView = new ScreenView(screenBuffer);
        this.setSize(screenView.getPreferredSize());
        this.setLayout(new BorderLayout());
        this.add(screenView, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    // TODO: implement key handlers, they will interactor with screenManager when switching screens
}
