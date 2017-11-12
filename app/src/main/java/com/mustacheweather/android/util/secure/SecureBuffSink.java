package com.mustacheweather.android.util.secure;

import android.util.Log;

import java.io.IOException;

import okio.Buffer;
import okio.Sink;
import okio.Timeout;

/**
 * Created by caojing on 2017/11/10.
 */

public class SecureBuffSink implements Sink
{
    private static final String TAG = "SecureBuffSink";
    
    @Override
    public void write(Buffer source, long byteCount) throws IOException {
        Log.i(TAG, "write: ");
    }

    @Override
    public void flush() throws IOException {
        Log.i(TAG, "flush: ");
    }

    @Override
    public Timeout timeout() {
        Log.i(TAG, "timeout: ");
        return null;
    }

    @Override
    public void close() throws IOException {
        Log.i(TAG, "close: ");
    }
}
