package com.example.ivanlauncher.ui.elements;

import android.content.Context;
import android.provider.CallLog;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.loaders.ContactsLoader;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CallLogDetail {

    static enum Type {
        MISSED, RECEIVED, DAILED
    }

    Type type;

    private String phoneNumber;
    private String name;
    int duration;


    private LocalDateTime dateTime;


    public CallLogDetail(String phoneNumber, String name, int duration, long dateEpoch, int type) {
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                this.type = Type.RECEIVED;
            case CallLog.Calls.MISSED_TYPE:
                this.type = Type.MISSED;
            case CallLog.Calls.OUTGOING_TYPE:
                this.type = Type.DAILED;
        }

        this.phoneNumber = phoneNumber;
        this.name = name;
        this.duration = duration;
        this.dateTime = Instant.ofEpochMilli(dateEpoch).atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (name == null || name.length() == 0) {
            this.name = ContactsLoader.getContact(phoneNumber);
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }


    public int getDuration() {
        return duration;
    }

    public LocalDateTime getTime() {
        return dateTime;
    }


    public String getInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append(" ");
        sb.append(getTime().format(DateTimeFormatter.ofPattern("HH:mm EEEE dd MMMM yyyy", Locale.getDefault())));
        sb.append(" ");
        sb.append(context.getString(R.string.duration));
        sb.append(" ");
        sb.append(duration);

        return sb.toString();
    }


}
