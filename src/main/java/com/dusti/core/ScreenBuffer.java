package com.dusti.core;

import java.awt.Color;
import java.util.logging.Logger;
import com.dusti.events.MatrixCharProperty;
import com.dusti.interfaces.BufferChangeListener;
import com.dusti.interfaces.ScreenElement;
import com.dusti.models.ScreenModel;
import com.dusti.views.themes.Theme;

public class ScreenBuffer {
    private static final Logger logger = LoggerFactory.getLogger(ScreenBuffer.class.getName());
    private static final Theme theme = new Theme();
    private MatrixCharProperty bufferProperty;
    private boolean[][] protectedMatrix;
    private Color fgColor[][];
    private Color bgColor[][];
    private final int rows;
    private final int cols;
    private String message;

    public ScreenBuffer(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.bufferProperty = new MatrixCharProperty(rows, cols);
        this.protectedMatrix = new boolean[rows][cols];
        this.fgColor = new Color[rows][cols];
        this.bgColor = new Color[rows][cols];
        initializeBuffer();
    }

    private void initializeBuffer() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                bufferProperty.setValueAt(row, col, ' ');
                protectedMatrix[row][col] = true;
                fgColor[row][col] = theme.getFieldColor();
                bgColor[row][col] = theme.getScreenBackgroundColor();
            }
        }
    }

    public void updateFromModel(ScreenModel model) {
        // If no model then do nothing
        if (model == null) {
            logger.warning("Tried to update buffer from a null model.");
            return;
        }

        // Get listeners so that we can add them to new bufferProperty
        var listeners = bufferProperty.getListeners();

        // Initialize a new matrix
        Character[][] matrix = new Character[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                matrix[row][col] = ' ';
            }
        }

        // Place elements in matrix by character
        for (ScreenElement elem : model.getElements()) {
            char[] chars = elem.getVisualRepr().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int row = elem.getRow();
                int col = elem.getCol() + i;

                if (col > matrix[row].length - 1) {
                    logger.warning(
                            String.format("\"%s\" does not fit in matrix with %d rows and %d",
                                    elem.getVisualRepr(), rows, cols));
                    break;
                }

                // Unprotect input fields
                if (chars[i] == '*') {
                    matrix[row][col] = ' ';
                    protectedMatrix[row][col] = false;
                    fgColor[row][col] = theme.getInputColor();
                } else {
                    matrix[row][col] = chars[i];
                }
            }
        }

        // Reset bufferProperty and add listeners
        bufferProperty = new MatrixCharProperty(matrix);
        for (var listener : listeners) {
            bufferProperty.addListener(listener);
        }
        bufferProperty.dispatchUpdateEvent();
    }

    public void addListener(BufferChangeListener<Character> listener) {
        bufferProperty.addListener(listener);
    }

    public void removeListener(BufferChangeListener<Character> listener) {
        bufferProperty.removeListener(listener);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        message = msg;
        setStringAt(getRows() - 1, 1, msg, theme.getInformationIndicatorsColor());
    }

    public void clearMessage() {
        for (int col = 1; col < message.length() + 1; col++) {
            bufferProperty.setValueAt(getRows() - 1, col, ' ');
        }
    }

    public void setStringAt(int row, int col, String str, Color color) {
        for (int i = 0; i < col + str.length() - 1; i++) {
            bufferProperty.setValueAt(row, col + i, str.charAt(i));
            fgColor[row][col + i] = color;
        }
    }

    public Color getFgColorAt(int row, int col) {
        return fgColor[row][col];
    }

    public Color getBgColorAt(int row, int col) {
        return bgColor[row][col];
    }

    public char getCharAt(int row, int col) {
        return bufferProperty.getValueAt(row, col);
    }

    public void setCharAt(int row, int col, char value) {
        bufferProperty.setValueAt(row, col, value);
    }

    public void removeCharAt(int row, int col) {
        setCharAt(row, col, ' ');
    }

    public void insertCharAt(int row, int col, char value) {
        shiftCharsRightAt(row, col);
        setCharAt(row, col, value);
    }

    public void shiftCharsLeftAt(int row, int col) {
        // Get index of last unprotected cell following this cell
        int indexEnd = findNextUnprotectedIndexAfter(row, col);

        // Remove char 
        removeCharAt(row, col);

        // Shift left
        for (int i = 0; i < indexEnd - col; i++) {
            var ch = getCharAt(row, col + i + 1);
            removeCharAt(row, col + i + 1);
            bufferProperty.setValueAt(row, col + i, ch);
        }
    }

    public void shiftCharsRightAt(int row, int col) {
        // Get index of last unprotected cell following this cell
        int indexEnd = findNextUnprotectedIndexAfter(row, col);

        // Shift right inside input field only
        for (int i = indexEnd; i > col; i--) {
            var ch = getCharAt(row, i - 1);
            removeCharAt(row, i - 1);
            bufferProperty.setValueAt(row, i, ch);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isProtectedCell(int row, int col) {
        return protectedMatrix[row][col];
    }

    public int[] findFirstUnprotectedCellToTheLeft(int row, int col) {
        // Start search from specified location, moving right-to-left, bottom-to-top
        boolean firstFound = false;
        int canidateCol = col;
        for (int r = row; r >= 0; r--) {
            for (int c = getCols() - 1; c >= 0; c--) {
                if (firstFound) {
                    if (isProtectedCell(r, c)) {
                        return new int[] {r, canidateCol};
                    }
                    canidateCol = c;
                } else {
                    // On the first row iteration, start at specified column
                    if (r == row && c > col) {
                        c = col;
                    }
                    if (!isProtectedCell(r, c)) {
                        canidateCol = c;
                        firstFound = true;
                    }
                }
            }
        }

        // Wrap-around serach: start from the last cell up to the specified location
        for (int r = getRows() - 1; r >= 0; r--) {
            for (int c = getCols() - 1; c >= 0; c--) {
                if (r == row && c == col) {
                    break; // Stop when back at the starting point
                }

                if (firstFound) {
                    if (isProtectedCell(r, c)) {
                        return new int[] {r, canidateCol};
                    }
                    canidateCol = c;
                } else {
                    if (!isProtectedCell(r, c)) {
                        canidateCol = c;
                        firstFound = true;
                    }
                }
            }
        }

        // No unprotected cells found
        return new int[] {row, col};
    }

    public int[] findNextUnprotectedCellToTheRight(int row, int col) {
        // Start search from specified location, moving left-to-right, top-to-bottom
        for (int r = row; r < getRows(); r++) {
            for (int c = 0; c < getCols(); c++) {
                if (r == row && c < col) {
                    c = col; // Skip to the specified column on the first row iteration
                }

                if (!isProtectedCell(r, c)) {
                    return new int[] {r, c};
                }
            }
        }

        // Wrap-around search: start from the first cell up to the specified location
        for (int r = 0; r <= row; r++) {
            for (int c = 0; c < getCols(); c++) {
                if (r == row && c == col) {
                    break; // Stop when back at the starting point
                }

                if (!isProtectedCell(r, c)) {
                    return new int[] {r, c};
                }
            }
        }

        // No unprotected cells found
        return new int[] {row, col};
    }

    public int findFirstUnprotectedIndexBefore(int row, int col) {
        int indexStart = col;
        for (int i = col; i >= 0; i--) {
            if (isProtectedCell(row, i))
                indexStart = i;
            else
                break;
        }
        return indexStart;
    }

    public int findNextUnprotectedIndexAfter(int row, int col) {
        int indexEnd = col;
        for (int i = col; i < getCols(); i++) {
            if (!isProtectedCell(row, i))
                indexEnd = i;
            else
                break;
        }
        return indexEnd;
    }

}
