package com.mustacheweather.android.util;

import android.icu.util.Output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by caojing on 2017/10/22.
 */

public class StreamUtil {

    public static byte[] getBytesFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int readCount;
        byte[] data = new byte[16384];
        while ((readCount = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, readCount);
        }
        buffer.flush();

        return buffer.toByteArray();
    }
}
