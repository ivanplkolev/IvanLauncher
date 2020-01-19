package com.example.ivanlauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;

import com.example.ivanlauncher.call.OngoingCall;
import com.example.ivanlauncher.ui.TextReader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBroadCastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            if (OngoingCall.call != null) {
                OngoingCall.hangup();
            }

            readOff(context);


//            start main Activity !

            Intent lockIntent = new Intent(context, MainActivity.class);
            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

            context.startActivity(lockIntent);


            //Take count of the screen off position
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            if (OngoingCall.call != null) {
                OngoingCall.answer();
                TextReader.read(context.getString(R.string.displayOn));
            } else {
                readOn(context);
            }


        }
    }


    private void readOn(Context context){

        BatteryManager bm = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.displayOn));
        sb.append(" ");
        sb.append(context.getString(R.string.currentTime));
        sb.append(" ");
        sb.append(new SimpleDateFormat("HH:mm").format(new Date()));
        sb.append(" ");
        sb.append(context.getString(R.string.batteryIs));
        sb.append(" ");
        sb.append(batLevel);
        sb.append(" ");
        sb.append(context.getString(R.string.percent));

        if (networkInfo == null || !networkInfo.isConnected()) {
            sb.append(context.getString(R.string.noNetwork));
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            sb.append(context.getString(R.string.mobileNework));
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            sb.append(context.getString(R.string.wifi));
        }

//        sb.append(context.getString(R.string.selectedElementIs));
//        sb.append(" ");

        TextReader.read(sb.toString());

    }


    private void readOff(Context context){
        BatteryManager bm = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.displayOff));
        sb.append(" ");
        sb.append(context.getString(R.string.batteryIs));
        sb.append(" ");
        sb.append(batLevel);
        sb.append(" ");
        sb.append(context.getString(R.string.percent));


//        sb.append(context.getString(R.string.selectedElementIs));
//        sb.append(" ");

        TextReader.read(sb.toString());
    }



}
