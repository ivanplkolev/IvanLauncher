package com.example.ivanlauncher.ui.elements;

public class App {


    String name;


    String packageName;

    public App(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
    }


    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }


}
