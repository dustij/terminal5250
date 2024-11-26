package com.dusti.models;

import java.util.List;

public class ScreenModel {
    private String name;
    private String title;
    private List<String> elements;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<String> getElements() {
        return elements;
    }
    public void setElements(List<String> elements) {
        this.elements = elements;
    }
    
    
}
