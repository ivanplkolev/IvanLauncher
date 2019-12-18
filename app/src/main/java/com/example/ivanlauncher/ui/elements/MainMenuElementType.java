package com.example.ivanlauncher.ui.elements;

import com.example.ivanlauncher.R;

public enum MainMenuElementType {
    STATUS(R.string.status),
    EMAIL_SENDER(R.string.sendMail),
    CONTACTS(R.string.allContacts),
    SETTINGS(R.string.settings);

    private int nameId;

    MainMenuElementType(int nameId) {
        this.nameId = nameId;
    }

    public int getNameId() {
        return nameId;
    }
}
