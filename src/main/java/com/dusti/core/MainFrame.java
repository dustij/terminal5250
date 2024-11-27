package com.dusti.core;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import com.dusti.views.CursorView;
import com.dusti.views.ScreenView;
import com.dusti.views.themes.Theme;

public class MainFrame extends JFrame {
    private final JPanel screenView;
    private final JPanel cursorView;
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
        this.cursorView = new CursorView();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(screenView.getPreferredSize());
        layeredPane.setLayout(null);
        screenView.setBounds(0, 0, screenView.getWidth(), screenView.getHeight());
        cursorView.setBounds(0, 0, screenView.getWidth(), screenView.getHeight());

        // DEFAULT_LAYER - The standard layer, where most components go. This the bottommost layer.
        // PALETTE_LAYER - The palette layer sits over the default layer. Useful for floating toolbars and palettes, so they can be positioned above other components.
        layeredPane.add(screenView, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(cursorView, JLayeredPane.PALETTE_LAYER);

        this.setSize(screenView.getPreferredSize());
        this.setLayout(new BorderLayout());
        this.add(layeredPane, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }
}
