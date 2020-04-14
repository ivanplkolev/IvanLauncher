package com.example.ivanlauncher.ui.elements;

public class BookMark {


    private int id;

    private String name;

    private String source;

    public BookMark(int id, String name, String source) {
        this.id = id;
        this.name = name;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }
}
