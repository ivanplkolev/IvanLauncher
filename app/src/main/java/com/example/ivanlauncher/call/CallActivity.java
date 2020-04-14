package com.example.ivanlauncher.call;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.common.GestureListener;
import com.example.ivanlauncher.common.TextReader;

public final class CallActivity extends AppCompatActivity {

    boolean speakName = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_call);


        findViewById(R.id.caller_layout).setOnTouchListener(new GestureListener() {
            public void onRightToLeftSwipe() {
                Log.e("RIGHT-LEFT", "please pass SwipeDetector.onSwipeEvent Interface instance");
                speakName = false;
                OngoingCall.answer();
            }

            public void onLeftToRightSwipe() {
                Log.e("LEFT-RIGHT", "please pass SwipeDetector.onSwipeEvent Interface instance");

                speakName = false;
                OngoingCall.hangup();


                TextReader.read(getApplicationContext().getString(R.string.closedConnection));
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


        String callerInfo = intent.getStringExtra("number");
        ((TextView) findViewById(R.id.callInfo)).setText(callerInfo);


        callernameRepeater = new CallernameRepeater(callerInfo);
        speakName = true;
        callernameRepeater.start();

    }


    protected void onStart() {
        super.onStart();

        OngoingCall.callActivity = this;
    }

    public final void updateUi(int state) {
        speakName = (state == Call.STATE_RINGING);
    }

    protected void onStop() {
        super.onStop();
//        this.disposables.clear();
    }


    void kill_activity() {

        TextReader.read(getApplicationContext().getString(R.string.closedConnection));

        finish();


    }


    private CallernameRepeater callernameRepeater;

    class CallernameRepeater extends Thread {

        private String callerName;

        CallernameRepeater(String callerName) {
            this.callerName = callerName;
        }

        @Override
        public void run() {
            while (speakName) {
                TextReader.read(callerName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}