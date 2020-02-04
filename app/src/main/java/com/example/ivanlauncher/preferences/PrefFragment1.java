package com.example.ivanlauncher.preferences;


import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.ivanlauncher.R;

public class PrefFragment1 extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.settings, rootKey);
    }

}