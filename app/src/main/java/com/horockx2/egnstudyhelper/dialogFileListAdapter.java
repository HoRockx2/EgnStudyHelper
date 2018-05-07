package com.horockx2.egnstudyhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FilenameFilter;
import java.util.ArrayList;

public class dialogFileListAdapter extends ArrayAdapter<DialogFileModel> {

    public dialogFileListAdapter(Context context, ArrayList<DialogFileModel> files)
    {
        super(context, 0, files);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DialogFileModel file = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dialog_file_list_adapter,
                    parent, false);
        }

        ImageView checkMark = convertView.findViewById(R.id.DailyCheck);
        TextView fileName = convertView.findViewById(R.id.FileName);
        TextView lastCheckedDate = convertView.findViewById(R.id.last_checked_date);

        fileName.setText(file.fileName);

        if(file.isFail)
        {
            checkMark.setImageResource(R.mipmap.exclamation_mark);
            checkMark.setVisibility(View.VISIBLE);
        }
        else
        {
            checkMark.setImageResource(R.mipmap.check_mark);

            if(file.isDailyChecked) {
                lastCheckedDate.setVisibility(View.GONE);
                checkMark.setVisibility(View.VISIBLE);
            }
            else
            {
                checkMark.setVisibility(View.GONE);
                lastCheckedDate.setVisibility(View.VISIBLE);
                String lastDate;
                if(file.dailyCheckedDate.isEmpty())
                    lastDate = "N/A";
                else
                    lastDate = file.dailyCheckedDate;

                lastCheckedDate.setText(lastDate);
            }
        }


        return convertView;
    }

    public String GetFileName(int i)
    {
        DialogFileModel file = getItem(i);
        return file.fileName;
    }
}
