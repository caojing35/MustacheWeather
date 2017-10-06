package com.mustacheweather.android.util;

import android.text.TextUtils;

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
}
