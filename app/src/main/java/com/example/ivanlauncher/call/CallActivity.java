package com.example.ivanlauncher.call;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ivanlauncher.R;

public final class CallActivity extends AppCompatActivity {

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_call);

        Intent intent = this.getIntent();

        ((TextView)findViewById(R.id.callInfo)).setText(intent.getStringExtra("number"));

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


    void kill_activity()
    {
        finish();
    }





}