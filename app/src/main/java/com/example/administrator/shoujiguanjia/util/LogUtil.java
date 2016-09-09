package com.example.administrator.shoujiguanjia.util;
import android.util.Log;
public class LogUtil {
    public static boolean isOpenDebug = true;
    public static void d(Object obj, String msg) {
        if (isOpenDebug) {
            Log.d(obj.getClass().getSimpleName(), msg);
        }
    }
    public static void d(Object obj, String msg, Throwable throwable) {
        if (isOpenDebug) {
            Log.d(obj.getClass().getSimpleName(), msg, throwable);
        }
    }
}
