package com.dusti.models;

import com.dusti.logic.CellListener;

public class Field implements CellListener{
    private String label;
    private String name;
    private final int inputLength;
    private final int totalLength;
    private final Position position;
    private final Position inputPosition;
    private String inputData;

    public Field(String name, String label, int totalLength, int inputLength, Position position) {
        this.name = name;
        this.label = label;
        this.totalLength = totalLength;
        this.inputLength = inputLength;
        this.position = position;
        this.inputPosition = new Position(position.getRow(), position.getCol() + totalLength - inputLength);
        this.inputData = " ";
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public int getInputLength() {
        return inputLength;
    }


    public int length() {
        return totalLength;
    }

    public Position getInputPosition() {
        return inputPosition;
    }

    public Position getPosition() {
        return position;
    }

    public String getInputData() {
        return inputData;
    }

    public char getCharAt(int i) {
        return i < inputData.length() ? inputData.charAt(i) : ' ';
    }

    @Override
    public void onCellChanged(Cell cell) {
        StringBuilder sb = new StringBuilder(inputData);
        // If cell col is greater than input data then everything before it is unchanged, thus " "
        if (cell.gePosition().getCol() > inputPosition.getCol() + sb.length()) {
            sb.append(" ".repeat(cell.gePosition().getCol() - inputPosition.getCol() + sb.length()));
            sb.append(cell.getValue());
        }
        // If cell col is the same as input data then append
        else if (cell.gePosition().getCol() == inputPosition.getCol() + sb.length()) {
            sb.append(cell.getValue());
        }
        // If cell col is less than input data then replace char at that location
        else if (cell.gePosition().getCol() < inputPosition.getCol() + sb.length()) {
            sb.setCharAt(inputPosition.getCol() + sb.length() - cell.gePosition().getCol(), cell.getValue());
        }
    }
    
}
