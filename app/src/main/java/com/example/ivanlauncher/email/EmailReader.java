package com.example.ivanlauncher.email;

import com.example.ivanlauncher.preferences.PreferencesLoader;
import com.example.ivanlauncher.ui.elements.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

public class EmailReader {


    public static List<Email>  loadEmails() {

        String emailUser = PreferencesLoader.getEmail();
        String emailPass = PreferencesLoader.getEmailPass();

        List<Email> emails = new ArrayList<>();


        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", emailUser, emailPass);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            javax.mail.Message msg = inbox.getMessage(inbox.getMessageCount());// returns the last message
            javax.mail.Address[] in = msg.getFrom();
            for (javax.mail.Address address : in) {
                System.out.println("FROM:" + address.toString());
            }
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            System.out.println("SENT DATE:" + msg.getSentDate());
            System.out.println("SUBJECT:" + msg.getSubject());
            System.out.println("CONTENT:" + bp.getContent());

            Email email = new Email(true,
                    msg.getSentDate(),
                    msg.getFrom()[0].toString(),
                    msg.getSubject(),
                    bp.getContent().toString());

            emails.add(email);

        } catch (Exception mex) {
            mex.printStackTrace();
        }
        return emails;
    }


}
