package com.dusti.events;

public class Array2DCharProperty extends Array2DProperty<Character>{
    public Array2DCharProperty(int rows, int cols) {
        super(rows, cols, () -> new Character[rows][cols]);
    }

    public Array2DCharProperty(Character[][] array) {
        super(array.length, array[0].length, () -> array);
    }
}