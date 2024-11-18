package com.dusti.t5250;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    @Test
    void testGetDisplay() {
        Field testField = new Field("Label", 15, 5);
        assertEquals(testField.getDisplay(), "Label.....");
    }
    
    @Test
    void testInputStartPoint() {
        Field testField = new Field("Label", 15, 5);
        assertEquals(testField.inputStartPoint(), 10);
    }

    @Test
    void testGetInputData() {
        Field testField = new Field("Label", 15, 5);
        testField.setInputData("data");
        assertEquals(testField.getInputData(), "data");
        assertEquals(testField.getDisplay(), "Label.....data");
        testField.setInputData("data0data0data0");
        assertEquals(testField.getInputData(), "data0");
        assertEquals(testField.getDisplay(), "Label.....data0");
    }

    @Test
    void testGetCharAt() {
        Field testField = new Field("Label", 15, 5);
        assertEquals(testField.getCharAt(0), 'L');
        assertEquals(testField.getCharAt(5), '.');
        assertEquals(testField.getCharAt(14), ' ');
    }
}