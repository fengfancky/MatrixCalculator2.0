package com.math.cky.matrixcalculator.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Point;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;

import junit.runner.Version;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Created by saklhc on 14-1-18.
 */
public class Preference implements OnSharedPreferenceChangeListener {

    public static final String SCREEN_WIDTH = "key:screen_width";
    public static final String SCREEN_HEIGHT = "key:screen_height";

    private static volatile Preference mPreference; //单例模式
    private static SharedPreferences mSharedPreferences;

    public static Preference newInstance(Context context) {
        if (mPreference == null) {
            synchronized (Preference.class){
                if(mPreference==null){
                    mPreference = new Preference(context);
                }
            }
        }
        return mPreference;
    }

    /**
     * 第一次初始化只能在Activity或Fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private Preference(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked") Method method =
                    c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            put(SCREEN_WIDTH, dm.widthPixels);
            put(SCREEN_HEIGHT, dm.heightPixels);
        } catch (Exception e) {

        }
    }

    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }

    /**
     * String
     */
    public Preference put(String key, String value) {
        edit().putString(key, value).commit();
        return this;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    /**
     * Set<String>
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Preference put(String key, Set<String> value) {
        edit().putStringSet(key, value).commit();
        return this;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key, Set<String> defaultValues) {
        return mSharedPreferences.getStringSet(key, defaultValues);
    }

    /**
     * Int
     */
    public Preference put(String key, int value) {
        edit().putInt(key, value).commit();
        return this;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Long
     */
    public Preference put(String key, long value) {
        edit().putLong(key, value).commit();
        return this;
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    /**
     * Float
     */
    public Preference put(String key, float value) {
        edit().putFloat(key, value).commit();
        return this;
    }

    public float getFloat(String key, float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    /**
     * Boolean
     */
    public Preference put(String key, boolean value) {
        edit().putBoolean(key, value).commit();
        return this;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }

    private Editor edit() {
        return new Editor();
    }

    public static class Editor implements SharedPreferences.Editor {
        private SharedPreferences.Editor mEditor;

        /**
         * Constructor.
         */
        private Editor() {
            mEditor = mSharedPreferences.edit();
        }

        @Override
        public SharedPreferences.Editor putString(String key, String value) {
            mEditor.putString(key, value);
            return this;
        }

        @Override
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
            mEditor.putStringSet(key, values);
            return this;
        }

        @Override
        public SharedPreferences.Editor putInt(String key, int value) {
            mEditor.putInt(key, value);
            return this;
        }

        @Override
        public SharedPreferences.Editor putLong(String key, long value) {
            mEditor.putLong(key, value);
            return this;
        }

        @Override
        public SharedPreferences.Editor putFloat(String key, float value) {
            mEditor.putFloat(key, value);
            return this;
        }

        @Override
        public SharedPreferences.Editor putBoolean(String key, boolean value) {
            mEditor.putBoolean(key, value);
            return this;
        }

        @Override
        public SharedPreferences.Editor remove(String key) {
            mEditor.remove(key);
            return this;
        }

        @Override
        public SharedPreferences.Editor clear() {
            mEditor.clear();
            return this;
        }

        @Override
        public boolean commit() {
            return mEditor.commit();
        }

        @Override
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public void apply() {
            mEditor.apply();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //当默认的preference中的xml值改变的话，会自动发送消�?
//        EventBus.getDefault().post(new ChangeEvent(key));
    }


    /**
     * screen width,height
     */
    public int getScreenWidth() {
        return getInt(SCREEN_WIDTH, 0);
    }

    public int getScreenHeight() {
        return getInt(SCREEN_HEIGHT, 0);
    }

    public static class ChangeEvent {
        private String key;

        public ChangeEvent(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public boolean equals(String key) {
            return this.key.equals(key);
        }
    }




}
