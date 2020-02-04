package com.example.ivanlauncher.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.ivanlauncher.R;

import java.util.HashSet;


public class PreferencesActivity extends AppCompatActivity {



    private static SharedPreferences prefs;


    public static final String EMAIL_KEY = "emailUserName";
    public static final String EMAIL_PASS_KEY = "emailPassword";
    public static final String EMAIL_RECIPIENT_1_KEY = "receiver1";
    public static final String EMAIL_RECIPIENT_2_KEY = "receiver2";
    public static final String EMAIL_RECIPIENT_3_KEY = "receiver3";
    public static final String FORBID_STATUSBAR_KEY = "forbidStatusBar";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefs);

        //If you want to insert data in your settings
        PrefFragment1  settingsFragment = new PrefFragment1();
//        settingsFragment. ...
//        getSupportFragmentManager().beginTransaction().replace(R.id.idddd, PrefFragment1).commit();

        //Else
        getSupportFragmentManager().beginTransaction().replace(R.id.idddd, settingsFragment).commit();



         setPrefs(this);

    }


    public static void setPrefs(Context c){
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public static String getEmail(){
        return prefs.getString(EMAIL_KEY, null);
    }


    public static String getEmailPass(){
        return prefs.getString(EMAIL_PASS_KEY, null);
    }

    public static String[] getEmailRecepients(){
        HashSet<String> set = new HashSet<>();

        set.add(prefs.getString(EMAIL_RECIPIENT_1_KEY, null));
        set.add( prefs.getString(EMAIL_RECIPIENT_2_KEY, null));
        set.add(  prefs.getString(EMAIL_RECIPIENT_3_KEY, null));
        set.remove(null);

        return set.toArray(new String[set.size()]);
    }


    public static boolean getForbidStatusBar(){
        return prefs.getBoolean(FORBID_STATUSBAR_KEY, false);
    }

}