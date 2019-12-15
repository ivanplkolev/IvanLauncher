package com.example.ivanlauncher.ui;

public class Contact extends MenuElement {

    public Contact(String name, MenuElement parent) {
        super(name, parent);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    String phoneNumber = "+359894391434";

}
