package com.example.weatheralarm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by shaunw on 9/25/13.
 */
public class AlarmAlertActivity extends Activity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    Button buttonDismiss;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_scheduledactivity);
        init();
        String location = getCurrentLocation();

        startWebServiceTask("https://api.forecast.io/forecast/080b269a155a3e2253046ee40bd6ba86/"+location);
    }

    public void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void init() {
        tts = new TextToSpeech(this, this);

        buttonDismiss = (Button)findViewById(R.id.dismiss);
        buttonDismiss.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                tts.stop();
                finish();
            }});

        locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
            }

            public void onProviderDisabled(String provider) {
            }

            public void onProviderEnabled(String provider) {}

            public void onStatusChanged(String provider,int status,Bundle extras){}
        };
    }


    // use google api to get current lat/long used in forecast call
    public String getCurrentLocation() {
        LocationManager locManager;
        double latitude;
        double longitude;
        StringBuilder sb = new StringBuilder();
        locManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,
                500.0f, locationListener);
        Location location = locManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            sb.append(String.valueOf(latitude) + "," + String.valueOf(longitude));
        }

        return sb.toString();
    }

    // used to initialize text to speech preferences
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    // start web service to get weather data and parse html
    private void startWebServiceTask(String url) {
        WebServiceAsyncTask webServiceTask = new WebServiceAsyncTask();
        webServiceTask.execute(url);
    }


class WebServiceAsyncTask extends AsyncTask<String, Void, Forecast> {

    @Override
    protected void onPreExecute() {}

    @Override
    protected Forecast doInBackground(String... params) {
        String weather = grabWeather(params[0]);
    //    Log.d("Weather", weather);
        Forecast forecast = parseHTML(weather);

        return forecast;
    }

    @Override
    protected void onPostExecute(Forecast forecast) {
        StringBuilder sb = new StringBuilder();

        sb.append(correlateWeather(forecast));
        speakOut(sb.toString());
    }

    // grab the news specified by the news section in the drop down list
    public String grabWeather(String url) {
        String weather = null;

        try {
            Log.d("GRAB WEATHER", "BEFORE CONNECTION");
            Log.d("URL", url);
            URL weatherURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) weatherURL.openConnection();
            InputStream in = new BufferedInputStream(con.getInputStream());
            weather = readStream(in);
            Log.d("GRAB WEATHER", weather);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weather;
    }

    public String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    public Forecast parseHTML(String json) {
        JSONWeatherParser jsonWeatherParser = new JSONWeatherParser();

        Forecast forecast = jsonWeatherParser.getForecast(json);

        return forecast;
    }

    // run some correlation on the 10 hours of weather brought in to see important data points.
    public String correlateWeather(Forecast forecast) {
        StringBuilder finalReport = new StringBuilder();


        finalReport.append("Good day, ");
        finalReport.append(getCurrentWeather(forecast));

        Log.d("Low temp", getLowestTemp(forecast));
        Log.d("High temp", getHighestTemp(forecast));


        finalReport.append("With a low of " + getLowestTemp(forecast));
        finalReport.append("and a high of " + getHighestTemp(forecast));

        return finalReport.toString();
    }

    public String getCurrentWeather(Forecast forecast) {
        StringBuilder sb = new StringBuilder();

        sb.append(" The current weather at " + forecast.getCurrent().getTime());
        sb.append(" is " + forecast.getCurrent().getTemperature() + " degrees");
        sb.append(" with a " + forecast.getCurrent().getPrecipitation() + " percent chance of precipitation.");
        sb.append(" It looks to be " + forecast.getCurrent().getSummary() + ", ");

        Log.d("TEMP", forecast.getCurrent().getTemperature());
        Log.d("PRECIPITATION", forecast.getCurrent().getTemperature());


        return sb.toString();
    }

    public String getHighestTemp(Forecast forecast) {
        List<Weather> weather = forecast.getHourly();
        int high = 0;

        for(int index = 0; index < weather.size()-1; index++) {
            int temp = Integer.valueOf(weather.get(index).getTemperature());
            if(temp > high) {
                high = temp;
            }
        }

        return String.valueOf(high);
    }


    public String getLowestTemp(Forecast forecast) {
        List<Weather> weather = forecast.getHourly();
        int low = 100;

        for(int index = 0; index < weather.size()-1; index++) {
            int temp = Integer.valueOf(weather.get(index).getTemperature());
            if(temp < low) {
                low = temp;
            }
        }

        return String.valueOf(low);
    }

    public String getHighestTempTime(Forecast forecast) {
        List<Weather> weather = forecast.getHourly();
        int high = 0;
        String time = "";

        for(int index = 0; index < weather.size()-1; index++) {
            int temp = Integer.valueOf(weather.get(index).getTemperature());
            if(temp > high) {
                high = temp;
                time = weather.get(index).getTime();
            }
        }

        return time;
    }

    public String getLowestTempTime(Forecast forecast) {
        List<Weather> weather = forecast.getHourly();
        int low = 100;
        String time = "";

        for(int index = 0; index < weather.size()-1; index++) {
            int temp = Integer.valueOf(weather.get(index).getTemperature());
            if(temp < low) {
                low = temp;
                time = weather.get(index).getTime();
            }
        }

        return time;
    }

    public String getBiggestChange(Forecast forecast) {
        int lowestTemp = Integer.valueOf(getLowestTemp(forecast));
        int highestTemp = Integer.valueOf(getHighestTemp(forecast));

        int biggestChange = highestTemp - lowestTemp;

        return String.valueOf(biggestChange);
    }


    public String getHighestPrecChange(Forecast forecast) {
        int highestChance = 0;
        List<Weather> weather = forecast.getHourly();

        for(int index=0; index<weather.size();index++) {
            int temp = Integer.valueOf(weather.get(index).getPrecipitation());
            if(temp > highestChance) {
                highestChance = temp;
            }
        }

        return String.valueOf(highestChance);
    }

    public String getLowestPrecChance(Forecast forecast) {
        int lowestChance = 100;
        List<Weather> weather = forecast.getHourly();

        for(int index=0;index<weather.size();index++) {
            int temp = Integer.valueOf(weather.get(index).getPrecipitation());
            if(temp < lowestChance) {
                lowestChance = temp;
            }
        }

        return String.valueOf(lowestChance);
    }

    //TODO add change of highest and lowest temp
    public String getBiggestTempChange(Forecast forecast) {
        int highest = Integer.valueOf(getHighestTemp(forecast));
        int lowest = Integer.valueOf(getLowestTemp(forecast));

        int change = (highest - lowest);

        return String.valueOf(change);
    }


}

}
