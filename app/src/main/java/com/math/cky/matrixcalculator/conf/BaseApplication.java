package com.math.cky.matrixcalculator.conf;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by office on 2017/12/18.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }
}
