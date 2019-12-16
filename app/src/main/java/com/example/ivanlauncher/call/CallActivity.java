package com.example.ivanlauncher.call;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.ui.GestureListener;

public final class CallActivity extends AppCompatActivity {

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_call);


        findViewById(R.id.camera_main_layout).setOnTouchListener(new GestureListener(){
            public void onRightToLeftSwipe() {
                Log.e("RIGHT-LEFT", "please pass SwipeDetector.onSwipeEvent Interface instance");
                OngoingCall.answer();
            }

            public void onLeftToRightSwipe() {
                Log.e("LEFT-RIGHT", "please pass SwipeDetector.onSwipeEvent Interface instance");
                OngoingCall.hangup();
            }

            public void onTopToBottomSwipe() {
                Log.e("TOP-DOWN", "please pass SwipeDetector.onSwipeEvent Interface instance");
//                MainActivity.this.onDown();
            }

            public void onBottomToTopSwipe() {
                Log.e("DOWN-TOP", "please pass SwipeDetector.onSwipeEvent Interface instance");
//                MainActivity.this.onUp();
            }
        });


        Intent intent = this.getIntent();

        ((TextView)findViewById(R.id.callInfo)).setText(intent.getStringExtra("number"));

    }

    protected void onStart() {
        super.onStart();

        OngoingCall.callActivity = this;
    }

//    public final void updateUi(int state) {
//        findViewById(R.id.answer).setVisibility(state == Call.STATE_RINGING ? View.VISIBLE : View.GONE);
//        findViewById(R.id.hangup).setVisibility(state == Call.STATE_DIALING || state == Call.STATE_RINGING || state == Call.STATE_ACTIVE ? View.VISIBLE : View.GONE);
//    }

    protected void onStop() {
        super.onStop();
//        this.disposables.clear();
    }


    void kill_activity()
    {
        finish();
    }





}