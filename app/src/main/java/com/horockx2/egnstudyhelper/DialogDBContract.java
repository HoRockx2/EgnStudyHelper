package com.horockx2.egnstudyhelper;

import android.provider.BaseColumns;

/**
 * Created by user on 2018-04-29.
 */

public final class DialogDBContract {
    private DialogDBContract() {};

    public static class DialogEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "dialog";

        public static final String COLUMN_NAME_FILE_NAME = "filename";
        public static final String COLUMN_NAME_DIALOG = "dialogjson";
        public static final String COLUMN_NAME_DAILY_CHECK_DAY = "dailycheckday";
        public static final String COLUMN_NAME_IS_FAIL = "isfail";

        public static final String IS_FAIL_TRUE = "TRUE";
        public static final String IS_FAIL_FALSE = "FALSE";
    }
}





