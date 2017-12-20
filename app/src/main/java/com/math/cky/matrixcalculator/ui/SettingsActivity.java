package com.math.cky.matrixcalculator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.conf.Settings;
import com.math.cky.matrixcalculator.utils.Preference;

/**
 * Created by office on 2017/12/4.
 */

public class SettingsActivity extends AppCompatActivity {

    private CheckBox checkbox1,checkbox2;
    private Switch formatSwitch,dayNightSwitch;
    private FrameLayout frame1,frame2;
    private TextView formatText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        initToolbar();
        checkbox1= (CheckBox) findViewById(R.id.checkbox1);
        checkbox2= (CheckBox) findViewById(R.id.checkbox2);
        formatSwitch= (Switch) findViewById(R.id.format_switch);
        frame1= (FrameLayout) findViewById(R.id.format1);
        frame2= (FrameLayout) findViewById(R.id.format2);
        formatText= (TextView) findViewById(R.id.format_text);
        dayNightSwitch= (Switch) findViewById(R.id.day_night_switch);

        if (Preference.newInstance(this).getString(Settings.FORMAT_SWITCH).equals(Settings.CLOSE)){
            formatSwitch.setChecked(true);
            frame1.setForeground(getResources().getDrawable(R.drawable.frame_fore));
            frame2.setForeground(getResources().getDrawable(R.drawable.frame_fore));
            formatText.setText("打开格式");
        }else {
            formatSwitch.setChecked(false);
            frame1.setForeground(null);
            frame2.setForeground(null);
            formatText.setText("关闭格式");
        }

        if (Preference.newInstance(this).getString(Settings.FORMAT).equals(Settings.NORMAL)){
            checkbox1.setChecked(true);
            checkbox2.setChecked(false);
        }else if (Preference.newInstance(this).getString(Settings.FORMAT).equals(Settings.PERCENTAGE)){
            checkbox1.setChecked(false);
            checkbox2.setChecked(true);
        }

        if (!TextUtils.isEmpty(Preference.newInstance(this).getString(Settings.DAY_NIGHT_MODE))){
            if (Preference.newInstance(this).getString(Settings.DAY_NIGHT_MODE).equals(Settings.NIGHT_MODE)){
                dayNightSwitch.setChecked(true);
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                dayNightSwitch.setChecked(false);
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Preference.newInstance(SettingsActivity.this).put(Settings.FORMAT,Settings.NORMAL);
                    checkbox1.setChecked(true);
                    checkbox2.setChecked(false);
                }else {
                    Preference.newInstance(SettingsActivity.this).put(Settings.FORMAT,Settings.PERCENTAGE);
                    checkbox1.setChecked(false);
                    checkbox2.setChecked(true);
                }
            }
        });

        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Preference.newInstance(SettingsActivity.this).put(Settings.FORMAT,Settings.PERCENTAGE);
                    checkbox1.setChecked(false);
                    checkbox2.setChecked(true);
                }else {
                    Preference.newInstance(SettingsActivity.this).put(Settings.FORMAT,Settings.NORMAL);
                    checkbox1.setChecked(true);
                    checkbox2.setChecked(false);
                }
            }
        });

        formatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Preference.newInstance(SettingsActivity.this).put(Settings.FORMAT_SWITCH,Settings.CLOSE);
                    frame1.setForeground(getResources().getDrawable(R.drawable.frame_fore));
                    frame2.setForeground(getResources().getDrawable(R.drawable.frame_fore));
                    checkbox1.setEnabled(false);
                    checkbox2.setEnabled(false);
                    formatText.setText("打开格式");
                }else {
                    Preference.newInstance(SettingsActivity.this).put(Settings.FORMAT_SWITCH,Settings.OPEN);
                    frame1.setForeground(null);
                    frame2.setForeground(null);
                    checkbox1.setEnabled(true);
                    checkbox2.setEnabled(true);
                    formatText.setText("关闭格式");
                }
            }
        });


        dayNightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Preference.newInstance(SettingsActivity.this).put(Settings.DAY_NIGHT_MODE,Settings.NIGHT_MODE);
                }else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Preference.newInstance(SettingsActivity.this).put(Settings.DAY_NIGHT_MODE,Settings.DAY_MODE);
                }

//               recreate();

            }
        });
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
