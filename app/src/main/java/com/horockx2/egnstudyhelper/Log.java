package com.horockx2.egnstudyhelper;
/**
 * Created by user on 2018-01-29.
 */

public class Log {

    public static void Start()
    {
        android.util.Log.i("EngStudyHelper", "[Start]");
    }

    public static void Start(String msg)
    {
        android.util.Log.i("EngStudyHelper", "[Start]" + msg);
    }

    public static void Info(String msg)
    {
        android.util.Log.i("EngStudyHelper", msg);
    }
}
