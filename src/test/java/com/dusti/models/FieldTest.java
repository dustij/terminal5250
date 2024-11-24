package com.dusti.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    @Test
    public void testGetInputPosition() {
        // Setup
        Position fieldPosition = new Position(0, 0);
        Field field = new Field("TestField", "Test Field", 20, 10, fieldPosition);

        // Assert
        assertEquals(field.getInputPosition().getRow(), 0);
        assertEquals(field.getInputPosition().getCol(), 10);
    }

    @Test
    public void testOnCellChanged_AddCharacter() {
        // Setup
        Position fieldPosition = new Position(0, 0);
        Field field = new Field("TestField", "Test Field", 20, 10, fieldPosition);

        Position cellPosition = new Position(0, 10);
        Cell cell = new Cell(' ', cellPosition);
        cell.addListener(field);

        // Act
        cell.changeValue('B');

        // Assert
        assertEquals("B", field.getInputData());
    }

    @Test
    public void testOnCellChanged_ReplaceCharacter() {
        // Setup
        Position fieldPosition = new Position(0, 0);
        Field field = new Field("TestField", "Test Field", 20, 10, fieldPosition);
        field.onCellChanged(new Cell('A', new Position(0, 10))); // Simulate initial input

        Position cellPosition = new Position(0, 10);
        Cell cell = new Cell(' ', cellPosition);
        cell.addListener(field);

        // Act
        cell.changeValue('C');

        // Assert
        assertEquals("C", field.getInputData());
    }

    @Test
    public void testOnCellChanged_FillGapsWithSpaces() {
        // Setup
        Position fieldPosition = new Position(0, 0);
        Field field = new Field("TestField", "Test Field", 20, 10, fieldPosition);

        Position cellPosition = new Position(0, 12);
        Cell cell = new Cell(' ', cellPosition);
        cell.addListener(field);

        // Act
        cell.changeValue('B');

        // Assert
        assertEquals("  B", field.getInputData());
    }

    @Test
    public void testOnCellChanged_AppendCharacter() {
        // Setup
        Position fieldPosition = new Position(0, 0);
        Field field = new Field("TestField", "Test Field", 20, 10, fieldPosition);
        field.onCellChanged(new Cell('A', new Position(0, 10))); // Simulate initial input

        Position cellPosition = new Position(0, 11);
        Cell cell = new Cell(' ', cellPosition);
        cell.addListener(field);

        // Act
        cell.changeValue('B');

        // Assert
        assertEquals("AB", field.getInputData());
    }
}
