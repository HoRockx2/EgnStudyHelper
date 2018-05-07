package com.horockx2.egnstudyhelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogListActivity extends AppCompatActivity {

    private class DialogListViewAdapter extends SimpleAdapter
    {
        ArrayList<HashMap<String, String>> fileList;

        public DialogListViewAdapter(Context context, int layoutId, ArrayList<HashMap<String, String>> list)
        {
            super(context, list, layoutId, new String[] {"fileName", "dailyCheck"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            fileList = list;
        }

        public String GetFileName(int index)
        {
            return fileList.get(index).get("fileName");
        }
    }

    private ArrayList<DialogFileModel> dialogList =  new ArrayList<DialogFileModel>();

    private dialogFileListAdapter adapter;
    ListView listView;
    BroadcastReceiver intentReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_list);

        listView = findViewById(R.id.dialog_list_listview);

        GetDialogList();
        CreateAdapter();
        AttachAdapterToListView();
        SetOnClickEventListener();
        SetUpdateItemIntentListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(intentReceiver);
    }

    private void SetUpdateItemIntentListener()
    {
        intentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                adapter.clear();
                GetDialogList();
                for(DialogFileModel obj : dialogList)
                {
                    adapter.insert(obj, adapter.getCount());
                }

                adapter.notifyDataSetChanged();
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(intentReceiver,
                new IntentFilter("updateDialogData"));
    }

    private void SetOnClickEventListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogFileListAdapter adapter = (dialogFileListAdapter)adapterView.getAdapter();
                Log.Info(adapter.GetFileName(i));

                ShowDialogActivity(adapter.GetFileName(i));
            }
        });
    }

    private void AttachAdapterToListView() {
        listView.setAdapter(adapter);
    }

    private void CreateAdapter() {
        adapter = new dialogFileListAdapter(this, dialogList);
    }

    private void ShowDialogActivity(String dialogFileName)
    {
        Intent intent = new Intent(this, DialogActivity.class);
        intent.putExtra("dialogFileName", dialogFileName);
        startActivity(intent);
    }

    private void GetDialogList()
    {
         dialogList = DialogManager.GetDialogFileNameAndDailyCheckList(this);
    }
}
