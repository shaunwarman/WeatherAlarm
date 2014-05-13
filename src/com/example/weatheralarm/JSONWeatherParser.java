package com.example.weatheralarm;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shaunw on 9/13/13.
 */
public class JSONWeatherParser {
    Forecast forecast;

    public Weather getCurrentWeather(String json) {
        Weather current = new Weather();
        json = json.replace("\"", "");
        String [] tags = json.split(",");
        for(String eachTag : tags) {
            if(eachTag.contains("time")) {
                String jsonValue = getJsonValue("time", eachTag);
                String time = timeConverter(jsonValue);
                current.setTime(time);
            } else if (eachTag.contains("summary")) {
                current.setSummary(getJsonValue("summary", eachTag));
            } else if (eachTag.contains("precipProbability")) {
                String precip = getJsonValue("precipProbability", eachTag).concat("0");
                current.setPrecipitation(precip);
            } else if (eachTag.contains("temperature")) {
                String temperature = getJsonValue("temperature", eachTag);
                if(temperature.contains("."))
                    temperature = temperature.substring(0, temperature.indexOf("."));
                current.setTemperature(temperature);
            } else {
                // do nothing
            }
        }
        Log.d("TIME", current.getTime());
        Log.d("SUMMARY", current.getSummary());
        Log.d("PRECIP", current.getPrecipitation());
        Log.d("TEMP", current.getTemperature());
        return current;
    }

    public List<Weather> getHourlyWeather(String json) {
        List<Weather> hourly = new LinkedList<Weather>();
        int iterate = 0;
        Weather current;
        json = json.replace("\"", "");
        String[] hourlyJson = json.split("\\}");
        for(String eachHour : hourlyJson) {
            iterate++;
            if(iterate > 10)
                break;

            current = new Weather();
            String[] hourData = eachHour.split(",");
            for(String eachTag : hourData) {
                if(eachTag.contains("time")) {
                    String jsonValue = getJsonValue("time", eachTag);
                    String time = timeConverter(jsonValue);
                    current.setTime(time);
                } else if (eachTag.contains("summary")) {
                    current.setSummary(getJsonValue("summary", eachTag));
                } else if (eachTag.contains("precipProbability")) {
                    String precip = getJsonValue("precipProbability", eachTag).concat("0");
                    current.setPrecipitation(precip);
                } else if (eachTag.contains("temperature")) {
                    String temperature = getJsonValue("temperature", eachTag);
                    if(temperature.contains("."))
                        temperature = temperature.substring(0, temperature.indexOf("."));
                    current.setTemperature(temperature);
                } else {
                    // do nothing
                }
            }
            Log.d("ITERATE", String.valueOf(iterate));
            Log.d("TIME", current.getTime());
            Log.d("SUMMARY", current.getSummary());
            Log.d("PRECIP", current.getPrecipitation());
            Log.d("TEMP", current.getTemperature());
            hourly.add(current);
        }
        return hourly;
    }

    public Forecast getForecast(String json) {
        Forecast forecast = new Forecast();
        Log.d("JSON", json);

        String current = json.substring(json.indexOf("currently"), json.indexOf("minutely"));
        String hourly = json.substring(json.indexOf("hourly"), json.indexOf("flags"));

        Weather currentWeather = getCurrentWeather(current);
        List<Weather> hourlyWeather = getHourlyWeather(hourly);

        forecast.setCurrent(currentWeather);
        forecast.setHourly(hourlyWeather);

        return forecast;
    }

    public String timeConverter(String timestamp) {
        long linuxTimestamp = Long.valueOf(timestamp);
        Date date = new Date(linuxTimestamp*1000);

        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String myTime = sdf.format(date);

        return myTime;
    }

    public String getJsonValue(String value, String json) {
        String tag = json.substring(json.indexOf(value));

        return tag.substring(value.length()+1);
    }


}
