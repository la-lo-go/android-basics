package com.example.repaso.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "demo.db";

    public BD(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) throws SQLiteException{
        db.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLiteException{
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void insertUser(String username, String password) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        db.insert("users", null, contentValues);
        db.close();
    }

    public boolean checkUser(String username) throws SQLiteException{
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public boolean checkUsernamePassword(String username, String password) throws SQLiteException{
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public void deleteUser(String username) throws SQLiteException{
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users", "username = ?", new String[]{username});
        db.close();
    }

    public void updateUser(String username, String password) throws SQLiteException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        db.update("users", contentValues, "username = ?", new String[]{username});
        db.close();
    }

}

