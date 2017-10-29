package com.mustacheweather.android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.mustacheweather.android.greendao.DaoMaster;
import com.mustacheweather.android.util.AndroidKeyUtil;
import com.mustacheweather.android.util.Environment;
import com.mustacheweather.android.util.MathUtil;
import com.mustacheweather.android.util.secure.SecureDecoder;
import com.mustacheweather.android.util.secure.SecureEncoder;
import com.mustacheweather.android.util.secure.UsherImageModelLoader;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.crypto.SecretKey;

/**
 * Created by caojing on 2017/10/20.
 */

public class WeatherApplication extends Application {

    private static String PERFKEY_DB_PWD = "PREF_DBPWD";


    private static final String TAG = "WeatherApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        Log.i(TAG, "MyApplication onCreate");
        Log.i(TAG, "MyApplication pid is " + pid);
        Environment.setContext(this);
        ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    if (procInfo.processName.equals("com.mustacheweather.android.test")) {
                        Log.i(TAG, "process name is " + procInfo.processName);
                    } else {
                        AndroidKeyUtil.init();
                        initSecureDb();
                        initSecureGlide();
                    }
                }
            }
        }

    }

    private void initSecureGlide(){
        Glide glide = Glide.get(this);
        glide.getRegistry()
                .append(String.class, InputStream.class, new UsherImageModelLoader.Factory())
                .prepend(InputStream.class, new SecureEncoder())
                .prepend(File.class, Bitmap.class, new SecureDecoder(glide.getBitmapPool()));
    }

    private void initSecureDb(){
        SharedPreferences pref = this.getSharedPreferences("dbinfo", Context.MODE_PRIVATE);
        String cipherPwdStr = pref.getString(PERFKEY_DB_PWD, null);
        SecretKey key = AndroidKeyUtil.getKey();
        String relPwd = null;
        if (cipherPwdStr == null) {
            Log.w(TAG, "onCreate: init db pwd...");
            relPwd = MathUtil.genRandomStr();
            Log.w(TAG, "onCreate: new pwd is : " + relPwd);
            byte[] pwd = AndroidKeyUtil.encrypt(relPwd.getBytes(), key);
            cipherPwdStr = Base64.encodeToString(pwd, Base64.DEFAULT);
            Log.w(TAG, "onCreate: cipherPwd: " + cipherPwdStr);
            pref.edit().putString(PERFKEY_DB_PWD, cipherPwdStr).apply();
            AndroidKeyUtil.storeIV();

        } else {
            Log.w(TAG, "onCreate: get cipherPwd: " + cipherPwdStr);
            byte[] cipherPwdByte = Base64.decode(cipherPwdStr, Base64.DEFAULT);
            byte[] plaintext = AndroidKeyUtil.decrypt(cipherPwdByte, key);
            relPwd = new String(plaintext);
            Log.w(TAG, "onCreate: get pwd is: " + relPwd);
        }

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "weather-db-encrypted.db");
        Database db = helper.getEncryptedWritableDb(relPwd);
        Environment.setDaoSession(new DaoMaster(db).newSession());
    }

}
