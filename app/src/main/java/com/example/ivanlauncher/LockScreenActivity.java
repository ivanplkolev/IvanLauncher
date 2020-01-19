package com.example.ivanlauncher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ivanlauncher.ui.GestureListener;
import com.example.ivanlauncher.ui.TextReader;

public class LockScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextReader.init(getApplicationContext());
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_view_main_layout).setOnTouchListener(new GestureListener() {
            public void onRightToLeftSwipe() {
                LockScreenActivity.this.finish();
            }

            public void onLeftToRightSwipe() {
                //do nothing
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
