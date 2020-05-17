package com.example.ivanlauncher.ui;

import android.content.Context;
import android.widget.TextView;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.common.TextReader;
import com.example.ivanlauncher.preferences.DbBackgroundHelper;
import com.example.ivanlauncher.ui.elements.BookMark;

import java.util.List;

public class LockScreenMenu implements MenuInterface {

    private UserInterfaceEngine parent;

    private Context context;

    private TextView tv;

    public LockScreenMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        this.tv = tv;
        this.context = context;
        this.parent = parent;
    }


    @Override
    public void resetUI() {
        notifyForChanges();
    }

    @Override
    public void selectNext() {
        notifyForChanges();
    }

    @Override
    public void selectPrevious() {
        notifyForChanges();
    }

    @Override
    public void back() {
        notifyForChanges();
    }

    @Override
    public void enter() {
        TextReader.read(context.getString(R.string.screenUnLocked));
        parent.goToMainMenu();
    }

    @Override
    public void notifyForChanges() {
        tv.setText(context.getText(R.string.screenLocked));
        tv.invalidate();
        TextReader.read(tv.getText().toString());
    }
}
