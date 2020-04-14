package com.example.ivanlauncher.ui;

import android.content.Context;
import android.widget.TextView;

import com.example.ivanlauncher.common.TextReader;
import com.example.ivanlauncher.loaders.ContacGroupsLoader;
import com.example.ivanlauncher.ui.elements.ContactGroup;

import java.util.List;

public class ContactGroupsMenu implements MenuInterface{


    private List<ContactGroup> contactGroups;

    private UserInterfaceEngine parent;

    private Context context;

    private TextView tv;

    private int currentPosition = 0;

    public ContactGroupsMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        this.tv = tv;
        this.context = context;
        this.parent = parent;
    }



    @Override
    public void resetUI() {
        currentPosition = 0;
        contactGroups = ContacGroupsLoader.loadAllContactGroups(context);

        if (contactGroups.size() == 0) {//todo first notify for the back
            back();
            return;
        }

        notifyForChanges();
    }

    @Override
    public void selectNext() {
        if (currentPosition < contactGroups.size() - 1) {
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
        ContactGroup currentElement = contactGroups.get(currentPosition);
        parent.goToContactGroup(currentElement);
    }

    @Override
    public void notifyForChanges() {
        ContactGroup currentElement = contactGroups.get(currentPosition);
        tv.setText(currentElement.getName());
        tv.invalidate();
        TextReader.read(tv.getText().toString());
    }
}
