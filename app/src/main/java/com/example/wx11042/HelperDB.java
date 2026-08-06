package com.example.wx11042;

import static com.example.wx11042.Expanses.DESCRIPTION;
import static com.example.wx11042.Expanses.KEY_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
        String createTableQuery = "CREATE TABLE " + Expanses.TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DESCRIPTION + " TEXT, " +
                Expanses.AMOUNT + " REAL, " +
                Expanses.DATE + " TEXT, " +
                Expanses.CATEGORY + " TEXT)";

        Log.d(HelperDB.class.getName(), "Executing: " + createTableQuery);
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Expanses.TABLE_NAME);
        onCreate(db);
    }

    public long addExpanse(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, expense.getDescription());
        values.put(Expanses.AMOUNT, expense.getAmount());
        values.put(Expanses.CATEGORY, expense.getCategory());
        values.put(Expanses.DATE, expense.getDate());

        long id = db.insert(Expanses.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Expanses.TABLE_NAME + " ORDER BY " + Expanses.DATE + " DESC";

        Log.d(HelperDB.class.getName(), "Executing: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense(
                        cursor.getLong(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Expanses.AMOUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Expanses.CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Expanses.DATE))
                );
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return expenseList;
    }

}
