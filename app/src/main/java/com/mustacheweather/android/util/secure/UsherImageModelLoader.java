package com.mustacheweather.android.util.secure;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

/**
 * Created by caojing on 2017/10/22.
 */

public class UsherImageModelLoader extends BaseGlideUrlLoader<String> {

    public UsherImageModelLoader(ModelLoader<GlideUrl, InputStream> urlLoader,
                                 ModelCache<String, GlideUrl> modelCache) {
        super(urlLoader, modelCache);
    }

    @Override
    protected String getUrl(String model, int width, int height) {
        return model;
    }

    public static class Factory implements ModelLoaderFactory<String, InputStream> {

        private final ModelCache<String, GlideUrl> modelCache = new ModelCache<>(500);

        @Override
        public ModelLoader<String, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new UsherImageModelLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class),
                    modelCache);
        }

        @Override
        public void teardown() {
        }
    }


}