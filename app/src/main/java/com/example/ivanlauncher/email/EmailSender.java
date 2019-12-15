package com.example.ivanlauncher.email;

import android.os.AsyncTask;

public class EmailSender extends AsyncTask<String, Void, Void> {

    private Exception exception;

    protected Void doInBackground(String... urls) {
        Mail m = new Mail("plamenivkolev@gmail.com", "plamen57");

        m.setBody("this is 1st test");
        m.setFrom("plamenivkolev@gmail.com");
        m.setSubject("Test 1");
        m.setTo(new String[]{"ivan.pl.kolev@gmail.com"});
        try {
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
