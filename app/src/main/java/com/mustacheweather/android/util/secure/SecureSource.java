package com.mustacheweather.android.util.secure;

import java.io.IOException;

import okio.Buffer;
import okio.Source;
import okio.Timeout;

/**
 * Created by caojing on 2017/11/10.
 */

public class SecureSource implements Source
{
    @Override
    public long read(Buffer sink, long byteCount) throws IOException {
        return 0;
    }

    @Override
    public Timeout timeout() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
