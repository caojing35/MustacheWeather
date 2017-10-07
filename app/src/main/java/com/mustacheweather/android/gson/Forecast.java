package com.mustacheweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by caojing on 2017/10/7.
 */

public class Forecast {

    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature{

        public String max;

        public String min;

        @Override
        public String toString() {
            return "Temperature{" +
                    "max='" + max + '\'' +
                    ", min='" + min + '\'' +
                    '}';
        }
    }

    public class More{

        @SerializedName("txt_d")
        public String info_d;

        @SerializedName("txt_n")
        public String info_n;

        @Override
        public String toString() {
            return "More{" +
                    "info_d='" + info_d + '\'' +
                    ", info_n='" + info_n + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "date='" + date + '\'' +
                ", temperature=" + temperature +
                ", more=" + more +
                '}';
    }
}
