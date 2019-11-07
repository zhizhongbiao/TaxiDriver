package com.taxidriver.santos.utils.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.taxidriver.santos.utils.log.LogUtils;

/**
 * FileName :  SpUtil
 * Author   :  zhizhongbiao
 * Date     :  2018/9/5
 * Describe :
 */

public class SpUtil {

    public static final String BT_SP = "btSp";
    private static Context context;

    public static void init(Context c) {
        if (c == null) {
            throw new RuntimeException("Please initialize this in the application !!!  ,and Context must not be null !");
        }
        context = c;
    }

    public static SharedPreferences getSp(String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    public static boolean setInt(String key, int value) {
        return getSp(BT_SP)
                .edit()
                .putInt(key, value)
                .commit();
    }


    public static boolean setBoolean(String key, boolean value) {
        return getSp(BT_SP)
                .edit()
                .putBoolean(key, value)
                .commit();
    }

    public static boolean setString(String key, String value) {
        return getSp(BT_SP)
                .edit()
                .putString(key, value)
                .commit();
    }


    public static int getInt(String key, int defaultV) {
        return getSp(BT_SP).getInt(key, defaultV);


    }


    public static boolean getBoolean(String key, boolean defaultV) {
        return getSp(BT_SP).getBoolean(key, defaultV);
    }

    public static String getString(String key, String defaultV) {
        return getSp(BT_SP).getString(key, defaultV);
    }


    public static void clearSpData() {
        getSp(BT_SP).edit().clear().apply();
        LogUtils.i("Sp  clearSpData");
    }
}
