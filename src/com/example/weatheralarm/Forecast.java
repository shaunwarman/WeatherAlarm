package com.example.weatheralarm;

import java.util.List;

/**
 * Created by shaunw on 9/13/13.
 */
public class Forecast {
    protected Weather current;
    protected List<Weather> hourly;

    public Weather getCurrent() {
        return current;
    }

    public void setCurrent(Weather current) {
        this.current = current;
    }

    public List<Weather> getHourly() {
        return hourly;
    }

    public void setHourly(List<Weather> hourly) {
        this.hourly = hourly;
    }
}
