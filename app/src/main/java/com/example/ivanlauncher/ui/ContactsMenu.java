package com.example.ivanlauncher.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.contacts.ContactsProvider;
import com.example.ivanlauncher.ui.elements.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsMenu implements MenuInterface {


    private List<Contact> contacts;


    UserInterfaceEngine parent;

    Context context;

    //    TextReader reader;
    TextView tv;

    int currentPosition = 0;

    public ContactsMenu(Context context, TextView tv, UserInterfaceEngine parent) {
//        reader = new TextReader(context);
//        contacts = ContactsProvider.getContacts(context);
        this.tv = tv;
        this.context = context;
        this.parent = parent;

    }


    @Override
    public void resetUI() {
        currentPosition = 0;
        contacts = new ArrayList<>(ContactsProvider.getContacts(context));

        if (contacts.size() == 0) {
            back();
            return;
        }

        notifyForChanges();
    }

    public void selectNext() {
        if (currentPosition < contacts.size() - 1) {
            currentPosition++;
        }
        notifyForChanges();
    }

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
        Contact selectedContact = contacts.get(currentPosition);

        TextReader.read(context.getString(R.string.dailing) + " " + selectedContact.getName());

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + selectedContact.getPhoneNumber()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    @Override
    public void notifyForChanges() {
        tv.setText(contacts.get(currentPosition).getName());
        tv.invalidate();

        TextReader.read(tv.getText().toString());
    }

//    @Override
//    public void destroy() {
//        TextReader.shutdown();
//    }
}
