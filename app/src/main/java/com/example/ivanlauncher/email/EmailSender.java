package com.example.ivanlauncher.email;

import android.os.AsyncTask;

import com.example.ivanlauncher.preferences.PreferencesActivity;

import java.io.File;

public class EmailSender extends AsyncTask<File, Void, Void> {

    private Exception exception;

    protected Void doInBackground(File... urls) {

        String email = PreferencesActivity.getEmail();
        String emailPass = PreferencesActivity.getEmailPass();
        String[] recepients = PreferencesActivity.getEmailRecepients();

        try {
            Mail m = new Mail(email, emailPass);
            m.setBody("I am sending image");
            m.setFrom(email);
            m.setSubject("Please check the pic");
            m.addAttachment(urls[0]);
            m.setTo(recepients);

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
