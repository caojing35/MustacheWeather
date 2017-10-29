package com.mustacheweather.android.util.secure;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by caojing on 2017/10/25.
 */

public class SecureCacheInteceptor implements Interceptor {

    final SecureCache cache = new SecureCache(new File("cache_path"), 24*60*60);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        cache.internalCache.put(response);
        return response;
    }
}
