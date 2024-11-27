package com.dusti.events;

public class MatrixCharProperty extends MatrixProperty<Character>{
    public MatrixCharProperty(int rows, int cols) {
        super(rows, cols, () -> new Character[rows][cols]);
    }

    public MatrixCharProperty(Character[][] array) {
        super(array.length, array[0].length, () -> array);
    }
}