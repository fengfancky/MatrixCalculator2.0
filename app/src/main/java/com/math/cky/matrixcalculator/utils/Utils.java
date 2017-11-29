package com.math.cky.matrixcalculator.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by office on 2017/9/24.
 */

public class Utils {

    public static int getWidth(Activity context) {
        WindowManager wm = context.getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getHeight(Activity context) {
        WindowManager wm = context.getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }

    public static int dip2px(float dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
