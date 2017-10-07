package com.mustacheweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by caojing on 2017/10/7.
 */

public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;

        public String pm25;

        @SerializedName("qlty")
        public String quality;

        @Override
        public String toString() {
            return "AQICity{" +
                    "aqi='" + aqi + '\'' +
                    ", pm25='" + pm25 + '\'' +
                    ", quality='" + quality + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AQI{" +
                "city=" + city +
                '}';
    }
}
