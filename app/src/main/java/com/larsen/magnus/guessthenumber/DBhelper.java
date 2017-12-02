package com.larsen.magnus.guessthenumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by magnus on 12.05.16.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "HallOfFameDB";
    private static final int DB_VERSION = 2;
    private SQLiteDatabase db;
    Context context;

    DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQLStatement = "CREATE TABLE IF NOT EXISTS scoreTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, secretnum INTEGER, score INTEGER, level TEXT)";
        db.execSQL(createSQLStatement);

        ContentValues insertValues = new ContentValues();

        try {
            db.insert("scoreTable", null, insertValues);

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
