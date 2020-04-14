package com.example.ivanlauncher.preferences;

/**
 * Created by x on 10.7.2017 г..
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ivanlauncher.ui.elements.BookMark;

import java.util.ArrayList;

public class DbBackgroundHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BlindLauncher.db";
    public static final String BOOKMARKS_TABLE_NAME = "BOOKMARKS";
    public static final String BOOKMARKS_COLUMN_ID = "id";
    public static final String BOOKMARKS_COLUMN_SOURCE = "source";
    public static final String BOOKMARKS_COLUMN_NAME = "name";


    public DbBackgroundHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!tableExists(db, BOOKMARKS_TABLE_NAME)) {
            db.execSQL(
                    String.format("CREATE TABLE IF NOT EXISTS %s ( %s integer primary key, %s text, %s text )",
                            BOOKMARKS_TABLE_NAME,
                            BOOKMARKS_COLUMN_ID,
                            BOOKMARKS_COLUMN_SOURCE,
                            BOOKMARKS_COLUMN_NAME));
            //prefill if needed
        }
    }

    boolean tableExists(SQLiteDatabase db, String tableName) {
        if (tableName == null || db == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOKMARKS_TABLE_NAME);
        onCreate(db);
    }

    public BookMark insertBookmark(String name, String source) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKMARKS_COLUMN_SOURCE, source);
        contentValues.put(BOOKMARKS_COLUMN_NAME, name);
        long id = db.insert(BOOKMARKS_TABLE_NAME, null, contentValues);

        return new BookMark((int) id, name, source);
    }

    public Integer deleteUserBookmark(BookMark bookMark) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BOOKMARKS_TABLE_NAME,
                BOOKMARKS_COLUMN_ID + " = ? ",
                new String[]{String.valueOf(bookMark.getId())});
    }

    public Integer updateBookmark(BookMark bookmark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKMARKS_COLUMN_NAME, bookmark.getName());
        contentValues.put(BOOKMARKS_COLUMN_SOURCE, bookmark.getSource());
        return db.update(BOOKMARKS_TABLE_NAME, contentValues,
                BOOKMARKS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(bookmark.getId())});
    }

    public ArrayList<BookMark> getAllBookmarks() {
        ArrayList<BookMark> array_list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + BOOKMARKS_TABLE_NAME + " order by " + BOOKMARKS_COLUMN_ID, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {//todo ?? check
            array_list.add(new BookMark(res.getInt(res.getColumnIndex(BOOKMARKS_COLUMN_ID)),
                    res.getString(res.getColumnIndex(BOOKMARKS_TABLE_NAME)),
                    res.getString(res.getColumnIndex(BOOKMARKS_COLUMN_SOURCE))));
            res.moveToNext();
        }
        return array_list;
    }

}
