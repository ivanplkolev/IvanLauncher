package com.example.ivanlauncher.ui;

import android.content.Context;
import android.widget.TextView;

import com.example.ivanlauncher.ui.elements.ContactGroup;
import com.example.ivanlauncher.ui.elements.RegisterMenuElementType;

public class UserInterfaceEngine implements MenuInterface {


    public UserInterfaceEngine(Context context, TextView tv) {
        mainMenu = new MainMenu(context, tv, this);
        contacts = new ContactsMenu(context, tv, this);
        register = new RegisterMenu(context, tv, this);
        registerSubMenu = new RegisterSubMenu(context, tv, this);
        allApps = new AllAppsMenu(context, tv, this);
        allSmses = new SmsMenu(context, tv, this);
        contactGrooups = new ContactGroupsMenu(context, tv, this);
        contactGrooup = new ContactGroupMenu(context, tv, this);
        active = mainMenu;
    }

    MenuInterface active;
    MenuInterface contacts;
    MenuInterface mainMenu;
    MenuInterface register;
    RegisterSubMenu registerSubMenu;
    MenuInterface allApps;
    MenuInterface allSmses;
    MenuInterface contactGrooups;
    ContactGroupMenu contactGrooup;


    @Override
    public void resetUI() {
        active.resetUI();
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

//    @Override
//    public void destroy() {
//        active.destroy();
//    }

    void goToContacts() {
        active = contacts;
        resetUI();
    }

    void goToRegister() {
        active = register;
        resetUI();
    }

    void goToRegisterSubMenu(RegisterMenuElementType type) {
        active = registerSubMenu;
        registerSubMenu.type = type;
        resetUI();
    }

    void goToMainMenu() {
        active = mainMenu;
        resetUI();
    }

    void goToAllSMSes() {
        active = allSmses;
        resetUI();
    }

    void goToAllApps() {
        active = allApps;
        resetUI();
    }

    void goToContactGroups() {
        active = contactGrooups;
        resetUI();
    }

    void goToContactGroup(ContactGroup group) {
        active = contactGrooup;
        contactGrooup.group = group;
        resetUI();
    }




}
