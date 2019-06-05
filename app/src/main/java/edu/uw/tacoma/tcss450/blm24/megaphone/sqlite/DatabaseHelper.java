package edu.uw.tacoma.tcss450.blm24.megaphone.sqlite;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MegaphoneUser.db";
    private Cursor mCursor;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE USER(Name TEXT PRIMARY KEY NOT NULL)";
        String INSERT_DEFAULT_NAME = "INSERT INTO USER (Name) VALUES('HuskyHippo')";

        db.rawQuery(CREATE_USER_TABLE, null);
        db.rawQuery(INSERT_DEFAULT_NAME, null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(db);
    }

    public void changeUsername(SQLiteDatabase db, String newName) {
        db.execSQL("DELETE FROM USER LIMIT 1"); // Delete the previous username.
        db.execSQL("INSERT INTO USER VALUES(" + newName + ")");
    }

    public String getUsername() {
        String result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER", null);

        if (cursor == null) {
            Log.d("hi", "getUsername: CURSOR IS NULL.");
        } else if (cursor.getCount() <= 0) {
            Log.d("hi", "Cursor is empty.");
        } else {
            cursor.moveToFirst();
            result = cursor.getString(0);
        }


        return result;
    }
}
