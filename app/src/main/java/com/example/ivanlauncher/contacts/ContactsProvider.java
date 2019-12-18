package com.example.ivanlauncher.contacts;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ivanlauncher.ui.elements.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsProvider {

    private List<Contact> contacts = new ArrayList<>();
    private long lastRefreshed = -1;
    private final Context context;

    static final private long REFRESH_INTERVAL = 5 * 60 * 1000;

    ContactsRefreshed thread = new ContactsRefreshed();

    public ContactsProvider(Context context) {
        this.context = context;
    }


    public List<Contact> getContacts() {
        if (System.currentTimeMillis() - lastRefreshed > REFRESH_INTERVAL && thread.getStatus() != AsyncTask.Status.RUNNING) {
            thread.execute(contacts);
        }

        return contacts;
    }

    class ContactsRefreshed extends AsyncTask<List<Contact>, Void, Void> {

        @Override
        protected Void doInBackground(List<Contact>... lists) {
            ContactsLoader.realodAllcontacts(context, lists[0]);
            lastRefreshed = System.currentTimeMillis();
            return null;
        }
    }

}
