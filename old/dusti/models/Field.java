package com.dusti.models;

import com.dusti.logic.CellListener;

public class Field implements CellListener{
    private String label;
    private String name;
    private final int inputLength;
    private final int totalLength;
    private final Position position;
    private String inputData;

    public Field(String name, String label, int totalLength, int inputLength, Position position) {
        this.name = name;
        this.label = label;
        this.totalLength = totalLength;
        this.inputLength = inputLength;
        this.position = position;
        this.inputData = " ".repeat(inputLength);
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
        return new Position(position.getRow(), position.getCol() + totalLength - inputLength);
    }

    public Position getStartPosition() {
        return position;
    }

    public Position getEndPosition() {
        return new Position(position.getRow(), position.getCol() + totalLength);
    }

    public String getInputData() {
        return inputData;
    }

    public char getInputCharAt(int i) {
        return i < inputData.length() - 1 ? inputData.charAt(i) : ' ';
    }

    public char getCharAt(int i) {
        // Alternate " " and "."
        var spacer = "";
        var spaceBetween = totalLength - inputLength - label.length();
        for (int j = 0; j < spaceBetween; j++) {
            if (j % 2 == 0) spacer += " ";
            if (j % 2 == 1) spacer += ".";
        }
        var displayed = label + spacer + inputData;
        System.out.println("displayed: -->'"+displayed+"'<-- totalLength="+totalLength);
        return i < totalLength - 1 ? displayed.charAt(i) : ' ';
    }

    @Override
    public void onCellChanged(Cell cell) {
        int relativeIndex = cell.getPosition().getCol() - getInputPosition().getCol();
        StringBuilder sb = new StringBuilder(inputData);
        while (sb.length() <= relativeIndex) {
            sb.append(" ");
        }

        sb.setCharAt(relativeIndex, cell.getValue());
        inputData = sb.toString();
        
    }
    
}
