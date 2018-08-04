package com.example.aleksandrabramovski.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.*;
import java.util.ArrayList;

import static android.R.id.edit;

/**
 * Created by Aleksandr.Abramovski on 18/06/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static String LOG_TAG = "mylogs";

    public static final String DATABASE_NAME = "lithium.db";
    public static final String TABLE_NAME = "batts_table";
    public static final String COL_ID = "_id";
    public static final String COL_1 = "boxN";
    public static final String COL_2 = "condition";
    public static final String COL_3 = "serNum1";
    public static final String COL_4 = "serNum2";
    public static final String COL_5 = "serNum3";
    public static final String COL_6 = "Date";
    public static final String COL_7 = "Status";
    public static final String COL_8 = "Comment";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                    " boxN INTEGER, condition TEXT, serNum1 TEXT," +
                                                    " serNum2 TEXT, serNum3 TEXT, Date TEXT," +
                                                    " Status TEXT, Comment TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String boxn, String condition, String sernum1, String sernum2, String sernum3,
                              String date, String status, String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, boxn);
        contentValues.put(COL_2, condition);
        contentValues.put(COL_3, sernum1);
        contentValues.put(COL_4, sernum2);
        contentValues.put(COL_5, sernum3);
        contentValues.put(COL_6, date);
        contentValues.put(COL_7, status);
        contentValues.put(COL_8, comment);
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d(LOG_TAG, "battery inserted: \n" +
                    "boxN: " + boxn + "\n" +
                    "condition: " + condition + "\n" +
                    "serial: " + sernum1);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkIfBattExists(String edittextSearch){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;
        Cursor res = db.query(TABLE_NAME, new String[] { COL_3 }, "serNum1 = ?",
                new String[] { edittextSearch.toUpperCase() }, null, null, null);
        Log.d(LOG_TAG, "res count = " + res.getCount() + "result = " + result);
        if (res.getCount() > 0){
            result = true;
        }
        return result;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(TABLE_NAME, null, null, null, null, null, null);
        return res;
    }

    public Cursor getSearchData(String edittextSearch){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_3
                + " LIKE " + "'%" + edittextSearch + "%'", null);
        return res;
    }
}

