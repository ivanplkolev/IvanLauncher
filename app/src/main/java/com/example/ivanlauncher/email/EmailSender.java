package com.example.ivanlauncher.email;

import android.os.AsyncTask;

import com.example.ivanlauncher.preferences.PreferencesLoader;

import java.io.File;

public class EmailSender extends AsyncTask<File, Void, Void> {

    private Exception exception;

    protected Void doInBackground(File... urls) {

        String emailUser = PreferencesLoader.getEmail();
        String emailPass = PreferencesLoader.getEmailPass();
        String[] recepients = PreferencesLoader.getEmailRecepients();

        try {
            Mail m = new Mail(emailUser, emailPass);
            m.setBody("I am sending image");
            m.setFrom(emailUser);
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
