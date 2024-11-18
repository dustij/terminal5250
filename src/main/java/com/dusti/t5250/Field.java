package com.dusti.t5250;

public class Field {
    private String label;
    private int inputLength;
    private int fieldLength;
    private String fieldDisplay;
    private String inputData;
    // TODO: add foreground and background color data

    public Field(String label, int fieldLength, int inputLength) {
        this.label = label;
        this.fieldLength = fieldLength;
        this.inputLength = inputLength;
        buildDisplay();
    }

    private void buildDisplay() {
        StringBuilder sb = new StringBuilder();
        
        int numOfSpaces = fieldLength - inputLength - label.length();
        String spaces = ".".repeat(numOfSpaces);

        sb.append(label);
        sb.append(spaces);
        sb.append(inputData != null ? inputData : "");
        fieldDisplay = sb.toString();
    }

    public int length() {
        return fieldLength;
    }

    public char getCharAt(int i) {
        try {
            return fieldDisplay.charAt(i);
        } catch (IndexOutOfBoundsException e) {
            return ' ';
        }
    }

    public int inputStartPoint() {
        return fieldLength - inputLength;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData.substring(0, Math.min(inputData.length(), inputLength));
        buildDisplay();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDisplay() {
        return fieldDisplay;
    }

}
