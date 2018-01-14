package com.hhb.supermandict.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hhb.supermandict.db.NoteBookDatabaseHelper;

/**
 * Created by HoHoibin on 14/01/2018.
 * Email: imhhb1997@gmail.com
 */


public class DBUtil {

    public static Boolean queryIfItemExist(NoteBookDatabaseHelper dbhelper, String queryString){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query("notebook",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String s = cursor.getString(cursor.getColumnIndex("input"));
                if (queryString.equals(s)){
                    return true;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return false;
    }

    public static void insertValue(NoteBookDatabaseHelper dbhelper, ContentValues values){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        db.insert("notebook",null,values);
    }

    public static void deleteValue(NoteBookDatabaseHelper dbhelper,String deleteString){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        db.delete("notebook","input = ?",new String[]{deleteString});
    }

}
