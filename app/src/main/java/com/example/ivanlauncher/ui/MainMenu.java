package com.example.ivanlauncher.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.widget.TextView;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.simple_camera.CamerActivity;
import com.example.ivanlauncher.ui.elements.MainMenuElementType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu implements MenuInterface {

    public MainMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        reader = new TextReader(context);
        this.tv = tv;
        this.context = context;
        this.parent = parent;

        menu = new MainMenuElementType[4];
        menu[0] = MainMenuElementType.STATUS;
        menu[1] = MainMenuElementType.CONTACTS;
        menu[2] = MainMenuElementType.EMAIL_SENDER;
        menu[3] = MainMenuElementType.SETTINGS;
    }

    UserInterfaceEngine parent;

    Context context;


    MainMenuElementType[] menu ;

    TextReader reader;
    TextView tv;

    int currentPosition = 0;


    public void resumeUI() {
        currentPosition = 0;
        readStatus();
    }

    public void selectNext() {
        if (currentPosition < menu.length - 1) {
            currentPosition++;
        }
        notifyForChanges();
    }

    public void selectPrevious() {
        if (currentPosition > 0) {
            currentPosition--;
        }
        notifyForChanges();
    }

    public void back() {
        notifyForChanges();
    }

    public void enter() {
        MainMenuElementType currentElement = menu[currentPosition];

        switch (currentElement) {
            case STATUS:
                readStatus();
                break;
            case CONTACTS:
                parent.goToContacts();
                break;
            case EMAIL_SENDER:
                Intent i = new Intent(context, CamerActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                break;
            case SETTINGS:
                context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                break;
        }
    }


    public void notifyForChanges() {
        tv.setText(context.getString(menu[currentPosition].getNameId()));
        tv.invalidate();

        reader.read(tv.getText().toString());
    }

    private void readStatus() {
        BatteryManager bm = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.currentTime));
        sb.append(" ");
        sb.append(new SimpleDateFormat("HH:mm").format(new Date()));
        sb.append(" ");
        sb.append(context.getString(R.string.batteryIs));
        sb.append(" ");
        sb.append(batLevel);
        sb.append(" ");
        sb.append(context.getString(R.string.percent));
        sb.append(" ");

        if (networkInfo == null || !networkInfo.isConnected()) {
            sb.append(context.getString(R.string.noNetwork));
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            sb.append(context.getString(R.string.mobileNework));
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            sb.append(context.getString(R.string.Wifi));
        }

        sb.append(context.getString(R.string.selectedElementIs));
        sb.append(" ");

        reader.read(sb.toString());
    }


    public void destroy() {
        reader.shutdown();
    }
}
