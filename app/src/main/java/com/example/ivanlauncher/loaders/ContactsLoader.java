package com.example.ivanlauncher.loaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.ivanlauncher.ui.elements.Contact;
import com.example.ivanlauncher.ui.elements.ContactGroup;

import java.util.ArrayList;
import java.util.Collections;

public class ContactsLoader {


    public static ArrayList<Contact> cachedContactList = new ArrayList<>();

    public static ArrayList<Contact> realodAllcontacts(Context c) {

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

        Collections.sort(contactList);

        return contactList;
    }

    public static ArrayList<Contact> realodAllcontacts(Context c, ContactGroup group) {

        ArrayList<Contact> contactList = new ArrayList<>();
        long groupId = Long.valueOf(group.getId());
        String[] cProjection = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID};

        Cursor groupCursor = c.getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                cProjection,
                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID + "= ?" + " AND "
                        + ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE + "='"
                        + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE + "'",
                new String[]{String.valueOf(groupId)}, null);
        if (groupCursor != null && groupCursor.moveToFirst()) {
            do {

                int nameCoumnIndex = groupCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                String name = groupCursor.getString(nameCoumnIndex);

                long contactId = groupCursor.getLong(groupCursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID));

                Cursor numberCursor = c.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);

                if (numberCursor.moveToFirst()) {
                    int numberColumnIndex = numberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String phoneNo = numberCursor.getString(numberColumnIndex);
                    Contact contact = new Contact(name, phoneNo);
                    contactList.add(contact);
                    numberCursor.close();
                }
            } while (groupCursor.moveToNext());
            groupCursor.close();
        }

        return contactList;
    }


    public static String getContact(String incomeNumber) {
        if (incomeNumber.length() > 8) {

            String number = incomeNumber.replaceAll("\\s", "");

            number = number.substring(number.length() - 8);


            for (Contact contact : cachedContactList) {
                if (contact.getPhoneNumber().replaceAll("\\s", "").endsWith(number)) {
                    return contact.getName();
                }
            }
        }
        return incomeNumber;
    }


}
