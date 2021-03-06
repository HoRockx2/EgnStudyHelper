package com.horockx2.egnstudyhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by user on 2018-02-26.
 */

public class DialogManager {
    public class DialogModel
    {
        private String fileName;
        private String dialog;

        DialogModel(String fileName, String dialog)
        {
            this.fileName = fileName;
            this.dialog = dialog;
        }
    }

    public static String GetDialogJsonFromFile(Context context, String fileName)
    {
        String json = null;

        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<DialogFileModel> GetDialogFileNameAndDailyCheckList(Context context)
    {
        DialogDbHelper dbHelper = new DialogDbHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select " + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME + ","
                + DialogDBContract.DialogEntry.COLUMN_NAME_DAILY_CHECK_DAY + ","
                + DialogDBContract.DialogEntry.COLUMN_NAME_IS_FAIL + " from "
                + DialogDBContract.DialogEntry.TABLE_NAME + ";";

        Cursor results = db.rawQuery(sql, null);

        ArrayList<DialogFileModel> retList = new ArrayList<DialogFileModel>();
        results.moveToFirst();
        while(!results.isAfterLast())
        {
            retList.add(new DialogFileModel(results.getString(0), results.getString(1), results.getString(2)));

            results.moveToNext();
        }
        results.close();

        return retList;
    }

    public static String GetRandomDialogFileNameByPriority(Context context)
    {
        DialogDbHelper dbHelper = new DialogDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strRet = "";

        String sql = "select " + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME +
                " from " + DialogDBContract.DialogEntry.TABLE_NAME +
                " where " + DialogDBContract.DialogEntry.COLUMN_NAME_DAILY_CHECK_DAY +
                " = '';";

        Cursor neverCheckDialogs = db.rawQuery(sql, null);
        if(neverCheckDialogs.getCount() != 0)
        {
            Random rand = new Random();
            int randInt = rand.nextInt(neverCheckDialogs.getCount());

            neverCheckDialogs.moveToFirst();
            neverCheckDialogs.move(randInt);

            strRet = neverCheckDialogs.getString(0);
        }
        else
        {
            sql = "select " + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME +
                    " from " + DialogDBContract.DialogEntry.TABLE_NAME +
                    " where " + DialogDBContract.DialogEntry.COLUMN_NAME_DAILY_CHECK_DAY +
                    " not in ('" + GetYesterDay() + "','" + GetToday() + "');";

            Cursor notCheckedYesterDayAndToday = db.rawQuery(sql, null);

            if(notCheckedYesterDayAndToday.getCount() != 0)
            {
                Random rand = new Random();
                int randInt = rand.nextInt(notCheckedYesterDayAndToday.getCount());

                notCheckedYesterDayAndToday.moveToFirst();
                notCheckedYesterDayAndToday.move(randInt);

                strRet = notCheckedYesterDayAndToday.getString(0);
            }
            else
            {
                sql = "select " + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME +
                        " from " + DialogDBContract.DialogEntry.TABLE_NAME +
                        " where " + DialogDBContract.DialogEntry.COLUMN_NAME_DAILY_CHECK_DAY +
                        " not in ('" + GetToday() + "');";

                Cursor notCheckedToday = db.rawQuery(sql, null);

                if(notCheckedToday.getCount() != 0)
                {
                    Random rand = new Random();
                    int randInt = rand.nextInt(notCheckedToday.getCount());

                    notCheckedToday.moveToFirst();
                    notCheckedToday.move(randInt);

                    strRet = notCheckedToday.getString(0);
                }
                else
                {
                    Log.Info("Select random dialog Error!!");
                }
            }
        }

        return strRet;
    }

    public static DialogFileModel GetCertainDialogFileNameAndDailyCheckList(Context context, String FileName)
    {
        DialogDbHelper dbHelper = new DialogDbHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select " + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME + ","
                + DialogDBContract.DialogEntry.COLUMN_NAME_DAILY_CHECK_DAY + ","
                + DialogDBContract.DialogEntry.COLUMN_NAME_IS_FAIL + " from "
                + DialogDBContract.DialogEntry.TABLE_NAME + " where "
                + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME+ " = '" + FileName
        + "';";

        Cursor results = db.rawQuery(sql, null);

        DialogFileModel ret = null;
        results.moveToFirst();
        while(!results.isAfterLast())
        {
            ret = new DialogFileModel(results.getString(0), results.getString(1), results.getString(2));
        }

        results.close();

        return ret;
    }

    public static ArrayList<String> GetDialogFileNames(Context context)
    {
        ArrayList<String> dialogList = new ArrayList<String>();

        try
        {
            String[] list;
            list = context.getAssets().list("");
            if(list.length  > 0)
            {
                for(String file : list)
                {
                    if(file.contains("chapter")) {
                        dialogList.add(file);
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return dialogList;
    }

    public static String GetRandomDialog(Context context)
    {
        ArrayList<String> dialogList = new ArrayList<>();

        try
        {
            String[] list;
            list = context.getAssets().list("");
            if(list.length  > 0)
            {
                for(String file : list)
                {
                    if(file.contains("chapter")) {
                        dialogList.add(file);
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(dialogList.size());
        String SeletedFileName = dialogList.get(randomIndex);

        return SeletedFileName;
    }

    public static String GetToday()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String GetYesterDay()
    {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date date = c.getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        String formattedDate = df.format(date);

        return formattedDate;
    }
}
