package com.example.ivanlauncher.ui;

import android.content.Context;
import android.widget.TextView;

public class UserInterfaceEngine implements MenuInterface {


    public UserInterfaceEngine(Context context, TextView tv) {
        mainMenu = new MainMenu(context, tv, this);
        contacts = new ContactsMenu(context, tv, this);
        active = mainMenu;
    }

    MenuInterface active;
    MenuInterface contacts;
    MenuInterface mainMenu;


    @Override
    public void resumeUI() {
        active.resumeUI();
    }

    @Override
    public void selectNext() {
        active.selectNext();
    }

    @Override
    public void selectPrevious() {
        active.selectPrevious();
    }

    @Override
    public void back() {
        active.back();
    }

    @Override
    public void enter() {
        active.enter();
    }

    @Override
    public void notifyForChanges() {
        active.notifyForChanges();
    }

    @Override
    public void destroy() {
        active.destroy();
    }

    void goToContacts() {
        active = contacts;
        resumeUI();
    }

    void goToMainMenu() {
        active = mainMenu;
        resumeUI();
    }


}
