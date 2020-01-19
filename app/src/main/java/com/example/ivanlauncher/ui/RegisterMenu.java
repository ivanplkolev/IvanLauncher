package com.example.ivanlauncher.ui;

import android.content.Context;
import android.widget.TextView;

import com.example.ivanlauncher.ui.elements.RegisterMenuElementType;

public class RegisterMenu implements MenuInterface {

    public RegisterMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        this.tv = tv;
        this.context = context;
        this.parent = parent;

        menu = new RegisterMenuElementType[4];
        menu[0] = RegisterMenuElementType.MISSED;
        menu[1] = RegisterMenuElementType.DAILED;
        menu[2] = RegisterMenuElementType.RECEIVED;
    }

    private UserInterfaceEngine parent;

    private Context context;


    private RegisterMenuElementType[] menu;

    //    TextReader reader;
    private TextView tv;

    private int currentPosition = 0;


    @Override
    public void resetUI() {
        currentPosition = 0;
        notifyForChanges();
    }

    @Override
    public void selectNext() {
        if (currentPosition < menu.length - 1) {
            currentPosition++;
        }
        notifyForChanges();
    }

    @Override
    public void selectPrevious() {
        if (currentPosition > 0) {
            currentPosition--;
        }
        notifyForChanges();
    }

    @Override
    public void back() {
        parent.goToMainMenu();
    }

    @Override
    public void enter() {
        RegisterMenuElementType currentElement = menu[currentPosition];
        parent.goToRegisterSubMenu(currentElement);
    }

    @Override
    public void notifyForChanges() {
        RegisterMenuElementType currentElement = menu[currentPosition];
        tv.setText(context.getString(currentElement.getNameId()));
        tv.invalidate();
        TextReader.read(tv.getText().toString());
    }
}
