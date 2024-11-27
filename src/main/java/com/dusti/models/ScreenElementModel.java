package com.dusti.models;

import com.dusti.interfaces.ScreenElement;

/**
 * This represents an attribute of the object in the screen resource file (JSON, XML, PlainText).
 * 
 */
public class ScreenElementModel implements ScreenElement{
    private final String visualRepr;
    private final String id;
    private final int row;
    private final int col;

    public ScreenElementModel(String visualRepr, String id, int row, int col) {
        this.visualRepr = visualRepr;
        this.id = id;
        this.row = row;
        this.col = col;
    }

    @Override
    public String getVisualRepr() {
        return visualRepr;
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

    
    
}