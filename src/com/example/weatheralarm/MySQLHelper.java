package com.example.weatheralarm;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLHelper extends SQLiteOpenHelper{
    
	
    public static final String TABLE_NAME = "alarm_entries";
    
    public static final String ID_COL = "Alarm_id";
    public static final String TIME = "Time";
    public static final String MERIDIEM = "Meridiem";
    public static final String SUNDAY = "Sunday";
    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";
    public static final String PRECIPITATION = "Precipitation";
    public static final String HOURLY = "Hourly";
    public static final String REPEAT = "Repeat";
    public static final String ON_OR_OFF = "on_or_off";


    private static final int DB_VERSION = 1;
    
    private static final String DB_NAME = "alarm.db";
    private static final String DB_CREATE =
      "create table "+ TABLE_NAME + "("
        + ID_COL + " integer primary key autoincrement, "
        + TIME + " text, "
        + SUNDAY + " integer, "
        + MONDAY + " integer, "
        + TUESDAY + " integer, "
        + WEDNESDAY + " integer, "
        + THURSDAY + " integer, "
        + FRIDAY + " integer, "
        + SATURDAY + " integer, "
        + PRECIPITATION + " integer, "
        + HOURLY + " integer, "
        + REPEAT + " integer, "
        + MERIDIEM + " integer, "
        + ON_OR_OFF + " integer);";

    public MySQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    
    

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }
    
}
