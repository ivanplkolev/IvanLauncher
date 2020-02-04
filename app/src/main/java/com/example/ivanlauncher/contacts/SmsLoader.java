package com.example.ivanlauncher.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.ivanlauncher.ui.elements.Sms;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class SmsLoader {


    public static List<Sms> loadAllReceivedSms(Context activity) {

        ArrayList<Sms> messages = new ArrayList<>();

        ContentResolver cr = activity.getContentResolver();

        Cursor cur = activity.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cur == null || cur.getCount() == 0) {
            if (cur != null) {
                cur.close();
            }
        }

        if (cur != null && cur.moveToFirst()) {
            do {


                String read = cur.getString(cur.getColumnIndex("read"));
                String status = cur.getString(cur.getColumnIndex("read"));
                String seen = cur.getString(cur.getColumnIndex("seen"));
                String person = cur.getString(cur.getColumnIndex("person"));
                String creator = cur.getString(cur.getColumnIndex("creator"));
                String address = cur.getString(cur.getColumnIndex("address"));
                String body = cur.getString(cur.getColumnIndex("body"));
                long date = cur.getLong(cur.getColumnIndex("date"));
                long dateSent = cur.getLong(cur.getColumnIndex("date_sent"));

                LocalDateTime dateTime = Instant.ofEpochMilli(date != 0 ? date : dateSent).atZone(ZoneId.systemDefault()).toLocalDateTime();

                Sms sms = new Sms(false, dateTime, address, body);

                messages.add(sms);


            } while (cur.moveToNext());

        }


        cur.close();

        return messages;
    }

}
