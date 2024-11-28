package com.dusti.core;

import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import com.dusti.controllers.CursorController;

public class KeyboardHandler {
    private static final Logger logger = LoggerFactory.getLogger(KeyboardHandler.class.getName());
    private ScreenBuffer screenBuffer;
    private CursorController cursorController;
    private InputMap inputMap;
    private ActionMap actionMap;

    public KeyboardHandler(ScreenBuffer screenBuffer, CursorController cursorController) {
        this.screenBuffer = screenBuffer;
        this.cursorController = cursorController;
    }

    public void setInputMap(InputMap inputMap) {
        this.inputMap = inputMap;
    }

    public void setActionMap(ActionMap actionMap) {
        this.actionMap = actionMap;
    }

    public void initKeyBindings() {
        if (inputMap == null) {
            logger.warning("Failed to initialize key bindings. KeyboardHandler.inputMap is null.");
            return;
        }

        if (actionMap == null) {
            logger.warning("Failed to initialize key bindings. KeyboardHandler.actionMap is null.");
            return;
        }

        // Up arrow key
        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move cursor up
                cursorController.moveUp();
            }
        });

        // Down arrow key
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move cursor down
                cursorController.moveDown();
            }
        });

        // Left arrow key
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move cursor left
                cursorController.moveLeft();
            }
        });

        // Right arrow key
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move cursor right
                cursorController.moveRight();
            }
        });

        // Tab key
        inputMap.put(KeyStroke.getKeyStroke("TAB"), "handleTab");
        actionMap.put("handleTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move cursor to next field (left to right, top to bottom)
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("shift TAB"), "handleTab");
        actionMap.put("handleTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move cursor to prev field (right to left, bottom to top)
            }
        });

        // Backspace key
        inputMap.put(KeyStroke.getKeyStroke("BACK_SPACE"), "handleBackspace");
        actionMap.put("handleBackspace", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newRow = cursorController.getRow();
                int newCol = cursorController.getCol() - 1;

                if (newCol < 0) return;

                // Remove char from buffer
                screenBuffer.removeCharAt(newRow, newCol);

                // Move cursor left
                cursorController.moveLeft();
            }
        });

        // Delete key
        inputMap.put(KeyStroke.getKeyStroke("DELETE"), "handleDelete");
        actionMap.put("handleDelete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = cursorController.getRow();
                int col = cursorController.getCol();

                // Shift chars left
                screenBuffer.shiftCharsLeftAt(row, col);
            }
        });

        // Insert key
        inputMap.put(KeyStroke.getKeyStroke("INSERT"), "handleInsert");
        actionMap.put("handleInsert", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursorController.toggleInsertMode();
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
                    int row = cursorController.getRow();
                    int col = cursorController.getCol();
                    
                    if (cursorController.isInsertMode()) {
                        // Insert char in buffer and shift right
                        screenBuffer.insertCharAt(row, col, ch);
                    } else {
                        // Replace char in buffer
                        screenBuffer.setCharAt(row, col, ch);

                        // Move cursor right
                        cursorController.moveRight();
                    }
                }
            });
        }

    }
    
}
