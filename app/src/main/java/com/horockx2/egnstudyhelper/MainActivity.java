package com.horockx2.egnstudyhelper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DialogDbHelper mDbHelper = new DialogDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

//        String dropSql = "DROP TABLE " + DialogDBContract.DialogEntry.TABLE_NAME;
//        db.execSQL(dropSql);
//
//        String SQL_CREATE_ENTRIES =
//                "CREATE TABLE " + DialogDBContract.DialogEntry.TABLE_NAME + " (" +
//                        DialogDBContract.DialogEntry._ID + " INTEGER PRIMARY KEY," +
//                        DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME + " TEXT," +
////                    DialogDBContract.DialogEntry.COLUMN_NAME_DIALOG + " TEXT," +
//                        DialogDBContract.DialogEntry.COLUMN_NAME_DAILY_CHECK_DAY + " TEXT," +
//                        DialogDBContract.DialogEntry.COLUMN_NAME_IS_FAIL +" TEXT)";
//
//        db.execSQL(SQL_CREATE_ENTRIES);


        ArrayList<String> dialogFileList = DialogManager.GetDialogFileNames(this);

        for(String fileName: dialogFileList)
        {
            String sql = "select " + DialogDBContract.DialogEntry._ID + " from " + DialogDBContract.DialogEntry.TABLE_NAME
                    + " where " + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME + " = '"
                    + fileName + "';";

            Cursor result = db.rawQuery(sql, null);

            if(result.moveToFirst() == false)
            {
                String[] step1 = fileName.split("\\.");
                String[] step2 = step1[0].split("_");
                int id = Integer.parseInt(step2[1]);

//                String dialog = DialogManager.GetDialogJsonFromFile(this, fileName);
//                dialog = dialog.replace("\r", "");
//                dialog = dialog.replace("\n", "");
//                dialog = dialog.replace("\t", "");

                String insertSql = "insert into " + DialogDBContract.DialogEntry.TABLE_NAME
                         + " values(" + id + ",'" + fileName + "','','" + DialogDBContract.DialogEntry.IS_FAIL_FALSE + "');";
                db.execSQL(insertSql);
            }



        }

    }

    public void OnClickAddNewDialog(View view) {
        Log.Start();

        Intent intent = new Intent(this, AddNewDialogActivity.class);
        startActivity(intent);
    }

    public void OnClickOption(View view)
    {
        Log.Start();

        Intent intent = new Intent(this, OptionActivity.class);
        startActivity(intent);
    }

    public void OnClickDebug(View view) {

        Intent intent = new Intent(this, DialogActivity.class);
        intent.putExtra("dialogChapter", 57);
        startActivity(intent);
    }

    public void OnClickDialogList(View view)
    {
        Intent intent = new Intent(this, DialogListActivity.class);
        startActivity(intent);
    }

    public void OnClickTest(View view) {

    }

    public void OnClickDailyRandomTest(View view) {


    }
}
