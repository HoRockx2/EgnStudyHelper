package com.horockx2.egnstudyhelper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DialogActivity extends AppCompatActivity {

    private int dialogChapter;
    private String dialogFileName;

    JSONObject rootObj;
    ListView dialogListView;

    ArrayList<SentenceModel> sentenceList;
    DialogListAdapter adapter;

    private final int numberOfSentence = 6;
    private boolean[] isOpened;

    Button successBtn;
    Button failBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Init();
        FillSentenceList();
        CreateAdapter();
        AttachAdapterToListView();

        UpdateActionBarText();

        Log.Info("dialogChapter : " + dialogChapter);
        Log.Info("dialogFileName : " + dialogFileName);
    }

    private void UpdateActionBarText()
    {
        if(dialogChapter != -1)
        {
            getSupportActionBar().setTitle("Dialog" + dialogChapter);
        }
        else if(dialogFileName != null)
        {
            getSupportActionBar().setTitle(dialogFileName);
        }
    }

    private void Init()
    {
        isOpened = new boolean[numberOfSentence];
        Arrays.fill(isOpened, false);

        successBtn = findViewById(R.id.SuccessBtn);
        failBtn = findViewById(R.id.FailBtn);

//        successBtn.setAlpha(0);
//        failBtn .setAlpha(0);

        Intent thisIntent = getIntent();

        this.dialogChapter = thisIntent.getIntExtra("dialogChapter", -1);
        dialogFileName = thisIntent.getStringExtra("dialogFileName");

        Log.Info("dialogfilename in intent : " + thisIntent.getStringExtra("dialogFileName"));

        sentenceList = new ArrayList<SentenceModel>();
        dialogListView = findViewById(R.id.dialog_listview);

        dialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                Log.Info("i is : " + i);

                isOpened[i] = true;
                final TextView engSentence =  view.findViewById(R.id.engSentence);

                if(engSentence.getVisibility() == View.INVISIBLE)
                {
                    engSentence.animate().alpha(1.0f).setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    engSentence.setVisibility(View.VISIBLE);
                                }
                            });
                }
                else
                {
                    engSentence.animate().alpha(0.0f).setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    engSentence.setVisibility(View.INVISIBLE);
                                }
                            });
                }

                boolean isAllSentenceOpened = true;

                for(boolean b : isOpened)
                {
                    if(b == false)
                    {
                        isAllSentenceOpened = false;
                        break;
                    }
                }

//                if(isAllSentenceOpened)
//                {
//                    successBtn.animate().alpha(1.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            successBtn.setVisibility(View.VISIBLE);
//                        }
//                    });
//
//                    failBtn.animate().alpha(1.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            failBtn.setVisibility(View.VISIBLE);
//                        }
//                    });
//                }
            }
        });
    }

    private void AttachAdapterToListView()
    {
        dialogListView.setAdapter(adapter);
    }

    private void CreateAdapter()
    {
        adapter = new DialogListAdapter(this, sentenceList);
    }

    private void FillSentenceList()
    {
        try
        {
            rootObj = new JSONObject(loadJSONFromAsset());
            for(int i = 1; i <= 6; i++)
            {
                JSONObject sentenceData = rootObj.getJSONObject(Integer.toString(i));
                SentenceModel sentenceModel = new SentenceModel(sentenceData);

                sentenceList.add(sentenceModel);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        String jsonFileName;
        try {
            if(dialogChapter != -1)
            {
                jsonFileName = "chapter_" + dialogChapter +".json";
            }
            else if(dialogFileName != null)
            {
                jsonFileName = dialogFileName;
            }
            else
            {
                throw new IOException();
            }
            InputStream is = getAssets().open(jsonFileName);
            
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

    public void OnClickSuccess(View view) {
        String currentTime = DialogManager.GetToday();

        DialogDbHelper dbHelper = new DialogDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "update " + DialogDBContract.DialogEntry.TABLE_NAME + " set "
                + DialogDBContract.DialogEntry.COLUMN_NAME_DAILY_CHECK_DAY + " = '"
                + currentTime + "' where " + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME
                + " = '" + dialogFileName +"';";
        Log.Info(sql);
        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();

        InvokeUpdateListView();
    }

    public void OnClickFail(View view) {
        DialogDbHelper dbHelper = new DialogDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "update " + DialogDBContract.DialogEntry.TABLE_NAME + " set "
                + DialogDBContract.DialogEntry.COLUMN_NAME_IS_FAIL + " = '"
                + DialogDBContract.DialogEntry.IS_FAIL_TRUE + "' where " + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME
                + " = '" + dialogFileName + "';";

        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();

        InvokeUpdateListView();
    }

    public void OnClickUnCheckFail(View view) {
        DialogDbHelper dbHelper = new DialogDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "update " + DialogDBContract.DialogEntry.TABLE_NAME + " set "
                + DialogDBContract.DialogEntry.COLUMN_NAME_IS_FAIL + " = '"
                + DialogDBContract.DialogEntry.IS_FAIL_FALSE + "' where " + DialogDBContract.DialogEntry.COLUMN_NAME_FILE_NAME
                + " = '" + dialogFileName + "';";

        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();

        InvokeUpdateListView();
    }

    private void InvokeUpdateListView()
    {
        Intent intent = new Intent("updateDialogData");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
