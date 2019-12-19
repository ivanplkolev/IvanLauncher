package com.example.ivanlauncher.contacts;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ivanlauncher.ui.elements.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsProvider {

    private static List<Contact> contacts = new ArrayList<>();
    private static long lastRefreshed = -1;

    static final private long REFRESH_INTERVAL = 5 * 60 * 1000;

    static ContactsRefreshed thread = new ContactsRefreshed();

    public static List<Contact> getContacts(Context context) {
        if (System.currentTimeMillis() - lastRefreshed > REFRESH_INTERVAL
                && thread.getStatus() != AsyncTask.Status.RUNNING) {
            thread = new ContactsRefreshed();
            thread.execute(new Object[]{context, ContactsProvider.contacts});
        }

        return contacts;
    }

    private static class ContactsRefreshed extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... objects) {

            ContactsLoader.realodAllcontacts((Context) objects[0], (List<Contact>) objects[1]);
            ContactsProvider.lastRefreshed = System.currentTimeMillis();
            return null;
        }

    }

}
