package com.mustacheweather.android.util;

import com.mustacheweather.android.util.secure.SecureCache;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;

/**
 * Created by caojing on 2017/10/6.
 */

public class HttpUtil {


    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        Cache cache = new Cache(new File("cache_path"), 24*60*60);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.cache(cache);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //Request newRequest = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                Response response = chain.proceed(request);
                return response;
            }
        });
//        //cache response
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//
//                String key = chain.request().url()
//                scache.
//                return null;
//            }
//        });
        OkHttpClient client = builder.build();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
