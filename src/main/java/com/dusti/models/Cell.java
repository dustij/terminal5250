package com.dusti.models;

import java.util.ArrayList;
import java.util.List;
import com.dusti.logic.CellListener;

public class Cell {
    private char value;
    private final List<CellListener> listeners;
    private final Position position;

    public Cell(char initialValue, Position position) {
        this.value = initialValue;
        this.position = position;
        this.listeners = new ArrayList<>();
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public void changeValue(char value) {
        if (this.value != value) {
            this.value = value;
            notifyListeners();
        }
    }

    public Position getPosition() {
        return position;
    }

    public void addListener(CellListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        for (CellListener listener : listeners) {
            listener.onCellChanged(this);
        }
    }
}
