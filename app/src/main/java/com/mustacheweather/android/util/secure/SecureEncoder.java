package com.mustacheweather.android.util.secure;

import android.util.Base64;

import com.bumptech.glide.load.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by caojing on 2017/10/22.
 */
public class SecureEncoder implements Encoder<InputStream> {

    public SecureEncoder() {
    }

    private String base64Data(InputStream inputStream) throws IOException {
        byte[] inputStreamToByteArray = inputStreamToByteArray(inputStream);
        return Base64.encodeToString(inputStreamToByteArray, Base64.NO_WRAP);
    }

    private byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int readCount;
        byte[] data = new byte[16384];
        while ((readCount = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, readCount);
        }
        buffer.flush();

        return buffer.toByteArray();
    }


    @Override
    public boolean encode(InputStream data, OutputStream os) {
        try {

            String anyText = base64Data(data);
            String base64EncryptedData = null;//TODO:encryptionRepository.encrypt(anyText);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
            outputStreamWriter.write(base64EncryptedData);
            outputStreamWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getId() {
        return "com.mustacheweather.android.util.secure.SecureEncoder";
    }
}

