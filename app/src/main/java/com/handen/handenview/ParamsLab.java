package com.handen.handenview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.handen.handenview.database.DatabaseHelper;
import com.handen.handenview.database.DbSchema;
import com.handen.handenview.database.DbSchema.BaseTable.Cols;

import java.util.ArrayList;

import static com.handen.handenview.database.DbSchema.BaseTable.NAME;

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
        contentValues.put(Cols.NAME, "199");
        contentValues.put(Cols.MIN, 0);
        contentValues.put(Cols.MAX, 100);
        //contentValues.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        //contentValues.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        mDatabase.insert(NAME, null, contentValues);
    }

    public void update(String column, String value, int id) {
        //mDatabase.execSQL("");
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        mDatabase.update(NAME, contentValues, "_id = " + id, null);
    }

    public int getFirstId() {
        String[] columns = new String[1];
        columns[0] = "min(_id)";

        Cursor cursor = mDatabase.query(true,
                NAME,
                columns,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor.getCount() > 0 ? cursor.getInt(0) : 0;
    }

    public int getLastId() {
        String[] columns = new String[1];
        columns[0] = "max(_id)";

        Cursor cursor = mDatabase.query(true,
                NAME,
                columns,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor.getCount() > 0 ? cursor.getInt(0) : 0;
    }

    public String getValue(int currentRowId, String columnName) {
        String[] columns = new String[1];
        columns[0] = columnName;
        Cursor cursor = mDatabase.query(true,
                NAME,
                columns,
                "_id = " + currentRowId,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor.getCount() > 0 ? cursor.getString(0) : "";
    }

    public ArrayList<String> getNames() {
        ArrayList<String> ret = new ArrayList<>();
        String[] columns = new String[1];
        columns[0] = Cols.NAME;
        Cursor cursor = mDatabase.query(false,
                NAME,
                columns,
                null,
                null,
                null,
                null,
                "ID desc",
                null,
                null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i ++) {
            ret.add(cursor.getString(0));
            cursor.moveToNext();
        }

        /*if(cursor.getCount() > 0) {
            do {
                ret.add(cursor.getString(0));
                cursor.moveToNext();
            }
            while (!cursor.isLast());
        }
        */
        return ret;
    }

    public int getRowsCount() {
        String[] columns = new String[1];
        columns[0] = "count(*)";

        Cursor cursor = mDatabase.query(true,
                NAME,
                columns,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor.getCount() > 0 ? cursor.getInt(0) : 0;
    }

    public void delete(int currentRowId) {
        mDatabase.delete(DbSchema.BaseTable.NAME, "_id = " + currentRowId, null);
    }

    public int getPrevisiousId(int currentRowId) {
        if (currentRowId == getFirstId())
            return currentRowId;
        String[] columns = new String[1];
        columns[0] = "max(_id)";

        Cursor cursor = mDatabase.query(true,
                NAME,
                columns,
                "_id < " + currentRowId,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor.getCount() > 0 ? cursor.getInt(0) : currentRowId;
    }

    public int getNextId(int currentRowId) {
        if (currentRowId == getLastId())
            return currentRowId;
        String[] columns = new String[1];
        columns[0] = "min(_id)";

        Cursor cursor = mDatabase.query(true,
                NAME,
                columns,
                "_id > " + currentRowId,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor.getCount() > 0 ? cursor.getInt(0) : currentRowId;
    }

    public ArrayList<Integer> getIds() {
        ArrayList<Integer> ret = new ArrayList<>();
        String[] columns = new String[1];
        columns[0] = Cols.ID;
        Cursor cursor = mDatabase.query(false,
                NAME,
                columns,
                null,
                null,
                null,
                null,
                "ID desc",
                null,
                null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i ++) {
            ret.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        return ret;
    }
}
