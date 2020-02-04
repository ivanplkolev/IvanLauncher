package com.example.ivanlauncher.contacts;

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
