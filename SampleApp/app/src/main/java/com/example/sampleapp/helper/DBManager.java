package com.example.sampleapp.helper;

/**
 * Created by sunil gowroji on 07/07/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sampleapp.model.RowsModel;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Saving the values in the table
    public void insert(String title, String desc, String path) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.TITLE, title);
        contentValue.put(DatabaseHelper.DESC, desc);
        contentValue.put(DatabaseHelper.PATH, path);

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    // Retrieves whole data, which is stored in the table
    public List<RowsModel> getData() {
        String[] columns = new String[]{DatabaseHelper._ID, DatabaseHelper.TITLE, DatabaseHelper.DESC, DatabaseHelper.PATH};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        List<RowsModel> mArrayList = new ArrayList<RowsModel>();

        if (cursor.moveToFirst()) {
            do {
                RowsModel row = new RowsModel();
                row.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE)));
                row.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESC)));
                row.setImageHref(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PATH)));
                mArrayList.add(row);
            } while (cursor.moveToNext());


        }
        cursor.close();
        return mArrayList;
    }

    // Check whether data is availble or not in the table
    public int isDataAvailable() {
        String[] columns = new String[]{DatabaseHelper._ID, DatabaseHelper.TITLE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getCount();
    }

}
