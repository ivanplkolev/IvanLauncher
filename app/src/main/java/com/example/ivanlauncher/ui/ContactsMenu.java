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

import java.util.List;

public class ContactsMenu implements MenuInterface {


    private List<Contact> contacts;
    ContactsProvider contactsProvider;


    UserInterfaceEngine parent;

    Context context;

    TextReader reader;
    TextView tv;

    int currentPosition = 0;

    public ContactsMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        reader = new TextReader(context);
        contactsProvider = new ContactsProvider(context);
        this.tv = tv;
        this.context = context;
        this.parent = parent;

    }


    @Override
    public void resumeUI() {
        currentPosition = 0;
        contacts = contactsProvider.getContacts();
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

        reader.read(context.getString(R.string.dailing) + " "+ selectedContact.getName());

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + selectedContact.getPhoneNumber()));
            context.startActivity(intent);
        }
    }

    @Override
    public void notifyForChanges() {
        tv.setText(contacts.get(currentPosition).getName());
        tv.invalidate();

        reader.read(tv.getText().toString());
    }

    @Override
    public void destroy() {
        reader.shutdown();
    }
}
