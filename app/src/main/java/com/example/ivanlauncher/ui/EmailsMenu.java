package com.example.ivanlauncher.ui;

import android.content.Context;
import android.widget.TextView;

import com.example.ivanlauncher.common.TextReader;
import com.example.ivanlauncher.email.EmailReader;
import com.example.ivanlauncher.ui.elements.Email;

import java.util.List;

public class EmailsMenu implements MenuInterface {

    private List<Email> emails;

    private UserInterfaceEngine parent;

    private Context context;

    private TextView tv;

    private int currentPosition = 0;

    public EmailsMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        this.tv = tv;
        this.context = context;
        this.parent = parent;
    }


    @Override
    public void resetUI() {
        currentPosition = 0;
        emails = EmailReader.loadEmails();

        if (emails.size() == 0) {//todo first notify for the back
            back();
            return;
        }

        notifyForChanges();
    }

    @Override
    public void selectNext() {
        if (currentPosition < emails.size() - 1) {
            currentPosition++;
        }
        notifyForChanges();
    }

    @Override
    public void selectPrevious() {
        if (currentPosition > 0) {
            currentPosition--;
        }
        notifyForChanges();
    }

    @Override
    public void back() {
        parent.goToMainMenu();
    }

    @Override
    public void enter() {
        Email selectedEmail = emails.get(currentPosition);

        parent.gotoFullEmailReading(selectedEmail.readFull());
    }

    @Override
    public void notifyForChanges() {
        Email selectedEmail = emails.get(currentPosition);
        tv.setText(selectedEmail.readShort(context));
        tv.invalidate();
        TextReader.read(tv.getText().toString());
    }
}
