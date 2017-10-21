package com.mustacheweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.mustacheweather.android.greendao.City;
import com.mustacheweather.android.greendao.CityDao;
import com.mustacheweather.android.greendao.County;
import com.mustacheweather.android.greendao.Province;
import com.mustacheweather.android.greendao.CountyDao;
import com.mustacheweather.android.greendao.ProvinceDao;
import com.mustacheweather.android.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caojing on 2017/10/6.
 */

public class GsonUtil {

    private static final String TAG = "GsonUtil";

    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            ProvinceDao provinceDao = Environment.getDaoSession().getProvinceDao();
            try{
                JSONArray allProv = new JSONArray(response);
                for(int i = 0; i < allProv.length(); i++) {
                    JSONObject provObj = allProv.getJSONObject(i);
                    Province prov = new Province();
                    prov.setProvinceName(provObj.getString("name"));
                    prov.setProvinceCode(provObj.getInt("id"));
                    provinceDao.insert(prov);
                }
                return true;
            }catch (JSONException ex)
            {
                return false;
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, long provinceId){
        if(!TextUtils.isEmpty(response)){
            CityDao cityDao = Environment.getDaoSession().getCityDao();
            try{
                JSONArray allCity = new JSONArray(response);
                for(int i = 0; i < allCity.length(); i++) {
                    JSONObject ctObj = allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(ctObj.getString("name"));
                    city.setCityCode(ctObj.getInt("id"));
                    city.setProvinceId(provinceId);
                    cityDao.insert(city);
                }
                return true;
            }catch (JSONException ex)
            {
                return false;
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, long cityId){
        if(!TextUtils.isEmpty(response)){
            CountyDao countyDao = Environment.getDaoSession().getCountyDao();
            try{
                JSONArray allCounty = new JSONArray(response);
                for(int i = 0; i < allCounty.length(); i++) {
                    JSONObject countyObj = allCounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObj.getString("name"));
                    county.setWeatherId(countyObj.getString("weather_id"));
                    county.setCityId(cityId);
                    countyDao.insert(county);
                }
                return true;
            }catch (JSONException ex)
            {
                return false;
            }
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response){
        try{
            Log.i(TAG, "handleWeatherResponse: " + response);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            Weather weather = new Gson().fromJson(weatherContent, Weather.class);
            Log.i(TAG, "handleWeatherResponse: " + weather.toString());
            return weather;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
