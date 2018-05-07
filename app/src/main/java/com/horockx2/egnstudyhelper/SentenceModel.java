package com.horockx2.egnstudyhelper;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by user on 2018-02-08.
 */

public class SentenceModel {
    public String speaker;
    public String engSentence;
    public String korSentence;

    public SentenceModel(JSONObject sentenceData)
    {
        try {
            speaker = sentenceData.getString("speaker");
            JSONArray detailDialog = sentenceData.getJSONArray("sentence");
            engSentence = detailDialog.getString(0);
            korSentence = detailDialog.getString(1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
