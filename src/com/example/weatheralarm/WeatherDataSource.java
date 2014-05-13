package com.example.weatheralarm;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class WeatherDataSource {
    MySQLHelper dbhelper;
    SQLiteDatabase db;
    
    String[] cols = {MySQLHelper.ID_COL, MySQLHelper.TIME, MySQLHelper.SUNDAY, MySQLHelper.MONDAY, MySQLHelper.TUESDAY, MySQLHelper.WEDNESDAY,
            MySQLHelper.THURSDAY, MySQLHelper.FRIDAY, MySQLHelper.SATURDAY,
            MySQLHelper.PRECIPITATION, MySQLHelper.HOURLY, MySQLHelper.REPEAT, MySQLHelper.MERIDIEM,
            MySQLHelper.ON_OR_OFF};

    public WeatherDataSource(Context c) {
        dbhelper = new MySQLHelper(c);
    }
    
    public void open() throws SQLException{
         db = dbhelper.getWritableDatabase();
    }
    
    public void close(){
        dbhelper.close();
    }
    
    public void addWeatherEntry(AlarmEntry alarmEntry){
        ContentValues v = new ContentValues();

        v.put(MySQLHelper.TIME, alarmEntry.getTime());
        v.put(MySQLHelper.SUNDAY, alarmEntry.getIsSunday());
        v.put(MySQLHelper.MONDAY, alarmEntry.getIsMonday());
        v.put(MySQLHelper.TUESDAY, alarmEntry.getIsTuesday());
        v.put(MySQLHelper.WEDNESDAY, alarmEntry.getIsWednesday());
        v.put(MySQLHelper.THURSDAY, alarmEntry.getIsThursday());
        v.put(MySQLHelper.FRIDAY, alarmEntry.getIsFriday());
        v.put(MySQLHelper.SATURDAY, alarmEntry.getIsSaturday());
        v.put(MySQLHelper.PRECIPITATION, alarmEntry.getIsPrecipitation());
        v.put(MySQLHelper.HOURLY, alarmEntry.getIsHourly());
        v.put(MySQLHelper.REPEAT, alarmEntry.getIsRepeat());
        v.put(MySQLHelper.MERIDIEM, alarmEntry.getMeridiem());
        v.put(MySQLHelper.ON_OR_OFF, alarmEntry.getOnOrOff());
        
        long insertId = db.insert(MySQLHelper.TABLE_NAME, null, v);
        
        Cursor cursor = db.query(MySQLHelper.TABLE_NAME,
                cols, MySQLHelper.ID_COL + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        AlarmEntry newEntry = cursorToEntry(cursor);
        cursor.close();
    }
    
    public void deleteAlarmEntry(AlarmEntry ae){
    	long id = ae.getPos();
        db.delete(MySQLHelper.TABLE_NAME, MySQLHelper.ID_COL + "='" + id + "'", null);
    }
    
    public List<AlarmEntry> getAllWeatherEntries(){
        List<AlarmEntry> weatherEntry = new ArrayList<AlarmEntry>();
        
        Cursor cursor = db.query(MySQLHelper.TABLE_NAME, cols, null, null, null, null, null);
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	AlarmEntry alarmEntry = cursorToEntry(cursor);
            weatherEntry.add(alarmEntry);
            cursor.moveToNext();
        }
        cursor.close();

        return weatherEntry;
    }
    

    private AlarmEntry cursorToEntry(Cursor cursor) {
    	AlarmEntry alarmEntry = new AlarmEntry();

        alarmEntry.setPos(cursor.getLong(0));
        alarmEntry.setTime(cursor.getString(1));
        alarmEntry.setIsSunday(cursor.getString(2));
        alarmEntry.setIsMonday(cursor.getString(3));
        alarmEntry.setIsTuesday(cursor.getString(4));
        alarmEntry.setIsWednesday(cursor.getString(5));
        alarmEntry.setIsThursday(cursor.getString(6));
        alarmEntry.setIsFriday(cursor.getString(7));
        alarmEntry.setIsSaturday(cursor.getString(8));
        alarmEntry.setIsPrecipitation(cursor.getString(9));
        alarmEntry.setIsHourly(cursor.getString(10));
        alarmEntry.setIsRepeat(cursor.getString(11));
        alarmEntry.setMeridiem(cursor.getString(12));
        alarmEntry.setOnOrOff(cursor.getString(13));

        return alarmEntry;
    }
    
}