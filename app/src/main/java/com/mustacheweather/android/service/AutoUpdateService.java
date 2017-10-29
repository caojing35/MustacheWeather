package com.mustacheweather.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.mustacheweather.android.gson.Weather;
import com.mustacheweather.android.ui.weather.WeatherActivity;
import com.mustacheweather.android.util.AppPerfs;
import com.mustacheweather.android.util.GsonUtil;
import com.mustacheweather.android.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {

    private static final String TAG = "AutoUpdateService";

    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        updateWeather();
        updateBingPic();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 *1000;
        long triggerAtTime = SystemClock.currentThreadTimeMillis() + 1000;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather(){
        SharedPreferences prefs = this.getSharedPreferences(AppPerfs.WEATHER, Context.MODE_PRIVATE);
        String weatherString = prefs.getString("weather", null);
        Log.i(TAG, "updateWeather: " + weatherString);
        if (weatherString != null){
            Weather weather = GsonUtil.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;
            String weatherUrl = String.format("https://free-api.heweather.com/v5/weather?city=%s&key=%s", weatherId, "5932ca5eca2740fb98e4cfd94b947c1c");
            Log.i(TAG, "updateWeather: start send request.");
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "onFailure: ", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i(TAG, "onResponse: in autoUpdate...");
                    final String responseText = response.body().string();
                    final Weather weather = GsonUtil.handleWeatherResponse(responseText);
                    if (weather != null){
                        SharedPreferences.Editor editor = AutoUpdateService.
                                this.getSharedPreferences(AppPerfs.WEATHER, MODE_PRIVATE).
                                edit();

                        editor.putString("weather", responseText);
                        if (!editor.commit()){
                            Log.e(TAG, "onResponse: commit fail..");
                            return;
                        }

                        Intent i = new Intent();
                        i.setAction("com.mustacheweather.android.updateweather");
                        AutoUpdateService.this.sendBroadcast(i);
                        //LocalBroadcastManager.getInstance(AutoUpdateService.this).sendBroadcast(i);
                        Log.i(TAG, "onResponse: send autoupdate weather broadcast");
                    }
                }
            });
        }
    }

    private void updateBingPic(){
        final String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();

                SharedPreferences.Editor editor = AutoUpdateService.this.getSharedPreferences(AppPerfs.WEATHER, MODE_PRIVATE)
                        .edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
            }
        });
    }
}
