package com.example.wx11042;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperDB extends SQLiteOpenHelper {

    // --- Constants ---
    public static final String DATABASE_NAME = "dbexam.db";
    public static final int DATABASE_VERSION = 1;

    // --- Constructor ---
    public HelperDB(Context context) {
        // Pass the context, database name, a null cursor factory, and the version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // --- Required Methods ---

    @Override
    public void onCreate(SQLiteDatabase db) {
        // This method runs the very first time the database is created.
        // This is where you will write your SQL string to create your tables.

        /* Example:
        String strCreate = "CREATE TABLE my_table (id INTEGER PRIMARY KEY, name TEXT);";
        db.execSQL(strCreate);
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method runs when you increase the DATABASE_VERSION number.
        // You usually use this to drop old tables and create new ones.

        /* Example:
        db.execSQL("DROP TABLE IF EXISTS my_table");
        onCreate(db);
        */
    }
}
