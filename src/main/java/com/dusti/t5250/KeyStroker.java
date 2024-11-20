package com.dusti.t5250;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class KeyStroker {
    private final ScreenBuffer screenBuffer;
    private final Cursor cursor;
    private final T5250Panel panel;

    public KeyStroker(ScreenBuffer screenBuffer, Cursor cursor, T5250Panel panel) {
        this.panel = panel;
        this.screenBuffer = screenBuffer;
        this.cursor = cursor;

        setupKeyBindings();
    }

    private void setupKeyBindings() {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        // Up arrow key
        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursor.moveUp();
            }
        });

        // Down arrow key
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursor.moveDown();
            }
        });

        // Left arrow key
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursor.moveLeft();
            }
        });

        // Right arrow key
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursor.moveRight();
            }
        });

        // Backspace key
        inputMap.put(KeyStroke.getKeyStroke("BACK_SPACE"), "handleBackspace");
        actionMap.put("handleBackspace", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = cursor.getRow();
                int col = cursor.getCol() - 1;
                if (col < 0)
                    return; // Do nothing if on the left edge
                if (!screenBuffer.isProtectedCell(row, col)) {
                    screenBuffer.writeChar(' ', row, col);
                    cursor.moveLeft();
                }
            }
        });

        // Magic thing to handle typing any character, refer to ASCII table
        for (char c = ' '; c <= '~'; c++) {
            final char ch = c;  // type safe for actionPerformed below
            String actionName = "type" + ch;
            inputMap.put(KeyStroke.getKeyStroke(ch), actionName);
            actionMap.put(actionName, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!screenBuffer.isProtectedCell(cursor.getRow(), cursor.getCol())) {
                        screenBuffer.writeChar(ch, cursor.getRow(), cursor.getCol());
                        cursor.moveRight();
                    }
                }
            });
        }
    }

}
