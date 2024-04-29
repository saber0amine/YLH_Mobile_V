package com.example.yallah_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Yallah.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_AGE = "age";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_GENDER + " TEXT," +
                    COLUMN_AGE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public String checkUserExists(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            String userName = nameIndex != -1 ? cursor.getString(nameIndex) : null;
            cursor.close();
            return   userName;
        } else {
            return null ;
        }

    }


















}