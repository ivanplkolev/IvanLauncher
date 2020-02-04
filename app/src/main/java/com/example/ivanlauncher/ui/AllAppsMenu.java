package com.example.ivanlauncher.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.ivanlauncher.contacts.App;
import com.example.ivanlauncher.contacts.AppLoader;

import java.util.List;

public class AllAppsMenu implements MenuInterface {


    private List<App> allApps;


    boolean confirmed = false;

    private UserInterfaceEngine parent;

    private Context context;

    private TextView tv;

    private int currentPosition = 0;

    public AllAppsMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        this.tv = tv;
        this.context = context;
        this.parent = parent;
    }

    @Override
    public void resetUI() {

        confirmed = false;

        currentPosition = 0;
        allApps = AppLoader.loadAllApps(context);

        if (allApps.size() == 0) {//todo first notify for the back
            back();
            return;
        }

        notifyForChanges();
    }

    @Override
    public void selectNext() {
        if (currentPosition < allApps.size() - 1) {
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
            notifyForChanges();
        } else {
            parent.goToMainMenu();
        }
    }

    @Override
    public void enter() {

        App selectedApp = allApps.get(currentPosition);

        //todo confirmation needed

        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(selectedApp.getPackageName());
        context.startActivity(launchIntent);



//        Toast.makeText(v.getContext(), appsList.get(pos).label.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void notifyForChanges() {
        App selectedApp = allApps.get(currentPosition);
        tv.setText(selectedApp.getName());
        tv.invalidate();
        TextReader.read(tv.getText().toString());
    }
}
