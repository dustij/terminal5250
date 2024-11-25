package com.dusti.models;

public class Screen{
    private final String name;
    private final Field[] fields;

    public Screen(String name, Field[] fields) {
        this.name = name;
        this.fields = fields;
    }

    public Field[] getFields() {
        return fields;
    }

    public String getName() {
        return name;
    }

}
