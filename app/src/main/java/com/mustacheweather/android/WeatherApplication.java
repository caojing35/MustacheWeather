package com.mustacheweather.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.mustacheweather.android.greendao.DaoMaster;
import com.mustacheweather.android.greendao.DaoSession;
import com.mustacheweather.android.util.AndroidKeyUtil;
import com.mustacheweather.android.util.Environment;
import com.mustacheweather.android.util.MathUtil;

import org.greenrobot.greendao.database.Database;

import java.security.SecureRandom;

import javax.crypto.SecretKey;

/**
 * Created by caojing on 2017/10/20.
 */

public class WeatherApplication extends Application {

    private static String PERFKEY_DB_PWD = "PREF_DBPWD";

    private static String PERFKEY_KEY_IV = "PREF_KEYIV";

    private static final String TAG = "WeatherApplication";

    @Override
    public void onCreate() {
        super.onCreate();

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

            String base64IV = Base64.encodeToString(AndroidKeyUtil.mIV, Base64.DEFAULT);
            pref.edit().putString(PERFKEY_KEY_IV, base64IV).apply();

        } else {
            Log.w(TAG, "onCreate: get cipherPwd: " + cipherPwdStr);
            String base64IV = pref.getString(PERFKEY_KEY_IV, null);
            AndroidKeyUtil.mIV = Base64.decode(base64IV, Base64.DEFAULT);
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
