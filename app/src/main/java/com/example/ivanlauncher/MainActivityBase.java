package com.example.ivanlauncher;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telecom.TelecomManager;

import androidx.core.app.ActivityCompat;

import com.example.ivanlauncher.lock_screen.ScreenOnOffBroadCastReciever;
import com.example.ivanlauncher.preferences.PreferencesLoader;
import com.example.ivanlauncher.ui.RestrictedActivity;

public abstract class MainActivityBase extends RestrictedActivity {

    public static final int REQUEST = 79;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestPermission();

        offerReplacingDefaultDialer();


        try {
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            ScreenOnOffBroadCastReciever mReceiver = new ScreenOnOffBroadCastReciever();
            registerReceiver(mReceiver, intentFilter);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

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

}
