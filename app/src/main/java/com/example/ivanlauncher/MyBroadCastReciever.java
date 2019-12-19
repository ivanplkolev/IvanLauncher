package com.example.ivanlauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ivanlauncher.call.OngoingCall;
import com.example.ivanlauncher.ui.TextReader;

public class MyBroadCastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            if (OngoingCall.call != null) {
                OngoingCall.hangup();
            }
            TextReader.read(context.getString(R.string.displayOff));

            //Take count of the screen off position
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            if (OngoingCall.call != null) {
                OngoingCall.answer();
            }
            TextReader.read(context.getString(R.string.displayOn));
        }
    }

}
