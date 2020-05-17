package com.example.ivanlauncher.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.widget.TextView;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.camera.CameraActivity;
import com.example.ivanlauncher.common.TextReader;
import com.example.ivanlauncher.preferences.PreferencesActivity;
import com.example.ivanlauncher.ui.elements.MainMenuElementType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu implements MenuInterface {

    public MainMenu(Context context, TextView tv, UserInterfaceEngine parent) {
//        reader = new TextReader(context);
        this.tv = tv;
        this.context = context;
        this.parent = parent;

        menu = new MainMenuElementType[]{
        MainMenuElementType.STATUS,
        MainMenuElementType.REGISTER,
        MainMenuElementType.CONTACTS,
//         MainMenuElementType.IMAGE_READER;
//         MainMenuElementType.EMAIL_SENDER;
//         MainMenuElementType.SETTINGS;
         MainMenuElementType.CONTACT_GROUPS,
         MainMenuElementType.SMS
//         MainMenuElementType.EMAILS;
//       MainMenuElementType.BOOKMARKS;
//        MainMenuElementType.ALL_APPS
        };

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
            case SMS:
                parent.goToAllSMSes();
                break;
            case EMAILS:
                parent.goToAllEmails();
                break;
            case BOOKMARKS:
                parent.goToBookmarks();
                break;
            case ALL_APPS:
                parent.goToAllApps();
                break;
            case CONTACT_GROUPS:
                parent.goToContactGroups();
                break;
            case REGISTER:
                parent.goToRegister();
                break;
            case EMAIL_SENDER:
                Intent i = new Intent(context, CameraActivity.class);
                i.putExtra(CameraActivity.ACTION, CameraActivity.TYPE_SEND_IMAGE);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                break;
            case IMAGE_READER:
                Intent ir = new Intent(context, CameraActivity.class);
                ir.putExtra(CameraActivity.ACTION, CameraActivity.TYPE_READ_IMAGE);
                ir.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ir);
                break;
            case SETTINGS:
//                Intent is = new Intent(android.provider.Settings.ACTION_SETTINGS);
                Intent is = new Intent(context, PreferencesActivity.class);
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
        sb.append(context.getString(R.string.screenUnLocked));
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


//    public void destroy() {
//        reader.shutdown();
//    }
}
