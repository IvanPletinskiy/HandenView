package com.handen.handenview.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.handen.handenview.database.DbSchema.*;

/**
 * Created by Vanya on 21.05.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "base.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BaseTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BaseTable.Cols.ID + "," +
                BaseTable.Cols.NAME + ", " +
                BaseTable.Cols.DESCRIPTION + ", " +
                BaseTable.Cols.MIN + "," +
                BaseTable.Cols.MAX + "," +
                BaseTable.Cols.UNITS +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
