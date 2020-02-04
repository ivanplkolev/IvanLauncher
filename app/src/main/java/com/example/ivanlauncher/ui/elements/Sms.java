package com.example.ivanlauncher.ui.elements;

import android.content.Context;

import com.example.ivanlauncher.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Sms {


    private boolean read;
    private LocalDateTime received;
    private String sender;
    private String content;

    public Sms(boolean read, LocalDateTime received, String sender, String content) {
        this.read = read;
        this.received = received;
        this.sender = sender;
        this.content = content;
    }


    public boolean isRead() {
        return read;
    }

    public LocalDateTime getReceived() {
        return received;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }


    public String readShort(Context c) {
        StringBuilder sb = new StringBuilder();
        sb.append(c.getString(read ? R.string.read : R.string.notRead));
        sb.append(" ");
        sb.append(c.getString( R.string.from));
        sb.append(" ");
        sb.append(getSender());
        sb.append(" ");
        sb.append(c.getString( R.string.receivedOn));
        sb.append(" ");
        sb.append(getReceived().format(DateTimeFormatter.ofPattern("HH:mm EEEE dd MMMM yyyy", Locale.getDefault())));
        return sb.toString();
    }

    public String readFull(Context c) {
        return getContent();
    }


}
