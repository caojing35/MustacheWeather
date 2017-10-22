package com.mustacheweather.android.util.secure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.mustacheweather.android.util.StreamUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by caojing on 2017/10/22.
 */

public class SecureDecoder implements ResourceDecoder<File, Bitmap> {

    private static final String TAG = "SecureDecoder";

    private final BitmapPool bitmapPool;

    public SecureDecoder(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;

    }

    @Override
    public boolean handles(File source, Options options) throws IOException {
//        String base64EncryptedData = getStringFromStream(new FileInputStream(source));
//        String decryptedData = null;//TODO:encryptionRepository.decrypt(base64EncryptedData);
//        return !decryptedData.equals(base64EncryptedData);
        return true;
    }

    @Override
    public Resource<Bitmap> decode(File source, int width, int height, Options options) throws IOException {
        //no enctyption...
        Log.e(TAG, "decode: use my decode...");
        byte[] sourceBase64Bytes = StreamUtil.getBytesFromStream(new FileInputStream(source));
        byte[] sourceBytes = Base64.decode(sourceBase64Bytes, Base64.DEFAULT);
        return BitmapResource.obtain(BitmapFactory.decodeByteArray(sourceBytes, 0, sourceBytes.length), bitmapPool);

//        String base64EncryptedData = getStringFromStream(new FileInputStream(source));
//        String decryptedData = null;//TODO:encryptionRepository.decrypt(base64EncryptedData);
//        byte[] encodeByte = Base64.decode(decryptedData, Base64.NO_WRAP);
//        return BitmapResource.obtain(BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length), bitmapPool);
    }
}