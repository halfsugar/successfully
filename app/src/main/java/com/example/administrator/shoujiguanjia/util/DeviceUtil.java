package com.example.administrator.shoujiguanjia.util;
import android.content.Context;
import android.view.WindowManager;
public class DeviceUtil {
    public static int dp2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    public static int px2dp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    public static int getScreenWidthPX(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeightPX(Context context) {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager.getDefaultDisplay().getHeight();
    }
}