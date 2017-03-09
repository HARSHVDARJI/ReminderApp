package com.project.harsh.reminderapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by harsh on 9/3/17.
 */

class DbHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="userdata";
    public static final String TABLE_NAME="user";
    public static final String KEY_FNAME="rname";
    public static final String KEY_LNAME="rdatetime";
    public static final String KEY_ID="id";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_FNAME+" TEXT, "+KEY_LNAME+" TEXT)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

}

