package com.mustacheweather.android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

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

    private final BitmapPool bitmapPool;

    public SecureDecoder(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;

    }

    private String getStringFromStream(InputStream inputStream) throws IOException {

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String receiveString;
        StringBuilder stringBuilder = new StringBuilder();
        while ((receiveString = bufferedReader.readLine()) != null) {
            stringBuilder.append(receiveString);
        }

        inputStream.close();
        return stringBuilder.toString();
    }


    public boolean handles(File source, BitmapFactory.Options options) throws IOException {
        String base64EncryptedData = getStringFromStream(new FileInputStream(source));
        String decryptedData = null;//TODO:encryptionRepository.decrypt(base64EncryptedData);
        return !decryptedData.equals(base64EncryptedData);
    }

    public Resource<Bitmap> decode(File source, int width, int height, BitmapFactory.Options options) throws IOException {
        String base64EncryptedData = getStringFromStream(new FileInputStream(source));
        String decryptedData = null;//TODO:encryptionRepository.decrypt(base64EncryptedData);
        byte[] encodeByte = Base64.decode(decryptedData, Base64.NO_WRAP);
        return BitmapResource.obtain(BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length), bitmapPool);
    }

    @Override
    public Resource<Bitmap> decode(File source, int width, int height) throws IOException {
        String base64EncryptedData = getStringFromStream(new FileInputStream(source));
        String decryptedData = null;//TODO:encryptionRepository.decrypt(base64EncryptedData);
        byte[] encodeByte = Base64.decode(decryptedData, Base64.NO_WRAP);
        return BitmapResource.obtain(BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length), bitmapPool);
    }

    @Override
    public String getId() {
        return "com.mustacheweather.android.util.secure.SecureDecoder";
    }
}