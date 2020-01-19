package com.example.ivanlauncher;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ivanlauncher.contacts.ContactsProvider;
import com.example.ivanlauncher.ui.GestureListener;
import com.example.ivanlauncher.ui.TextReader;
import com.example.ivanlauncher.ui.UserInterfaceEngine;

public class MainActivity extends AppCompatActivity {

    UserInterfaceEngine ui = null;
    public static final int REQUEST = 79;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextReader.init(getApplicationContext());

        //this blicks notifications but and the touch listener !
//        getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);

        setContentView(R.layout.activity_main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ui = new UserInterfaceEngine(getApplicationContext(), (TextView) findViewById(R.id.maintextView));


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
        MyBroadCastReciever mReceiver = new MyBroadCastReciever();
        registerReceiver(mReceiver, intentFilter);
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
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS,android.Manifest.permission.READ_CALL_LOG,
                    android.Manifest.permission.CALL_PHONE}, REQUEST);
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

        ContactsProvider.getContacts(getApplicationContext());
    }

    public void onResume() {
        super.onResume();

        ui.resetUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        TextRe
    }
}
