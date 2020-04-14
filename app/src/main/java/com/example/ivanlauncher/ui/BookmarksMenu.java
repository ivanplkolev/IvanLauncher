package com.example.ivanlauncher.ui;

import android.content.Context;
import android.widget.TextView;

import com.example.ivanlauncher.common.TextReader;
import com.example.ivanlauncher.preferences.DbBackgroundHelper;
import com.example.ivanlauncher.ui.elements.BookMark;

import java.util.List;

public class BookmarksMenu implements MenuInterface {

    private List<BookMark> bookmarks;

    private UserInterfaceEngine parent;

    private Context context;

    private TextView tv;

    private int currentPosition = 0;

    public BookmarksMenu(Context context, TextView tv, UserInterfaceEngine parent) {
        this.tv = tv;
        this.context = context;
        this.parent = parent;
    }


    @Override
    public void resetUI() {
        currentPosition = 0;
        bookmarks = new DbBackgroundHelper(context).getAllBookmarks();

        if (bookmarks.size() == 0) {//todo first notify for the back
            back();
            return;
        }

        notifyForChanges();
    }

    @Override
    public void selectNext() {
        if (currentPosition < bookmarks.size() - 1) {
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
        parent.goToMainMenu();
    }

    @Override
    public void enter() {
        BookMark selectedBookmark = bookmarks.get(currentPosition);

        parent.goToUrl(selectedBookmark.getSource());
    }

    @Override
    public void notifyForChanges() {
        BookMark selectedBookmark = bookmarks.get(currentPosition);
        tv.setText(selectedBookmark.getName());
        tv.invalidate();
        TextReader.read(tv.getText().toString());
    }
}
