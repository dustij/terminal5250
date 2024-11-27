package com.dusti.models;

public class CursorModel {
    private CursorModel instance;
    private boolean insertMode;
    private int row;
    private int col;

    private CursorModel() {
        insertMode = false;
        row = 0;
        col = 0;
    }

    public CursorModel getInsance() {
        if (instance == null) {
            instance = new CursorModel();
        }
        return instance;
    }

    public boolean isInsertMode() {
        return insertMode;
    }

    public void setInsertMode(boolean insertMode) {
        this.insertMode = insertMode;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    
}
