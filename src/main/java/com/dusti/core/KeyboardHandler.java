package com.dusti.core;

import java.util.function.Function;
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

        // Enter key
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "handleEnter");
        actionMap.put("handleEnter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: handle submit data, or tab if n/a
            }
        });

        // Tab key
        // Moves cursor to the start of the next input field in row-major order
        inputMap.put(KeyStroke.getKeyStroke("TAB"), "handleTab");
        actionMap.put("handleTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Current cursor position
                int currRow = cursorController.getRow();
                int currCol = cursorController.getCol();
                int[] targetCell = new int[] {currRow, currCol};

                // Move cursor based on current cell's protection state
                if (screenBuffer.isProtectedCell(currRow, currCol)) {
                    // Move to the first unprotected cell to the right
                    targetCell = screenBuffer.findNextUnprotectedCellToTheRight(currRow, currCol);
                } else {
                    // Find the next protected cell, then move to the following unprotected cell
                    int nextProtectedCol = screenBuffer.findNextUnprotectedIndexAfter(currRow, currCol) + 1;
                    targetCell = screenBuffer.findNextUnprotectedCellToTheRight(currRow, nextProtectedCol);
                }

                // Update cursor position
                cursorController.moveTo(targetCell[0], targetCell[1]);
            }
        });

        // Shift+Tab key
        // Moves cursor to the start of the previous input field in reverse row-major order
        inputMap.put(KeyStroke.getKeyStroke("shift TAB"), "handleShiftTab");
        actionMap.put("handleShiftTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Current cursor position
                int currRow = cursorController.getRow();
                int currCol = cursorController.getCol();
                int[] targetCell = new int[] {currRow, currCol};

                // Move cursor based on cell's protection state
                if (screenBuffer.isProtectedCell(currRow, currCol)) {
                    // Move to the the first unprotected cell to the left
                    // This is not necessarily the first cell seen, it is actually
                    // the first cell in the next input section to the left
                    targetCell = screenBuffer.findFirstUnprotectedCellToTheLeft(currRow, currCol);
                } else {
                    // Find the next protected cell, then move to the previous unprotected cell,
                    // again like above, its the first cell in the input section
                    int nextProtectedCol = screenBuffer.findFirstUnprotectedIndexBefore(currRow, currCol) - 1;
                    targetCell = screenBuffer.findFirstUnprotectedCellToTheLeft(currRow, nextProtectedCol);
                }

                // Update cursor position
                cursorController.moveTo(targetCell[0], targetCell[1]);
            }
        });

        // Backspace key
        inputMap.put(KeyStroke.getKeyStroke("BACK_SPACE"), "handleBackspace");
        actionMap.put("handleBackspace", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = cursorController.getRow();
                int col = cursorController.getCol() - 1;

                preprocess(row, col, () -> {
                    if (col < 0)
                        return;

                    // Remove char from buffer
                    screenBuffer.removeCharAt(row, col);

                    // Move cursor left
                    cursorController.moveLeft();
                });
            }
        });

        // Delete key
        inputMap.put(KeyStroke.getKeyStroke("DELETE"), "handleDelete");
        actionMap.put("handleDelete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = cursorController.getRow();
                int col = cursorController.getCol();

                preprocess(row, col, () -> {
                    // Shift chars left
                    screenBuffer.shiftCharsLeftAt(row, col);
                });
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

                    preprocess(row, col, () -> {
                        if (cursorController.isInsertMode()) {
                            // Insert char in buffer and shift right
                            screenBuffer.insertCharAt(row, col, ch);
                        } else {
                            // Replace char in buffer
                            screenBuffer.setCharAt(row, col, ch);

                            // Move cursor right
                            cursorController.moveRight();
                        }
                    });
                }
            });
        }
    }

    private void preprocess(int row, int col, Runnable func) {
        // Allow typing, backspacing, deleting, and inserting text in input fields only
        if (screenBuffer.isProtectedCell(row, col))
            return;
        func.run();
    }
}
