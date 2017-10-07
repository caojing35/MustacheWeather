package com.mustacheweather.android.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mustacheweather.android.R;
import com.mustacheweather.android.gson.Forecast;
import com.mustacheweather.android.gson.Weather;
import com.mustacheweather.android.util.GsonUtil;
import com.mustacheweather.android.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by caojing on 2017/10/7.
 */

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25text;

    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25text = (TextView) findViewById(R.id.pm25_text);

        String weatherId = getIntent().getStringExtra("weather_id");
        if (TextUtils.isEmpty(weatherId))
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            String weatherString = prefs.getString("weather", null);
            if (weatherString != null) {
                //有缓存报文时直接解析天气数据
                Weather weather = GsonUtil.handleWeatherResponse(weatherString);
                showWeatherInfo(weather);
            }

            Log.e(TAG, "onCreate: weatherId is empty and pref is empty.");
            finish();
        } else {
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
    }

    private void requestWeather(final String weatherId){
        String weatherUrl = String.format("https://free-api.heweather.com/v5/weather?city=%s&key=%s", weatherId, "5932ca5eca2740fb98e4cfd94b947c1c");

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "Get Weather Fail.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = GsonUtil.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null){
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).
                                    edit();

                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "Get Weather Fail.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Weather weather){
        if (weather == null) {
            Toast.makeText(this, "Get Weather Fail.", Toast.LENGTH_SHORT).show();
            return;
        }
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;

        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);

        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);

        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            showForecast(forecast, view);
            forecastLayout.addView(view);
        }

        if (weather.aqi != null){
            aqiText.setText(weather.aqi.city.aqi);
            pm25text.setText(weather.aqi.city.pm25);
        }

        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void showForecast(Forecast forecast, View view) {
        TextView dateText = (TextView) view.findViewById(R.id.date_text);
        TextView infoText = (TextView) view.findViewById(R.id.info_text);
        TextView maxText = (TextView) view.findViewById(R.id.max_text);
        TextView minText = (TextView) view.findViewById(R.id.min_text);

        dateText.setText(forecast.date);
        String info = (forecast.more.info_d.equals(forecast.more.info_n)) ?
                forecast.more.info_d :
                (String.format("%s~%s", forecast.more.info_d, forecast.more.info_n));

        infoText.setText(info);
        maxText.setText(forecast.temperature.max);
        minText.setText(forecast.temperature.min);
    }
}
