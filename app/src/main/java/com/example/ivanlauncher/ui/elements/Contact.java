package com.example.ivanlauncher.ui.elements;

public class Contact implements Comparable<Contact> {


    private String name;
    private String phoneNumber;


    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Contact o) {
        return name.compareTo(o.name);
    }

}
