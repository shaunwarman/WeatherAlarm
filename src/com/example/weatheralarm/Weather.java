package com.example.weatheralarm;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

// api key: hnyu5dvxk77vm2qj3gfkmjrz
// Example URL: http://api.wunderground.com/api/Your_Key/forecast/geolookup/conditions/q/CA/San_Francisco.json
public class Weather {
    private String time;
    private String summary;
	private String temperature;
	private String precipitation;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTemperature(String temp) {
		this.temperature = temp;
	}
	
	public String getTemperature() {
		return this.temperature;
	}
	
	public void setPrecipitation(String prec) {
		this.precipitation = prec;
	}
	
	public String getPrecipitation() {
		return this.precipitation;
	}


}
