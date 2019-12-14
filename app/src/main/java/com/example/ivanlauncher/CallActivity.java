package com.example.ivanlauncher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public final class CallActivity extends AppCompatActivity {

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_call);

        Intent intent = this.getIntent();

        Uri uri = intent.getData();

//        this.number = uri.getSchemeSpecificPart();
    }

    protected void onStart() {
        super.onStart();

        findViewById(R.id.answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OngoingCall.answer();
            }
        });

        findViewById(R.id.hangup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OngoingCall.hangup();
            }
        });

        OngoingCall.callActivity = this;
    }

    public final void updateUi(int state) {
        findViewById(R.id.answer).setVisibility(state == Call.STATE_RINGING ? View.VISIBLE : View.GONE);
        findViewById(R.id.hangup).setVisibility(state == Call.STATE_DIALING || state == Call.STATE_RINGING || state == Call.STATE_ACTIVE ? View.VISIBLE : View.GONE);
    }

    protected void onStop() {
        super.onStop();
//        this.disposables.clear();
    }





}