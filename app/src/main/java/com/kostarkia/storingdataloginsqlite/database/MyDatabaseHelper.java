package com.kostarkia.storingdataloginsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NEW = "my_user";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRSTNAME = "user_firstname";
    private static final String COLUMN_SURNAME = "user_surname";
    private static final String COLUMN_PASSWORD = "user_password";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Veritabanı oluşturmak için
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NEW + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRSTNAME + " TEXT UNIQUE, " +
                COLUMN_SURNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW);
    }

    // Kullanıcı eklemek için
    public void addUser(String firstName, String surName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRSTNAME, firstName);
        cv.put(COLUMN_SURNAME, surName);
        cv.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NEW, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }

    }

    //Giriş işleminde bilgileri kontrol etmek için
    public boolean checkUser(String firstName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_FIRSTNAME + " =? and " + COLUMN_PASSWORD + " =?";
        String[] selectionArgs = {firstName, password};
        Cursor cursor = db.query(TABLE_NEW, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count > 0) {
            return true;
        }
        return false;
    }

    //veritabanında firsname birden fazla kayıt var mı diye kontrol etmek için
    public boolean checkRegister(String firstName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_FIRSTNAME + " =?";
        String[] selectionArgs = {firstName};
        Cursor cursor = db.query(TABLE_NEW, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        if (count <= 0) {
            return false;
        }
        Toast.makeText(context, "Bu kullanıcı adına ait kayıt bulunmakta. Lütfen farklı bir kullanıcı adı giriniz.", Toast.LENGTH_SHORT).show();
        return true;
    }

}
