package com.example.ivanlauncher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static MenuElement root = new MenuElement("Root", null);
    public static MenuElement contacts;

    static {
        contacts = new MenuElement("Contacts", root);
        MenuElement readImage = new MenuElement("ReadImage", root);


        root.setChildren(new ArrayList<MenuElement>());
        root.getChildren().add(contacts);
        root.getChildren().add(readImage);

    }


    MenuElement currentPosition = root;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        tv.setOnTouchListener(new View.OnTouchListener() {

            private int min_distance = 100;
            private float downX, downY, upX, upY;
            private View v;


            public void onRightToLeftSwipe() {
                Log.e("RIGHT-LEFT", "please pass SwipeDetector.onSwipeEvent Interface instance");
                MainActivity.this.onLeft();
            }

            public void onLeftToRightSwipe() {
                Log.e("LEFT-RIGHT", "please pass SwipeDetector.onSwipeEvent Interface instance");
                MainActivity.this.onRight();
            }

            public void onTopToBottomSwipe() {
                Log.e("TOP-DOWN", "please pass SwipeDetector.onSwipeEvent Interface instance");
                MainActivity.this.onDown();
            }

            public void onBottomToTopSwipe() {
                Log.e("DOWN-TOP", "please pass SwipeDetector.onSwipeEvent Interface instance");
                MainActivity.this.onUp();
            }

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        downX = event.getX();
                        downY = event.getY();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        upX = event.getX();
                        upY = event.getY();

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
        });

        refreshContacts(ContactsLoader.getAllContacts(this));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

        }   else {
            requestPermission(this);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            refreshContacts(ContactsLoader.getAllContacts(this));
        }   else {
            requestPermission(this);
        }

        offerReplacingDefaultDialer();

        notifyForChanges();
    }


    private void offerReplacingDefaultDialer() {
        if (!getSystemService(TelecomManager.class).getDefaultDialerPackage().equals( getPackageName())) {
            Intent intent = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER);
            intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getPackageName());
            startActivity(intent);
        }
    }



    public static final int REQUEST_CALL_PHONE = 77;
    public static final int REQUEST_READ_CONTACTS = 79;

    private static void requestPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CALL_PHONE)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_CONTACTS)) {

        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }



    private void onDown() {
        this.currentPosition.focusPrevChild();
        notifyForChanges();
    }

    private void onUp() {
        this.currentPosition.focusOnNextChild();
        notifyForChanges();

    }

    private void onLeft() {
        setNewPosition(this.currentPosition.getChildOnFocus());
    }


    private void onRight() {
        setNewPosition(this.currentPosition.getParent());
    }


    private void setNewPosition(MenuElement theNewPosition) {

        if (theNewPosition instanceof Contact) {
            //perform the dial
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {


                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ((Contact) theNewPosition).getPhoneNumber()));
                startActivity(intent);
            }
        }
        this.currentPosition = theNewPosition;
        notifyForChanges();
    }

    private void notifyForChanges() {
        tv.setText(this.currentPosition.getName() + ":" + this.currentPosition.getFocusChildName());
        tv.invalidate();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    refreshContacts(ContactsLoader.getAllContacts(this));
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }




//    List contacts = null;

    //todo check when is needed to refresh that may be on resume or on start !!!
    void refreshContacts(ArrayList list) {
        this.contacts.setChildren(list);
    }
}
