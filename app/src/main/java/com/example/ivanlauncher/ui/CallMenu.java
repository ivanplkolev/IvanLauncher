package com.example.ivanlauncher.ui;

import android.content.Context;
import android.telecom.Call;
import android.widget.TextView;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.call.OngoingCall;
import com.example.ivanlauncher.common.TextReader;

public class CallMenu implements MenuInterface {

    private UserInterfaceEngine parent;

    private Context context;

    private TextView tv;

    boolean speakName = false;

    private CallernameRepeater callernameRepeater;

    public final void updateUi(int state) {
        speakName = (state == Call.STATE_RINGING);
    }


    public CallMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        this.tv = tv;
        this.context = context;
        this.parent = parent;
    }


    @Override
    public void resetUI() {
//        notifyForChanges();
    }

    @Override
    public void selectNext() {
    }

    @Override
    public void selectPrevious() {
    }

    @Override
    public void back() {
        speakName = false;
        OngoingCall.hangup();


        TextReader.read(context.getString(R.string.closedConnection));


//        notifyForChanges();
    }

    @Override
    public void enter() {
        if (speakName) {
            speakName = false;
            OngoingCall.answer();
        }

//        parent.goToMainMenu();
    }

    @Override
    public void notifyForChanges() {
//        tv.setText(context.getText(R.string.screenLocked));
//        tv.invalidate();
//        TextReader.read(tv.getText().toString());
    }


    void kill_activity() {
        TextReader.read(context.getString(R.string.closedConnection));
        parent.goToMainMenu();
    }

    public void openCall(String number) {

        tv.setText(number);
        tv.invalidate();
        TextReader.read(tv.getText().toString());


        callernameRepeater = new CallernameRepeater(number);
        speakName = true;
        callernameRepeater.start();
    }


    class CallernameRepeater extends Thread {

        private String callerName;

        CallernameRepeater(String callerName) {
            this.callerName = callerName;
        }

        @Override
        public void run() {
            while (speakName) {
                TextReader.read(callerName);
                try {
                    Thread.sleep(callerName.length() * 300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
