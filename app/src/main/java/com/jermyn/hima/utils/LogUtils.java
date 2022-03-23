package com.jermyn.hima.utils;

import android.util.Log;

public class LogUtils {
    private static String TAG = "LOGUTILS";//控制是否要输出log
    public static boolean _isRelease = false;

    /**
     * 如果是要发布了，可以在application里面把这里release一下，这样子就没有log输出了
     */
    public static void init(String baseTag, boolean isRelease) {
        TAG = baseTag;
        _isRelease = isRelease;
    }

    public static void d(String TAG, String content) {
        if (!_isRelease) {
            Log.d("[" + TAG + "]" + TAG, content);
        }
    }

    public static void v(String TAG, String content) {
        if (!_isRelease) {
            Log.d("[" + TAG + "]" + TAG, content);
        }
    }

    public static void i(String TAG, String content) {
        if (!_isRelease) {
            Log.d("[" + TAG + "]" + TAG, content);
        }
    }

    public static void w(String TAG, String content) {
        if (!_isRelease) {
            Log.d("[" + TAG + "]" + TAG, content);
        }
    }

    public static void e(String TAG, String content) {
        if (!_isRelease) {
            Log.d("[" + TAG + "]" + TAG, content);
        }
    }
}