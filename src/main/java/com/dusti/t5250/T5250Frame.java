package com.dusti.t5250;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class T5250Frame extends JFrame {
    private ScreenBuffer screenBuffer;
    private Cursor cursor;
    private T5250Panel panel;

    public T5250Frame(String title) {
        super(title);
        this.getContentPane().setBackground(Config.getBaseBackground());

        screenBuffer = new ScreenBuffer(24, 80);
        cursor = new Cursor(screenBuffer);
        panel = new T5250Panel(screenBuffer, cursor);

        setupKeyBindings();
        setupMouseListeners();

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

    private void setupKeyBindings() {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        // Up arrow key
        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenBuffer.clearMessage();
                cursor.moveUp();
            }
        });

        // Down arrow key
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenBuffer.clearMessage();
                cursor.moveDown();
            }
        });

        // Left arrow key
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenBuffer.clearMessage();
                cursor.moveLeft();
            }
        });

        // Right arrow key
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenBuffer.clearMessage();
                cursor.moveRight();
            }
        });

        // Backspace key
        inputMap.put(KeyStroke.getKeyStroke("BACK_SPACE"), "handleBackspace");
        actionMap.put("handleBackspace", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenBuffer.clearMessage();
                int row = cursor.getRow();
                int col = cursor.getCol() - 1;
                if (col < 0)
                    return; // Do nothing if on the left edge
                if (!screenBuffer.isProtectedCell(row, col)) {
                    screenBuffer.clearCell(row, col);
                    cursor.moveLeft();
                }
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
                    screenBuffer.clearMessage();
                    if (!screenBuffer.isProtectedCell(cursor.getRow(), cursor.getCol())) {
                        screenBuffer.writeChar(ch, cursor.getRow(), cursor.getCol());
                        cursor.moveRight();
                    } else {
                        screenBuffer.messageProtectedArea();
                    }
                }
            });
        }
    }

    private void setupMouseListeners() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cursor.moveToXY(e.getX(), e.getY());
            }
        });
    }
}
