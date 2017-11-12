package com.mustacheweather.android.util.secure;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.internal.io.FileSystem;
import okio.Sink;
import okio.Source;

/**
 * Created by caojing on 2017/11/10.
 */

public class SecureFileSystem implements FileSystem
{
    private static final String TAG = "SecureFileSystem";

    @Override
    public Source source(File file) throws FileNotFoundException {
        return new SecureSource();
    }

    @Override
    public Sink sink(File file) throws FileNotFoundException {
        return new SecureBuffSink();
    }

    @Override
    public Sink appendingSink(File file) throws FileNotFoundException {
        return null;
    }

    @Override
    public void delete(File file) throws IOException {
        Log.i(TAG, "delete: ");
    }

    @Override
    public boolean exists(File file) {
        return false;
    }

    @Override
    public long size(File file) {
        return 0;
    }

    @Override
    public void rename(File from, File to) throws IOException {

    }

    @Override
    public void deleteContents(File directory) throws IOException {

    }
}
