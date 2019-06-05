package edu.uw.tacoma.tcss450.blm24.megaphone.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UsernameDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "MegaphoneUser.db";

    private UsernameDBHelper mCourseDBHelper;
    private SQLiteDatabase mSQLiteDatabase;


    public UsernameDB(Context context) {
        mCourseDBHelper = new UsernameDBHelper(context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mCourseDBHelper.getWritableDatabase();
    }

    public String getUsername() {
        String result = null;

        Cursor cursor = mSQLiteDatabase.query("USER", new String[]{"Name"}, null, null, null, null, null);

        if (cursor == null) {
            Log.d("ugh", "CURSOR IS NULL");
        } else if (cursor.getCount() <= 0) {
            Log.d("ugh", "CURSOR IS EMPTY");
        } else {
            cursor.moveToFirst();
            result = cursor.getString(0);
        }

        return result;
    }

    public void setUsername(String name) {
        // Delete the previous username.
        mSQLiteDatabase.execSQL("DELETE FROM USER");
        String NEW_NAME_SQL = "INSERT INTO USER VALUES(" + "'" + name + "')";
        mSQLiteDatabase.execSQL(NEW_NAME_SQL);

    }

    class UsernameDBHelper extends SQLiteOpenHelper {

        private final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS USER (Name TEXT PRIMARY KEY)";
        private final String DELETE_TABLE_SQL = "DROP TABLE USER";

        public UsernameDBHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory,
                              int version) {
            super(context, name, factory, version);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
           // sqLiteDatabase.execSQL("DROP TABLE USER");
            sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL(DELETE_TABLE_SQL);
            onCreate(mSQLiteDatabase);
        }

    }

}