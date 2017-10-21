package com.mustacheweather.android.util;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by caojing on 2017/10/6.
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        Cache cache = new Cache(new File("cache_path"), 24*60*60);
        OkHttpClient client = new OkHttpClient().newBuilder().cache(cache).build();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
