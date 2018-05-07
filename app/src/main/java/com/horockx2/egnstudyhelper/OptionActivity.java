package com.horockx2.egnstudyhelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class OptionActivity extends AppCompatActivity {

    private Switch alarmSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        alarmSwitch = findViewById(R.id.alarm_switch);
        if(CheckAlarmExist())
        {
            alarmSwitch.setChecked(true);
        }

        SetSwitchListener();
    }

    private void SetSwitchListener()
    {
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    SetAlarm();
                }
                else
                {
                    UnsetAlarm();
                }
            }
        });
    }

    private Boolean CheckAlarmExist()
    {
        Intent intent = GetIntentForAlarm();
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_NO_CREATE);

        Boolean isAlarmUp = sender != null;

        if(isAlarmUp)
        {
            ShowToast("alarm exist");
        }
        else
        {
            ShowToast("alarm not exist");
        }

        return isAlarmUp;
    }

    private void SetAlarm()
    {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = GetIntentForAlarm();

        PendingIntent sender = PendingIntent.getBroadcast
                (this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar curCal = Calendar.getInstance();
        curCal.setTimeInMillis(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, curCal.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, curCal.get(Calendar.MINUTE) + 1);

//        ShowToast(calendar.toString());

        EditText alarmInterval = findViewById(R.id.alarm_interval);

        int minute = Integer.parseInt(alarmInterval.getText().toString());

        //1800000L
        long intervalTime = 60000L * minute;

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + intervalTime ,
                intervalTime, sender);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sen1der);

        ShowToast("Set alarm. It'll be fire in every " + intervalTime + "m");
    }

    private void UnsetAlarm()
    {
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent intent = GetIntentForAlarm();
        PendingIntent sender = PendingIntent.getBroadcast(this, 0,intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if(null != sender)
        {
            am.cancel(sender);
            sender.cancel();
        }

        ShowToast("Unset alarm");
    }

    private Intent GetIntentForAlarm()
    {
        Intent intent = new Intent(this, AlarmReceiver.class);

        return intent;
    }

    private void ShowToast(String msg)
    {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void OnClickDebugBtn(View view) {

        CheckAlarmExist();
    }
}
