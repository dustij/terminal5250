package com.dusti.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import com.dusti.config.ConfigLoader;
import com.dusti.config.Theme;
import com.dusti.logic.ScreenManager;

public class T5250Frame extends JFrame {
    private final ScreenManager screenManager;
    private final T5250Panel panel;

    public T5250Frame(String title) {
        super(title);

        // Initialize theme
        Theme theme = new Theme();

        // Apply theme to frame
        this.getContentPane().setBackground(theme.getScreenBackgroundColor());
        
        // Initialize ScreenManager and Panel
        this.screenManager = new ScreenManager();
        this.panel = new T5250Panel(screenManager, theme);

        setupUI();
        setupKeyBindings();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupUI() {
        // Adjust minimum size
        Dimension panelSize = panel.getPreferredSize();
        this.setSize(panelSize);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
    }

    private void setupKeyBindings() {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        // Up arrow key
        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.moveCursorUp();
            }
        });

        // Down arrow key
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.moveCursorDown();
            }
        });

        // Left arrow key
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.moveCursorLeft();
            }
        });

        // Right arrow key
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.moveCursorRight();
            }
        });

        // Tab key
        inputMap.put(KeyStroke.getKeyStroke("TAB"), "handleTab");
        actionMap.put("handleTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.handleTabPressed();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("shift TAB"), "handleTab");
        actionMap.put("handleTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.handleShftTabPressed();
            }
        });

        // Backspace key
        inputMap.put(KeyStroke.getKeyStroke("BACK_SPACE"), "handleBackspace");
        actionMap.put("handleBackspace", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenManager.handleBackspacePressed();
            }
        });

        // Delete key
        inputMap.put(KeyStroke.getKeyStroke("DELETE"), "handleDelete");
        actionMap.put("handleDelete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenManager.handleDeletePressed();
            }
        });

        // Magic thing to handle typing any character, refer to ASCII table
        for (char c = ' '; c <= '~'; c++) {
            final char ch = c; // type safe for actionPerformed below
            String actionName = "type" + ch;
            inputMap.put(KeyStroke.getKeyStroke(ch), actionName);
            actionMap.put(actionName, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    screenManager.handleCharPressed(ch);
                }
            });
        }
    }
}
