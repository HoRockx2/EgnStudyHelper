package com.horockx2.egnstudyhelper;

import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2018-04-29.
 */

public class DialogDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Dialog.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DialogDBContract.DialogEntry.TABLE_NAME + " (" +
                    DialogDBContract.DialogEntry._ID + " INTEGER PRIMARY KEY," +
                    DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME + " TEXT," +
//                    DialogDBContract.DialogEntry.COLUMN_NAME_DIALOG + " TEXT," +
                    DialogDBContract.DialogEntry.COLUMN_NAME_DAILY_CHECK_DAY + " TEXT," +
                    DialogDBContract.DialogEntry.COLUMN_NAME_IS_FAIL +" TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE " + DialogDBContract.DialogEntry.TABLE_NAME;

    public DialogDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
