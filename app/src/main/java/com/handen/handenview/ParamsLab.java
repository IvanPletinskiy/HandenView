package com.handen.handenview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.handen.handenview.database.DatabaseHelper;
import com.handen.handenview.database.DbSchema.BaseTable;

/**
 * Created by Vanya on 21.05.2018.
 */

public class ParamsLab {
    private static ParamsLab paramsLab;

    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static ParamsLab get(Context context) {

        if (paramsLab == null) {
            paramsLab = new ParamsLab(context);
        }
        return paramsLab;
    }

    private ParamsLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DatabaseHelper(mContext).getWritableDatabase();
    }

    public void add() {
        ContentValues contentValues = new ContentValues();
        mDatabase.insert(BaseTable.NAME, null, contentValues);
    }
}
