package com.dusti.logic;

import com.dusti.models.Cell;

@FunctionalInterface
public interface CellListener {
    void onCellChanged(Cell cell);
}
