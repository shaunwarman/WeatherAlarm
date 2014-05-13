package com.example.weatheralarm;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmActivity extends Activity {
    Spinner spinner;
    boolean isSunday = false;
    boolean isMonday = false;
    boolean isTuesday = false;
    boolean isWednesday = false;
    boolean isThursday = false;
    boolean isFriday = false;
    boolean isSaturday = false;
    boolean isPrecipitation = false;
    boolean isHourly = false;
    boolean isRepeat = false;

    //TODO add on or off functionality -> Maybe when set it is manually on.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        forceClick();
        String previous = getIntent().getStringExtra("previous");

        if(previous != null && previous.equals("true")) {
            String time = getIntent().getStringExtra("time");
            String monday = getIntent().getStringExtra("monday");
            String tuesday = getIntent().getStringExtra("tuesday");
            String wednesday = getIntent().getStringExtra("wednesday");
            String thursday = getIntent().getStringExtra("thursday");
            String friday = getIntent().getStringExtra("friday");
            String saturday = getIntent().getStringExtra("saturday");
            String sunday = getIntent().getStringExtra("sunday");
            String precipitation = getIntent().getStringExtra("precipitation");
            String hourly = getIntent().getStringExtra("hourly");
            String repeat = getIntent().getStringExtra("repeat");
            String meridiem = getIntent().getStringExtra("meridiem");

            if(time != null) {
                int currentHour = Integer.valueOf(time.substring(0, time.length()-6));
                if(meridiem.equals("PM")) currentHour += 12;
                if(meridiem.equals("AM") && time.substring(0, 2).equals("12")) currentHour = currentHour % 12;
                TimePicker tp = (TimePicker) findViewById(R.id.time_picker);
                tp.setCurrentHour(currentHour);
                tp.setCurrentMinute(Integer.valueOf(time.substring(time.length()-5, time.length()-3)));
            }
            initButtons(monday, tuesday, wednesday, thursday, friday, saturday, sunday, precipitation, hourly, repeat);
        }
    }

    public void initButtons(String monday, String tuesday, String wednesday, String thursday, String friday,
                                String saturday, String sunday, String precipitation, String hourly, String repeat) {
        if(monday.equals("true")) {
            Button mondayButton = (Button) findViewById(R.id.monday);
            mondayButton.performClick();
        }
        if(tuesday.equals("true")) {
            Button tuesdayButton = (Button) findViewById(R.id.tuesday);
            tuesdayButton.performClick();
        }
        if(wednesday.equals("true")) {
            Button wednesdayButton = (Button) findViewById(R.id.wednesday);
            wednesdayButton.performClick();
        }
        if(thursday.equals("true")) {
            Button thursdayButton = (Button) findViewById(R.id.thursday);
            thursdayButton.performClick();
        }
        if(friday.equals("true")) {
            Button fridayButton = (Button) findViewById(R.id.friday);
            fridayButton.performClick();
        }
        if(saturday.equals("true")) {
            Button saturdayButton = (Button) findViewById(R.id.saturday);
            saturdayButton.performClick();
        }
        if(sunday.equals("true")) {
            Button sundayButton = (Button) findViewById(R.id.sunday);
            sundayButton.performClick();
        }
        if(precipitation.equals("true")) {
            Button precipitationButton = (Button) findViewById(R.id.precipitation_checkbox);
            precipitationButton.performClick();
        }
        if(hourly.equals("true")) {
            Button hourlyButton = (Button) findViewById(R.id.hourly_checkbox);
            hourlyButton.performClick();
        }
        if(repeat.equals("true")) {
            Button repeatButton = (Button) findViewById(R.id.repeat_checkbox);
            repeatButton.performClick();
        }
    }

    // work around to set week day buttons to resize correctly
    public void forceClick() {
        Button sundayButton = (Button) findViewById(R.id.sunday);
        Button mondayButton = (Button) findViewById(R.id.monday);
        Button tuesdayButton = (Button) findViewById(R.id.tuesday);
        Button wednesdayButton = (Button) findViewById(R.id.wednesday);
        Button thursdayButton = (Button) findViewById(R.id.thursday);
        Button fridayButton = (Button) findViewById(R.id.friday);
        Button saturdayButton = (Button) findViewById(R.id.saturday);
        sundayButton.performClick();
        sundayButton.performClick();
        mondayButton.performClick();
        mondayButton.performClick();
        tuesdayButton.performClick();
        tuesdayButton.performClick();
        wednesdayButton.performClick();
        wednesdayButton.performClick();
        thursdayButton.performClick();
        thursdayButton.performClick();
        fridayButton.performClick();
        fridayButton.performClick();
        saturdayButton.performClick();
        saturdayButton.performClick();
    }



    public void cancelAlarm(View view) {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        super.finish();
    }

    public void setAlarm(View view) {
        StringBuilder time = new StringBuilder();
        TimePicker tp = (TimePicker) findViewById(R.id.time_picker);
        Integer hour = tp.getCurrentHour();
        Integer minute = tp.getCurrentMinute();

        String meridiem = (hour < 12) ? "AM" : "PM";

        if(hour == 12)
            meridiem = "PM";

        if(hour == 0)
            hour = 12;

        if(hour > 12)
            hour = hour % 12;

        String str_minute = convertMinute(minute);
        String str_hour = hour.toString();
        time.append(str_hour + ":" + str_minute + " " + meridiem);
        String monday = isMonday ? "true" : "false";
        String tuesday = isTuesday ? "true" : "false";
        String wednesday = isWednesday ? "true" : "false";
        String thursday = isThursday ? "true" : "false";
        String friday = isFriday ? "true" : "false";
        String saturday = isSaturday ? "true" : "false";
        String sunday = isSunday ? "true" : "false";
        String precipitation = isPrecipitation ? "true" : "false";
        String hourly = isHourly ? "true" : "false";
        String repeat = isRepeat ? "true" : "false";
        Toast.makeText(getBaseContext(), "Alarm created: " + time.toString(), Toast.LENGTH_SHORT).show();
        addAlarmEntry(time.toString(), monday, tuesday, wednesday, thursday,friday, saturday, sunday, precipitation, hourly, repeat, meridiem);
    }

    public void addAlarmEntry(String time, String monday, String tuesday, String wednesday, String thursday,
                               String friday, String saturday, String sunday, String precipitation, String hourly, String repeat, String meridiem) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("time",time);
        returnIntent.putExtra("monday", monday);
        returnIntent.putExtra("tuesday", tuesday);
        returnIntent.putExtra("wednesday", wednesday);
        returnIntent.putExtra("thursday", thursday);
        returnIntent.putExtra("friday", friday);
        returnIntent.putExtra("saturday", saturday);
        returnIntent.putExtra("sunday", sunday);
        returnIntent.putExtra("precipitation", precipitation);
        returnIntent.putExtra("hourly", hourly);
        returnIntent.putExtra("repeat", repeat);
        returnIntent.putExtra("meridiem", meridiem);
        setResult(RESULT_OK,returnIntent);
        super.finish();
    }


    public String convertMinute(Integer minute) {
        String str_minute = (minute < 10) ? "0"+minute.toString() : minute.toString();
        return str_minute;
    }

    public void setSunday(View view) {
        Button sundayButton = (Button)findViewById(R.id.sunday);
        isSunday = (isSunday) ? false : true;

        if(isSunday) {
            sundayButton.setBackgroundColor(Color.parseColor("#CAEBF8"));
        } else {
            sundayButton.setBackgroundColor(Color.LTGRAY);
        }
    }

    public void setMonday(View view) {
        Button mondayButton = (Button)findViewById(R.id.monday);
        isMonday = (isMonday) ? false : true;

        if(isMonday) {
            mondayButton.setBackgroundColor(Color.parseColor("#CAEBF8"));
        } else {
            mondayButton.setBackgroundColor(Color.LTGRAY);
        }
    }

    public void setTuesday(View view) {
        Button tuesdayButton = (Button)findViewById(R.id.tuesday);
        isTuesday = (isTuesday) ? false : true;

        if(isTuesday) {
            tuesdayButton.setBackgroundColor(Color.parseColor("#CAEBF8"));
        } else {
            tuesdayButton.setBackgroundColor(Color.LTGRAY);
        }
    }

    public void setWednesday(View view) {
        Button wednesdayButton = (Button)findViewById(R.id.wednesday);
        isWednesday = (isWednesday) ? false : true;

        if(isWednesday) {
            wednesdayButton.setBackgroundColor(Color.parseColor("#CAEBF8"));
        } else {
            wednesdayButton.setBackgroundColor(Color.LTGRAY);
        }
    }

    public void setThursday(View view) {
        Button thursdayButton = (Button)findViewById(R.id.thursday);
        isThursday = (isThursday) ? false : true;

        if(isThursday) {
            thursdayButton.setBackgroundColor(Color.parseColor("#CAEBF8"));
        } else {
            thursdayButton.setBackgroundColor(Color.LTGRAY);
        }
    }

    public void setFriday(View view) {
        Button fridayButton = (Button)findViewById(R.id.friday);
        isFriday = (isFriday) ? false : true;

        if(isFriday) {
            fridayButton.setBackgroundColor(Color.parseColor("#CAEBF8"));
        } else {
            fridayButton.setBackgroundColor(Color.LTGRAY);
        }
    }

    public void setSaturday(View view) {
        Button saturdayButton = (Button)findViewById(R.id.saturday);
        isSaturday = (isSaturday) ? false : true;

        if(isSaturday) {
            saturdayButton.setBackgroundColor(Color.parseColor("#CAEBF8"));
        } else {
            saturdayButton.setBackgroundColor(Color.LTGRAY);
        }
    }

    public void setPrecipitation(View view) {
        Button precipitationButton = (Button) findViewById(R.id.precipitation_checkbox);
        isPrecipitation = (isPrecipitation) ? false : true;
    }

    public void setHourly(View view) {
        Button hourlyButton = (Button) findViewById(R.id.hourly_checkbox);
        isHourly = (isHourly) ? false : true;
    }

    public void setRepeat(View view) {
        Button repeatButton = (Button) findViewById(R.id.repeat_checkbox);
        isRepeat = (isRepeat) ? false : true;
    }

}

