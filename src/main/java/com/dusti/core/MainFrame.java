package com.dusti.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JFrame;
import com.dusti.controllers.CursorController;
import com.dusti.views.ScreenView;
import com.dusti.views.themes.Theme;

public class MainFrame extends JFrame {
    private final ScreenView screenView;
    private final CursorController cursorController;
    private final ScreenBuffer screenBuffer;
    private final ScreenManager screenManager;
    private final KeyboardHandler keyboardHandler;
    private final MouseHandler mouseHandler;

    public MainFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusTraversalKeysEnabled(false);

        // Initialize theme
        Theme theme = new Theme();

        // Apply theme to frame
        this.getContentPane().setBackground(theme.getScreenBackgroundColor());

        this.screenBuffer = new ScreenBuffer(27, 80);
        this.screenManager = new ScreenManager(screenBuffer);
        screenManager.setActiveScreen("home");

        this.screenView = new ScreenView(screenBuffer);
        this.cursorController = new CursorController(screenView.getTextCursor());

        this.keyboardHandler = new KeyboardHandler(screenBuffer, cursorController);
        this.keyboardHandler.setInputMap(screenView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
        this.keyboardHandler.setActionMap(screenView.getActionMap());
        this.keyboardHandler.initKeyBindings();

        this.mouseHandler = new MouseHandler(cursorController);
        this.mouseHandler.addListener(screenView);

        // Set minimum size
        this.pack();
        Insets insets = this.getInsets();
        int frameMinWidth = screenView.getMinimumSize().width + insets.left + insets.right;
        int frameMinHeight = screenView.getMinimumSize().height + insets.top + insets.bottom;
        this.setMinimumSize(new Dimension(frameMinWidth, frameMinHeight));

        this.setLayout(new BorderLayout());
        this.add(screenView, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }
}
