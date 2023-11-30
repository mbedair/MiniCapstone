package com.example.minicapstone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "States.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_STATE = "student_surname";
    private static final String COLUMN_TIMESTAMP = "student_firstname";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_STATE + " TEXT, " +
                        COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addState(String state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STATE, state);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Posture Recorded successfully", Toast.LENGTH_SHORT).show();
            int entryCount = getEntryCount(db);
            if (entryCount >= 500) {
                resetDatabase(db);
            }
        }
        db.close();
    }

    private int getEntryCount(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }

    private void resetDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Database reset", Toast.LENGTH_SHORT).show();
    }


    Cursor readAllDataSortedByColumnId() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
           cursor =  db.rawQuery( query + " ORDER BY " + COLUMN_ID + " ASC", null);
        }
        return cursor;
    }

    Cursor readDataFromLast30Minutes() {
        long currentTimeMillis = System.currentTimeMillis();
        long thirtyMinutesAgo = currentTimeMillis - (30 * 60 * 1000); // 30 minutes in milliseconds

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_TIMESTAMP + " >= " + thirtyMinutesAgo +
                " ORDER BY " + COLUMN_TIMESTAMP + " DESC";

        return db.rawQuery(query, null);
    }

}
