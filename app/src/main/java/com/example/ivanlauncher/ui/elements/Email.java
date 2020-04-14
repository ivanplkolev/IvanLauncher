package com.example.ivanlauncher.ui.elements;

import android.content.Context;

import com.example.ivanlauncher.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Email {


    public static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm EEEE dd MMMM yyyy", Locale.getDefault());

    private boolean read;
    private Date received;
    private String sender;
    private String subject;
    private String content;

    public Email(boolean read, Date received, String sender, String subject, String content) {
        this.read = read;
        this.received = received;
        this.sender = sender;
        this.subject = subject;
        this.content = content;
    }


    public boolean isRead() {
        return read;
    }

    public Date getReceived() {
        return received;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
    public String getSubject() {
        return subject;
    }


    public String readShort(Context c) {
        StringBuilder sb = new StringBuilder();
        sb.append(c.getString(read ? R.string.read : R.string.notRead));
        sb.append(" ");
        sb.append(getSubject());
        sb.append(" ");
        sb.append(c.getString( R.string.from));
        sb.append(" ");
        sb.append(getSender());
        sb.append(" ");
        sb.append(c.getString( R.string.receivedOn));
        sb.append(" ");
        sb.append(sdf.format(getReceived()));
        return sb.toString();
    }

    public String readFull() {
        return getContent();
    }


}
