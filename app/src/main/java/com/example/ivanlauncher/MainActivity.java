package com.example.ivanlauncher;

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
import com.example.ivanlauncher.preferences.PreferencesLoader;
import com.example.ivanlauncher.ui.UserInterfaceEngine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    UserInterfaceEngine ui = null;
    public static final int REQUEST = 79;


    // To keep track of activity's window focus
    boolean currentFocus;

    // To keep track of activity's foreground/background status
    boolean isPaused;

    Handler collapseNotificationHandler;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        currentFocus = hasFocus;

        if (!hasFocus) {

            // Method that handles loss of window focus
            collapseNow();
        }
    }

    public void collapseNow() {

        // Initialize 'collapseNotificationHandler'
        if (collapseNotificationHandler == null) {
            collapseNotificationHandler = new Handler();
        }

        // If window focus has been lost && activity is not in a paused state
        // Its a valid check because showing of notification panel
        // steals the focus from current activity's window, but does not
        // 'pause' the activity
        if (!currentFocus && !isPaused) {

            // Post a Runnable with some delay - currently set to 300 ms
            collapseNotificationHandler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    // Use reflection to trigger a method from 'StatusBarManager'

                    Object statusBarService = getSystemService("statusbar");
                    Class<?> statusBarManager = null;

                    try {
                        statusBarManager = Class.forName("android.app.StatusBarManager");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Method collapseStatusBar = null;

                    try {

                        // Prior to API 17, the method to call is 'collapse()'
                        // API 17 onwards, the method to call is `collapsePanels()`

                        if (Build.VERSION.SDK_INT > 16) {
                            collapseStatusBar = statusBarManager.getMethod("collapsePanels");
                        } else {
                            collapseStatusBar = statusBarManager.getMethod("collapse");
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    collapseStatusBar.setAccessible(true);

                    try {
                        collapseStatusBar.invoke(statusBarService);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    // Check if the window focus has been returned
                    // If it hasn't been returned, post this Runnable again
                    // Currently, the delay is 100 ms. You can change this
                    // value to suit your needs.
                    if (!currentFocus && !isPaused) {
                        collapseNotificationHandler.postDelayed(this, 100L);
                    }

                }
            }, 300L);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        // Activity's been paused
        isPaused = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //todo ask for setting for hiding the toolbar

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

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

        requestPermission();

        offerReplacingDefaultDialer();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        ScreenOnOffBroadCastReciever mReceiver = new ScreenOnOffBroadCastReciever();
//        registerReceiver(mReceiver, intentFilter);


        PreferencesLoader.initPrefernces(this);
    }

    private void offerReplacingDefaultDialer() {
        if (!getSystemService(TelecomManager.class).getDefaultDialerPackage().equals(getPackageName())) {
            Intent intent = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER);
            intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getPackageName());
            startActivity(intent);
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_CONTACTS)) {
//            ContactsProvider.refreshContacts(getApplicationContext());
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CONTACTS,
                            android.Manifest.permission.READ_CALL_LOG,
                            android.Manifest.permission.READ_SMS,
                            android.Manifest.permission.CALL_PHONE},
                    REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    ContactsProvider.refreshContacts(getApplicationContext());
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    public void onResume() {
        super.onResume();

        isPaused = false;
        ui.resetUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        TextRe
    }


    @Override
    public void onBackPressed() {
        // Write your code here

//        super.onBackPressed();
    }

}
