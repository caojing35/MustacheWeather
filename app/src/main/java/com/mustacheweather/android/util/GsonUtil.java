package com.mustacheweather.android.util;

import android.text.TextUtils;

import com.mustacheweather.android.db.City;
import com.mustacheweather.android.db.County;
import com.mustacheweather.android.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caojing on 2017/10/6.
 */

public class GsonUtil {

    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allProv = new JSONArray(response);
                for(int i = 0; i < allProv.length(); i++) {
                    JSONObject provObj = allProv.getJSONObject(i);
                    Province prov = new Province();
                    prov.setProvinceName(provObj.getString("name"));
                    prov.setProvinceCode(provObj.getInt("id"));
                    prov.save();
                }
                return true;
            }catch (JSONException ex)
            {
                return false;
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCity = new JSONArray(response);
                for(int i = 0; i < allCity.length(); i++) {
                    JSONObject ctObj = allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(ctObj.getString("name"));
                    city.setCityCode(ctObj.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (JSONException ex)
            {
                return false;
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounty = new JSONArray(response);
                for(int i = 0; i < allCounty.length(); i++) {
                    JSONObject countyObj = allCounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObj.getString("name"));
                    county.setWeatherId(countyObj.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch (JSONException ex)
            {
                return false;
            }
        }
        return false;
    }
}
