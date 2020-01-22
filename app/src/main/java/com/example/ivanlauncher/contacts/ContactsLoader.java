package com.example.ivanlauncher.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.ivanlauncher.ui.elements.Contact;

import java.util.ArrayList;

public class ContactsLoader {


    public static  ArrayList<Contact> cachedContactList = new ArrayList<>();

    public static  ArrayList<Contact> realodAllcontacts(Context c) {

        ArrayList<Contact> contactList = new ArrayList<>();
        ContentResolver cr = c.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur == null || cur.getCount() == 0) {
            if (cur != null) {
                cur.close();
            }
        }
        while (cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                if (pCur != null && pCur.moveToNext()) {
                    String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactList.add(new Contact(name, phoneNo));
                    pCur.close();
                }
            }
        }
        cur.close();

        cachedContactList = contactList;

        return contactList;
    }


    public static String getContact(String number) {
        if (number.length() > 8) {
            number = number.substring(number.length() - 8);
        }

        for (Contact contact : cachedContactList) {
            if (contact.getPhoneNumber().replaceAll(" ","").endsWith(number)) {
                return contact.getName();
            }
        }

        return number;
    }


}
