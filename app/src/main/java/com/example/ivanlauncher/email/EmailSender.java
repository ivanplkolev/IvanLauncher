package com.example.ivanlauncher.email;

import android.os.AsyncTask;

import java.io.File;

public class EmailSender extends AsyncTask<File, Void, Void> {

    private Exception exception;

    protected Void doInBackground(File... urls) {
        try {
        Mail m = new Mail("plamenivkolev@gmail.com", "plamen57");
        m.setBody("this is 1st test");
        m.setFrom("plamenivkolev@gmail.com");
        m.setSubject("Test 1");
        m.addAttachment(urls[0]);
        m.setTo(new String[]{"ivan.pl.kolev@gmail.com"});

            m.send();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


    protected void onPostExecute() {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
