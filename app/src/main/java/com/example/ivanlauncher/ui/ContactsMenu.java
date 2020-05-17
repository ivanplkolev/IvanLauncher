package com.example.ivanlauncher.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.ivanlauncher.MainActivityObserver;
import com.example.ivanlauncher.R;
import com.example.ivanlauncher.common.TextReader;
import com.example.ivanlauncher.loaders.ContactsLoader;
import com.example.ivanlauncher.ui.elements.Contact;

import java.util.List;

public class ContactsMenu implements MenuInterface {


    private boolean confirmed = false;

    private List<Contact> contacts;


    private UserInterfaceEngine parent;

    private Context context;

    //    TextReader reader;
    private TextView tv;

    private int currentPosition = 0;

    public ContactsMenu(Context context, TextView tv, UserInterfaceEngine parent) {
//        reader = new TextReader(context);
//        contacts = ContactsProvider.getContacts(context);
        this.tv = tv;
        this.context = context;
        this.parent = parent;

    }


    @Override
    public void resetUI() {
        confirmed = false;
        currentPosition = 0;
        contacts = ContactsLoader.realodAllcontacts(context);

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
        if (confirmed) {
            confirmed = false;
        } else {
            parent.goToMainMenu();
        }
    }

    @Override
    public void enter() {
        Contact selectedContact = contacts.get(currentPosition);

        if(!confirmed){
            TextReader.read(context.getString(R.string.dailing) + " " + selectedContact.getName() +" "+context.getString(R.string.pleaseConfirm) );
            confirmed = true;

            return;
        }

        TextReader.read(context.getString(R.string.dailing) + " " + selectedContact.getName());

        try {
            //to finish reading the
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + selectedContact.getPhoneNumber()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivityObserver.allowRedirect();
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
