package com.example.ivanlauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ivanlauncher.ui.GestureListener;
import com.example.ivanlauncher.ui.UserInterfaceEngine;

public class MainActivity extends AppCompatActivity {

    UserInterfaceEngine ui = null;
    public static final int REQUEST = 79;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this blicks notifications but and the touch listener !
//        getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);

        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ui = new UserInterfaceEngine(getApplicationContext(), (TextView) findViewById(R.id.maintextView));


        findViewById(R.id.main_view_main_layout).setOnTouchListener(new GestureListener() {
            public void onRightToLeftSwipe() {
                ui.enter();
            }

            public void onLeftToRightSwipe() {
                ui.back();
            }

            public void onTopToBottomSwipe() {
                ui.selectNext();
            }

            public void onBottomToTopSwipe() {
                ui.selectPrevious();
            }
        });

        requestPermission();

        offerReplacingDefaultDialer();
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
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.CALL_PHONE}, REQUEST);
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

        ui.resumeUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ui.destroy();
    }
}
