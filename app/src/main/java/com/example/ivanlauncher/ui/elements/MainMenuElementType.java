package com.example.ivanlauncher.ui.elements;

import com.example.ivanlauncher.R;

public enum MainMenuElementType {
    STATUS(R.string.status),
    EMAIL_SENDER(R.string.sendMail),
    IMAGE_READER(R.string.readImage),
    CONTACTS(R.string.allContacts),
    REGISTER(R.string.callRegister),
    SETTINGS(R.string.settings),
    ALL_APPS(R.string.allApps),
    SMS(R.string.sms),
    CONTACT_GROUPS(R.string.contactGroups);

    private int nameId;

    MainMenuElementType(int nameId) {
        this.nameId = nameId;
    }

    public int getNameId() {
        return nameId;
    }
}
