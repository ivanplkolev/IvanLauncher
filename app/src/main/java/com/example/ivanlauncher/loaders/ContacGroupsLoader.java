package com.example.ivanlauncher.loaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.ivanlauncher.ui.elements.ContactGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ContacGroupsLoader {

    public static List<ContactGroup> loadAllContactGroups(Context activity) {

        ArrayList<ContactGroup> groupsList = new ArrayList<>();

        ContentResolver cr = activity.getContentResolver();

        Cursor cur = activity.getContentResolver().query(
                ContactsContract.Groups.CONTENT_URI,
                new String[]{
                        ContactsContract.Groups._ID,
                        ContactsContract.Groups.TITLE
                }, null, null, null
        );

        //groups with at least 1 contact inside
//        ContentResolver cr = getContentResolver();
//        Uri groupsUri = ContactsContract.Groups.CONTENT_SUMMARY_URI;
//        String where = "((" + ContactsContract.Groups.GROUP_VISIBLE + " = 1) AND ("
//                + ContactsContract.Groups.SUMMARY_WITH_PHONES + "!= 0))";
//        Cursor cursor = cr.query(groupsUri, null, where, null, null);

        if (cur == null || cur.getCount() == 0) {
            if (cur != null) {
                cur.close();
            }
        }

        while (cur.moveToNext()) {
            String id = cur.getString(0);// for  number //todo check with group id name
            String name = cur.getString(1);// for name
            ContactGroup group = new ContactGroup(id, name);

            groupsList.add(group);
        }

        cur.close();


        //todo filtering could be removed - or with preference
        groupsList.removeIf(a-> !Pattern.matches(".*\\p{InCyrillic}.*", a.getName()));


        return groupsList;
    }

}
