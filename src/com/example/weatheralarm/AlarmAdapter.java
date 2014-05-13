package com.example.weatheralarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.lang.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by shaun on 10/21/13.
 */
public class AlarmAdapter<String> extends ArrayAdapter<String> {
    public Context context;
    public int resource;
    public int textViewResourceId;
    public List objects;
    private WeatherDataSource dataSource;
    private List<AlarmEntry> entry;

    public AlarmAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.objects = objects;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        dataSource = new WeatherDataSource(this.context);
        dataSource.open();
        entry = dataSource.getAllWeatherEntries();

        for(AlarmEntry ae : entry)
            Log.d("AlarmAdapter", ae.getTime());

        if (v == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            v = inflater.inflate(R.layout.activity_alarm_item, parent, false);
        }
        TextView textView = (TextView) v.findViewById(R.id.time);
        textView.setText(entry.get(position).getTime());
        ToggleButton toggle = (ToggleButton) v.findViewById(R.id.alarm_status);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO Send column ID or add new column to hold ID to send in PendingIntent
                if (isChecked) {
                    Log.d("ENABLE ALARM", Integer.toString(position));
                    enableAlarm(position, (String) "enable");
                } else {
                    enableAlarm(position, (String) "disable");
                }

            }
        });
        return v;
    }

    public void enableAlarm(int position, String enableOrDisable) {
        Intent myIntent = new Intent(this.context, MyAlarmReceiver.class);
        myIntent.putExtra("id", position);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,
                0, myIntent, 0);

        AlarmManager alarmManager
                = (AlarmManager)context.getSystemService(this.context.ALARM_SERVICE);

        Calendar now = Calendar.getInstance();
        Calendar calendar = getCalendarTime(position);

        //if its in the past - increment
        if(calendar.before(now)){
            calendar.add(Calendar.DATE,1);
        }
        if(enableOrDisable.equals("enable")) {
            Log.d("Enabling alarm", "");
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), pendingIntent);
        } else if(enableOrDisable.equals("disable")) {
            Log.d("Disabling alarm", "");
            alarmManager.cancel(pendingIntent);
        }
    }


    public Calendar getCalendarTime(int position) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);

        int timeLength = entry.get(position).getTime().length();

        String hour = (String) entry.get(position).getTime().substring(0, timeLength - 6);
        String minute = (String) entry.get(position).getTime().substring(timeLength - 5, timeLength - 3);
        String meridian = (String) entry.get(position).getTime().substring(timeLength - 2, timeLength);

        int meridianInt = (meridian.equals("AM")) ? 0 : 1;

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt((java.lang.String) hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt((java.lang.String) minute));
        calendar.set(Calendar.AM_PM, meridianInt);

        return calendar;
    }

}
