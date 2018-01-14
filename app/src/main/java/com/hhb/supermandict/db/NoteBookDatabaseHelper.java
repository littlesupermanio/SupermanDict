package com.hhb.supermandict.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HoHoibin on 14/01/2018.
 * Email: imhhb1997@gmail.com
 */

public class NoteBookDatabaseHelper extends SQLiteOpenHelper {
    public Context context;

    public static final String CREATE_NOTEBOOK = "create table if not exists notebook("
            + "_id integer primary key autoincrement,"
            + "input text,"
            + "output text)";

    public NoteBookDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTEBOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
