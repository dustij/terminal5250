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
        int relativeIndex = cell.gePosition().getCol() - inputPosition.getCol();
        StringBuilder sb = new StringBuilder(inputData);
        while (sb.length() <= relativeIndex) {
            sb.append(" ");
        }

        sb.setCharAt(relativeIndex, cell.getValue());
        inputData = sb.toString();
        
    }
    
}
