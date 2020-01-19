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
//        reader = new TextReader(context);
        this.tv = tv;
        this.context = context;
        this.parent = parent;

        menu = new MainMenuElementType[6];
        menu[0] = MainMenuElementType.STATUS;
        menu[1] = MainMenuElementType.REGISTER;
        menu[2] = MainMenuElementType.CONTACTS;
        menu[3] = MainMenuElementType.IMAGE_READER;
        menu[4] = MainMenuElementType.EMAIL_SENDER;
        menu[5] = MainMenuElementType.SETTINGS;
    }

    UserInterfaceEngine parent;

    Context context;


    MainMenuElementType[] menu;

    //    TextReader reader;
    TextView tv;

    int currentPosition = 0;


    public void resetUI() {
        currentPosition = 0;
        notifyForChanges();
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
            case REGISTER:
                parent.goToRegister();
                break;
            case EMAIL_SENDER:
                Intent i = new Intent(context, CamerActivity.class);
                i.putExtra(CamerActivity.ACTION, CamerActivity.TYPE_SEND_IMAGE);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                break;
            case IMAGE_READER:
                Intent ir = new Intent(context, CamerActivity.class);
                ir.putExtra(CamerActivity.ACTION, CamerActivity.TYPE_READ_IMAGE);
                ir.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ir);
                break;
            case SETTINGS:
                Intent is = new Intent(android.provider.Settings.ACTION_SETTINGS);
                is.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(is);
                break;
        }
    }


    public void notifyForChanges() {
        MainMenuElementType currentElement = menu[currentPosition];
        tv.setText(context.getString(currentElement.getNameId()));
        tv.invalidate();
        if (currentElement == MainMenuElementType.STATUS) {
            readStatus();
        } else {
            TextReader.read(tv.getText().toString());
        }
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


//    public void destroy() {
//        reader.shutdown();
//    }
}
