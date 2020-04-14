package com.example.ivanlauncher.preferences;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ivanlauncher.R;


public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefs);

        //If you want to insert data in your settings
        PrefFragment settingsFragment = new PrefFragment();
//        settingsFragment. ...
//        getSupportFragmentManager().beginTransaction().replace(R.id.idddd, PrefFragment).commit();

        //Else
        getSupportFragmentManager().beginTransaction().replace(R.id.idddd, settingsFragment).commit();
    }




}