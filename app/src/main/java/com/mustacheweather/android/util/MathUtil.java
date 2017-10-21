package com.mustacheweather.android.util;

import java.util.UUID;

/**
 * Created by caojing on 2017/10/21.
 */

public class MathUtil {

    public static String genRandomStr(){
        return UUID.randomUUID().toString();
    }
}
