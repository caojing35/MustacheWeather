package com.mustacheweather.android.util;

import android.content.Context;

import com.mustacheweather.android.greendao.DaoSession;

/**
 * Created by caojing on 2017/10/20.
 */

public class Environment {

    private static Context context = null;

    private static DaoSession daoSession = null;

    public static void setContext(Context context){
        Environment.context = context;
    }

    public static Context getContext(){
        return context;
    }

    public static void  setDaoSession(DaoSession daoSession){
        Environment.daoSession = daoSession;
    }

    public static DaoSession getDaoSession(){
        return Environment.daoSession;
    }


}
