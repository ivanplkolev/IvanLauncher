package com.example.ivanlauncher.lock_screen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.common.GestureListener;
import com.example.ivanlauncher.common.TextReader;

public class LockScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextReader.init(getApplicationContext());
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_view_main_layout).setOnTouchListener(new GestureListener() {
            public void onRightToLeftSwipe() {
                //do nothing
            }

            public void onLeftToRightSwipe() {
                LockScreenActivity.this.finish();
            }

            public void onTopToBottomSwipe() {
                //do nothing
            }

            public void onBottomToTopSwipe() {
                //do nothing
            }
        });
    }


    public void onResume() {
        super.onResume();

        // read status

    }





}
