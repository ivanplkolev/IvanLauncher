package com.example.ivanlauncher.ui.elements;

import com.example.ivanlauncher.R;

public enum RegisterMenuElementType {
    RECEIVED(R.string.receivedCalls),
    DAILED(R.string.dailedCalls),
    MISSED(R.string.missedCalls);

    private int nameId;

    RegisterMenuElementType(int nameId) {
        this.nameId = nameId;
    }

    public int getNameId() {
        return nameId;
    }
}
