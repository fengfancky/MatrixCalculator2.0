package com.math.cky.matrixcalculator.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Bitmap cutView(View view) {
        view.setDrawingCacheEnabled(false);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        return bitmap;
    }

    public static String cutWindow(View dView) {
        String filePath="";
        dView.setDrawingCacheEnabled(false);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath()+File.separator+"shareImg"+File.separator;
                File files = new File(sdCardPath);
                if (!files.exists()) {
                    files.mkdirs();
                }
                // 图片文件路径
                filePath = sdCardPath+ System.currentTimeMillis() + ".png";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
            }
        }
        return filePath;
    }

}
