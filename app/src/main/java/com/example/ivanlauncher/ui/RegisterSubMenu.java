package com.example.ivanlauncher.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.contacts.CallRegisterLoader;
import com.example.ivanlauncher.ui.elements.CallLogDetail;
import com.example.ivanlauncher.ui.elements.RegisterMenuElementType;

import java.util.ArrayList;
import java.util.List;

public class RegisterSubMenu implements MenuInterface {

    private boolean confirmed = false;

    public RegisterSubMenu(Context context, TextView tv, UserInterfaceEngine parent) {
//        reader = new TextReader(context);
//        contacts = ContactsProvider.getContacts(context);
        this.tv = tv;
        this.context = context;
        this.parent = parent;

    }

    private List<CallLogDetail> logDetails;


    private UserInterfaceEngine parent;

    private Context context;


    private RegisterMenuElementType[] menu;

    //    TextReader reader;
    private TextView tv;

    private int currentPosition = 0;


    RegisterMenuElementType type;

    @Override
    public void resetUI() {
        confirmed = false;
        currentPosition = 0;
        logDetails = new ArrayList<>(CallRegisterLoader.realodAllCallHistory(context, type));

        if (logDetails.size() == 0) {
            back();
            return;
        }

        notifyForChanges();
    }

    @Override
    public void selectNext() {
        if (currentPosition < logDetails.size() - 1) {
            currentPosition++;
        }
        notifyForChanges();
    }

    @Override
    public void selectPrevious() {
        if (currentPosition > 0) {
            currentPosition--;
        }
        notifyForChanges();
    }

    @Override
    public void back() {
        if (confirmed) {
            confirmed = false;
        } else {
            parent.goToRegister();
        }
    }

    @Override
    public void enter() {
        CallLogDetail selectedDetail = logDetails.get(currentPosition);

        if(!confirmed){
            TextReader.read(context.getString(R.string.dailing) + " " + selectedDetail.getName() +" "+context.getString(R.string.pleaseConfirm) );
            confirmed = true;

            return;
        }

        TextReader.read(context.getString(R.string.dailing) + " " + selectedDetail.getName());

        try {
            //to finish reading the
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + selectedDetail.getPhoneNumber()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    @Override
    public void notifyForChanges() {

        CallLogDetail detail = logDetails.get(currentPosition);

        String info = detail.getInfo(context);

        tv.setText(info);
        tv.invalidate();

        TextReader.read(tv.getText().toString());
    }
}
