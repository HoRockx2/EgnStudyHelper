package com.horockx2.egnstudyhelper;

import java.time.temporal.IsoFields;

/**
 * Created by user on 2018-04-29.
 */

public class DialogFileModel {
    public String fileName;
    public boolean isDailyChecked;
    public String dailyCheckedDate;
    public boolean isFail;

    public DialogFileModel(String fileName, String dailyCheckedDate, String isFail)
    {
        this.fileName = fileName;
        this.dailyCheckedDate = dailyCheckedDate;
        if(dailyCheckedDate.equals(DialogManager.GetToday()))
            isDailyChecked = true;
        else
            isDailyChecked = false;

        if(isFail.equals(DialogDBContract.DialogEntry.IS_FAIL_TRUE))
            this.isFail = true;
        else
            this.isFail = false;
    }
}
