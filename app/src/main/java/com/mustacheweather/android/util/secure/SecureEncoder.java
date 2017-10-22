package com.mustacheweather.android.util.secure;

import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Options;
import com.mustacheweather.android.util.StreamUtil;

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

    private static final String TAG = "SecureEncoder";

    public SecureEncoder() {
    }

    @Override
    public boolean encode(InputStream data, File file, Options options) {
        try {
            Log.e(TAG, "encode: use my encode...");
            byte[] dataBytes = StreamUtil.getBytesFromStream(data);
            String base64String = Base64.encodeToString(dataBytes, Base64.DEFAULT);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputStreamWriter.write(base64String);
            outputStreamWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}


