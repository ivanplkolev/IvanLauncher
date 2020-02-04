package com.example.ivanlauncher.ui;

import android.content.Context;
import android.widget.TextView;

import com.example.ivanlauncher.contacts.SmsLoader;
import com.example.ivanlauncher.ui.elements.Sms;

import java.util.List;

public class SmsMenu implements MenuInterface {

    private List<Sms> smses;

    private UserInterfaceEngine parent;

    private Context context;

    private TextView tv;

    private int currentPosition = 0;

    public SmsMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        this.tv = tv;
        this.context = context;
        this.parent = parent;
    }


    @Override
    public void resetUI() {
        currentPosition = 0;
        smses = SmsLoader.loadAllReceivedSms(context);

        if (smses.size() == 0) {//todo first notify for the back
            back();
            return;
        }

        notifyForChanges();
    }

    @Override
    public void selectNext() {
        if (currentPosition < smses.size() - 1) {
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
        Sms selectedSMS = smses.get(currentPosition);
        TextReader.read(selectedSMS.readFull(context));
    }

    @Override
    public void notifyForChanges() {
        Sms selectedSMS = smses.get(currentPosition);
        tv.setText(selectedSMS.readShort(context));
        tv.invalidate();
        TextReader.read(tv.getText().toString());
    }
}
