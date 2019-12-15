package com.example.ivanlauncher.ui;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GestureListener implements View.OnTouchListener {

    private int min_distance = 100;
    private float downX;
    private float downY;
    private View v;


    public void onRightToLeftSwipe() {
        Log.e("RIGHT-LEFT", "please pass SwipeDetector.onSwipeEvent Interface instance");
    }

    public void onLeftToRightSwipe() {
        Log.e("LEFT-RIGHT", "please pass SwipeDetector.onSwipeEvent Interface instance");
    }

    public void onTopToBottomSwipe() {
        Log.e("TOP-DOWN", "please pass SwipeDetector.onSwipeEvent Interface instance");
    }

    public void onBottomToTopSwipe() {
        Log.e("DOWN-TOP", "please pass SwipeDetector.onSwipeEvent Interface instance");
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                float upX = event.getX();
                float upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                //HORIZONTAL SCROLL
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (Math.abs(deltaX) > min_distance) {
                        // left or right
                        if (deltaX < 0) {
                            this.onLeftToRightSwipe();
                            return true;
                        }
                        if (deltaX > 0) {
                            this.onRightToLeftSwipe();
                            return true;
                        }
                    } else {
                        //not long enough swipe...
                        return false;
                    }
                }
                //VERTICAL SCROLL
                else {
                    if (Math.abs(deltaY) > min_distance) {
                        // top or down
                        if (deltaY < 0) {
                            this.onTopToBottomSwipe();
                            return true;
                        }
                        if (deltaY > 0) {
                            this.onBottomToTopSwipe();
                            return true;
                        }
                    } else {
                        //not long enough swipe...
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}