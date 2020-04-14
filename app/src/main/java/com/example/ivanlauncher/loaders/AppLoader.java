package com.example.ivanlauncher.loaders;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.example.ivanlauncher.ui.elements.App;

import java.util.ArrayList;
import java.util.List;

public class AppLoader {


    public static List<App> loadAllApps(Context context) {
        ArrayList<App> apps = new ArrayList<>();

        PackageManager pm = context.getPackageManager();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
        for (ResolveInfo ri : allApps) {
            apps.add(new App(String.valueOf(ri.loadLabel(pm)), ri.activityInfo.packageName));
        }

        return apps;
    }
}
