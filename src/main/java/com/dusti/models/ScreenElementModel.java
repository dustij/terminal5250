package com.dusti.models;

import com.dusti.interfaces.ScreenElement;

/**
 * This represents an attribute of the object in the screen resource file (JSON, XML, PlainText).
 * 
 */
public class ScreenElementModel implements ScreenElement{
    private String visualRepr;
    private final String id;
    private final int row;
    private final int col;
    private final String type;

    public ScreenElementModel(String visualRepr, String id, int row, int col, String type) {
        this.visualRepr = visualRepr;
        this.id = id;
        this.row = row;
        this.col = col;
        this.type = type;
    }

    @Override
    public String getVisualRepr() {
        return visualRepr;
    }

    @Override
    public void setVisualRepr(String str) {
        visualRepr = str;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public String getType() {
        return type;
    }    
    
}
