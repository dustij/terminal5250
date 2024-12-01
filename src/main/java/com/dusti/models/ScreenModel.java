package com.dusti.models;

import java.util.List;

/**
 * This represents an object in the screen resource file (JSON, XML, PlainText, etc.)
 */
public class ScreenModel {
    private String name;
    private String title;
    private List<ScreenElementModel> elements;
    

    public ScreenModel(String name, String title, List<ScreenElementModel> elements) {
        this.name = name;
        this.title = title;
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public List<ScreenElementModel> getElements() {
        return elements;
    }
    
    
}
