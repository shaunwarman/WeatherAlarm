package com.example.weatheralarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;

import android.content.Context;
import android.content.DialogInterface;

import android.media.AudioManager;

import android.os.Bundle;

import android.app.Activity;

import android.content.Intent;

import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;

//TODO Need to add volume to database and have the volume set from UI.
//TODO Need to add ON/OFF for alarm so the toggle button is set on startup.
//TODO Should be able to place that right in between the set buttons and the checkboxes


// forecast io - apikey: 080b269a155a3e2253046ee40bd6ba86
// https://api.forecast.io/forecast/080b269a155a3e2253046ee40bd6ba86/LATITUDE,LONGITUDE
public class MainActivity extends Activity{
    private ArrayList<String> alarmItemArray;
    private ArrayList<AlarmEntry> alarmEntries;
    private MyListView lv;
    private ArrayAdapter<String> alarmAdapter;
    private AlarmAdapter<String> alarmAdapterString;
    private WeatherDataSource dataSource;
    private SeekBar seekBar;
    private CharSequence options[] = new CharSequence[] {"Edit", "Delete", "Cancel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    // set listview for opening screen
    public void init() {
        dataSource = new WeatherDataSource(this);
        dataSource.open();

        setDefaultVolume(3);

        alarmEntries = (ArrayList<AlarmEntry>) dataSource.getAllWeatherEntries();
        for(AlarmEntry ae : alarmEntries)
            Log.d("MainActivity", ae.getTime());

        alarmItemArray = getTimeFromEntries(alarmEntries);

        // Instantiate custom alarm adapter with alarm array with time from entries
        alarmAdapterString = new AlarmAdapter<String>(this,
                R.layout.activity_alarm_item, R.id.time, alarmItemArray);

        lv = (MyListView) findViewById(R.id.listview);
        lv.setAdapter(alarmAdapterString);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // long click show options menu with choice to: Edit, Delete, Cancel
        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                builder.setTitle("Options");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0:
                                Intent intent = getAlarmEntriesFromDataSource(position);
                                intent.putExtra("previous", "true");
                                startActivityForResult(intent, 1);
                                break;
                            case 1:
                                //TODO   Make sure to check if alarm is on and if so
                                //TODO          remove from Alarm Manager
                                // Look in the database to see if there is a ON/OFF flag
                                removeItem(position);
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            } });

        // get alarm information from datasource and if it was an already created alarm
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int position,
                                    long id) {
                Intent intent = getAlarmEntriesFromDataSource(position);
                //TODO Either delete on a new set and create or somehow just edit - if previous
                intent.putExtra("previous", "true");
                startActivityForResult(intent, 1);
            }

        });
    }

    // Get alarm information from database at position that was clicked
    public Intent getAlarmEntriesFromDataSource(int position) {
        Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
        List<AlarmEntry> entries = dataSource.getAllWeatherEntries();
        String time = entries.get(position).getTime();
        String monday = entries.get(position).getIsMonday();
        String tuesday = entries.get(position).getIsTuesday();
        String wednesday = entries.get(position).getIsWednesday();
        String thursday = entries.get(position).getIsThursday();
        String friday = entries.get(position).getIsFriday();
        String saturday = entries.get(position).getIsSaturday();
        String sunday = entries.get(position).getIsSunday();
        String precipitation = entries.get(position).getIsPrecipitation();
        String hourly = entries.get(position).getIsHourly();
        String repeat = entries.get(position).getIsRepeat();
        String meridiem = entries.get(position).getMeridiem();
        String on_or_off = entries.get(position).getOnOrOff();

        intent.putExtra("time", time);
        intent.putExtra("monday", monday);
        intent.putExtra("tuesday", tuesday);
        intent.putExtra("wednesday", wednesday);
        intent.putExtra("thursday", thursday);
        intent.putExtra("friday", friday);
        intent.putExtra("saturday", saturday);
        intent.putExtra("sunday", sunday);
        intent.putExtra("precipitation", precipitation);
        intent.putExtra("hourly", hourly);
        intent.putExtra("repeat", repeat);
        intent.putExtra("meridiem", meridiem);
        intent.putExtra("on_or_off", on_or_off);

        return intent;
    }

    // set the alarm volume
    public void setDefaultVolume(int volume) {
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    }

    // get the time from all the entries provided in the array list
    public ArrayList<String> getTimeFromEntries(ArrayList<AlarmEntry> ae) {
        ArrayList<String> timeFromAlarm = new ArrayList<String>();

        for(AlarmEntry each : ae) {
            timeFromAlarm.add(each.getTime());
        }

        return timeFromAlarm;
    }


    // show the alarm activity to set preferences
    public void showAlarm(View view) {
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivityForResult(intent, 1);
    }

    // returned information from alarm activity when alarm is set
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == 1) {
                if(resultCode == RESULT_OK){
                    String time=data.getStringExtra("time");
                    String monday=data.getStringExtra("monday");
                    String tuesday=data.getStringExtra("tuesday");
                    String wednesday=data.getStringExtra("wednesday");
                    String thursday=data.getStringExtra("thursday");
                    String friday=data.getStringExtra("friday");
                    String saturday=data.getStringExtra("saturday");
                    String sunday=data.getStringExtra("sunday");
                    String precipitation=data.getStringExtra("precipitation");
                    String hourly=data.getStringExtra("hourly");
                    String repeat=data.getStringExtra("repeat");
                    String meridiem=data.getStringExtra("meridiem");
                    String on_or_off=data.getStringExtra("on_or_off");
                    addItem(time, monday, tuesday, wednesday, thursday, friday,
                            saturday, sunday, precipitation, hourly, repeat,
                            meridiem, on_or_off);
                }
            }
        }
    }

    // remove alarm item
    public void removeItem(int position) {
        AlarmEntry alarmEntry = alarmEntries.get(position);
        alarmEntries.remove(position);
        alarmItemArray.remove(position);
        dataSource.deleteAlarmEntry(alarmEntry);
        alarmAdapterString.notifyDataSetChanged();
    }

    // add alarm item
    public void addItem(String time, String monday, String tuesday, String wednesday,
                        String thursday, String friday, String saturday, String sunday,
                        String precipitation, String hourly, String repeat, String meridiem,
                        String on_or_off) {
        AlarmEntry ae = new AlarmEntry();

        ae.setTime(time);
        ae.setIsMonday(monday);
        ae.setIsTuesday(tuesday);
        ae.setIsWednesday(wednesday);
        ae.setIsThursday(thursday);
        ae.setIsFriday(friday);
        ae.setIsSaturday(saturday);
        ae.setIsSunday(sunday);
        ae.setIsPrecipitation(precipitation);
        ae.setIsHourly(hourly);
        ae.setIsRepeat(repeat);
        ae.setMeridiem(meridiem);
        ae.setOnOrOff(on_or_off);

        alarmItemArray.add(time);
        alarmEntries.add(ae);
        dataSource.addWeatherEntry(ae);
        alarmAdapterString.notifyDataSetChanged();
    }


    public AlarmAdapter<String> getAlarmAdapterString() {
        return this.alarmAdapterString;
    }



}



