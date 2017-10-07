package com.mustacheweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by caojing on 2017/10/7.
 */

public class Weather {

    public Basic basic;

    public AQI aqi;

    public Now now;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    @Override
    public String toString() {
        return "Weather{" +
                "basic=" + basic +
                ", aqi=" + aqi +
                ", now=" + now +
                ", forecastList=" + forecastList +
                '}';
    }
}
