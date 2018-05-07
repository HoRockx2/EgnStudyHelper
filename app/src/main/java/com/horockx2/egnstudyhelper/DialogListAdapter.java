package com.horockx2.egnstudyhelper;

import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;

public class DialogListAdapter extends ArrayAdapter<SentenceModel> {

    public DialogListAdapter(Context context, ArrayList<SentenceModel> sentences)
    {
        super(context, 0, sentences);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SentenceModel sentence = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dialog_list_adapter, parent, false);
        }

        View mainView = convertView.findViewById(R.id.sentence_row);

        ImageView speaker = convertView.findViewById(R.id.speaker);
        TextView engSentence = convertView.findViewById(R.id.engSentence);
        TextView korSentence = convertView.findViewById(R.id.korSentence);

//        speaker.setText(sentence.speaker);
        if(sentence.speaker.equals("A"))
            speaker.setImageResource(R.mipmap.green_zaku_round);
        else
            speaker.setImageResource(R.mipmap.red_zaku_round);
        engSentence.setText(sentence.engSentence);
        korSentence.setText(sentence.korSentence);

        engSentence.setAlpha(0.0f);
        engSentence.setVisibility(View.INVISIBLE);

        int backgroundColor;
//        Log.Info("What is speaker : "+ speaker.getText());

//        if(speaker.getText().equals("A"))
//        {
//            backgroundColor = Color.parseColor("#C5CAE9");
//        }
//        else if(speaker.getText().equals("B"))
//        {
//            backgroundColor = Color.parseColor("#F8BBD0");
//        }
//        else
//        {
//            backgroundColor = Color.parseColor("#BDBDBD");
//        }
//        mainView.setBackgroundColor(backgroundColor);

        return convertView;
    }
}

