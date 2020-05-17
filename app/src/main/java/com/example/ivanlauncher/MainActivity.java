package com.example.ivanlauncher;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.TelecomManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ivanlauncher.common.GestureListener;
import com.example.ivanlauncher.common.TextReader;
import com.example.ivanlauncher.lock_screen.ScreenOnOffBroadCastReciever;
import com.example.ivanlauncher.preferences.PreferencesLoader;
import com.example.ivanlauncher.ui.RestrictedActivity;
import com.example.ivanlauncher.ui.UserInterfaceEngine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends MainActivityBase {

    UserInterfaceEngine ui = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextReader.init(getApplicationContext());

        setContentView(R.layout.activity_main);

        ui = new UserInterfaceEngine(getApplicationContext(), findViewById(R.id.maintextView));


        findViewById(R.id.main_view_main_layout).setOnTouchListener(new GestureListener() {
            public void onRightToLeftSwipe() {
                ui.enter();
            }

            public void onLeftToRightSwipe() {
                ui.back();
            }

            public void onTopToBottomSwipe() {
                ui.selectPrevious();
            }

            public void onBottomToTopSwipe() {
                ui.selectNext();
            }
        });


        MainActivityObserver.setActiveMainActivity(this);
    }

    public void onResume() {
        super.onResume();

        ui.resetUI();
    }


    public void lockScreen() {
        ui.lockTheScreen();
    }

    public void openCall(String caller) {
        ui.openCall(caller);
    }


    public void updateCall(int newState) {
        ui.updateCall(newState);
    }

    public void closeCall() {
        ui.closeCall();
    }

}
