package com.example.ivanlauncher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ivanlauncher.contacts.ContactsLoader;
import com.example.ivanlauncher.simple_camera.CamerActivity;
import com.example.ivanlauncher.ui.Contact;
import com.example.ivanlauncher.ui.GestureListener;
import com.example.ivanlauncher.ui.MenuElement;
import com.example.ivanlauncher.ui.TextReader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static MenuElement root = new MenuElement("Root", null);
    public static MenuElement contacts;

    static {
        MenuElement status = new MenuElement("Status", root);
        contacts = new MenuElement("Contacts", root);
        MenuElement readImage = new MenuElement("Send Email", root);
        MenuElement settings = new MenuElement("Settings", root);
        MenuElement allApps = new MenuElement("AllApps", root);
//        MenuElement emailSender = new MenuElement("Emails", root);


        root.setChildren(new ArrayList<MenuElement>());
        root.getChildren().add(status);
        root.getChildren().add(contacts);
        root.getChildren().add(readImage);
        root.getChildren().add(settings);
        root.getChildren().add(allApps);
//        root.getChildren().add(emailSender);

    }


    MenuElement currentPosition = root;
    TextView tv;


    TextReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this blicks notifications but and the touch listener !
//        getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);

        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv = findViewById(R.id.textView);
        tv.setOnTouchListener(new GestureListener(){
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
        });


        requestPermission(this);

        offerReplacingDefaultDialer();

        reader = new TextReader(getApplicationContext());

        notifyForChanges();
    }


    private void offerReplacingDefaultDialer() {
        if (!getSystemService(TelecomManager.class).getDefaultDialerPackage().equals(getPackageName())) {
            Intent intent = new Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER);
            intent.putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getPackageName());
            startActivity(intent);
        }
    }


    public static final int REQUEST_CALL_PHONE = 77;
    public static final int REQUEST_READ_CONTACTS = 79;

    private static void requestPermission(MainActivity activity) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CALL_PHONE)) {
//            // show UI part if you want here to show some rationale !!!
//        } else {
//            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE},
//                    REQUEST_CALL_PHONE);
//        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_CONTACTS)) {
            activity.refreshContacts(ContactsLoader.getAllContacts(activity));
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.CALL_PHONE},
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
        } else if ("Settings".equals(theNewPosition.getName())) {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            return;
        } else if("Status".equals(theNewPosition.getName())){
            readStatus();
            return;
        } else if("Send Email".equals(theNewPosition.getName())){
            Intent i = new Intent(this, CamerActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        this.currentPosition = theNewPosition;
        notifyForChanges();
    }

    private void readStatus() {

        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        reader.read("The battery persange is "+ batLevel);

    }

    private void notifyForChanges() {
        tv.setText(this.currentPosition.getName() + " : " + this.currentPosition.getFocusChildName());
        tv.invalidate();

       reader.read(tv.getText().toString());
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

    //todo check when is needed to refresh that may be on resume or on start !!!
    void refreshContacts(ArrayList list) {
        this.contacts.setChildren(list);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
