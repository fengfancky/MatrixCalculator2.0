package com.math.cky.matrixcalculator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.conf.Settings;
import com.math.cky.matrixcalculator.utils.Preference;

/**
 * Created by office on 2017/12/4.
 */

public class SettingsActivity extends AppCompatActivity {
    private CheckBox checkbox1,checkbox2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        initToolbar();
        checkbox1= (CheckBox) findViewById(R.id.checkbox1);
        checkbox2= (CheckBox) findViewById(R.id.checkbox2);
        if (Preference.newInstance(this).getString(Settings.FORMAT).equals(Settings.NORMAL)){
            checkbox1.setChecked(true);
            checkbox2.setChecked(false);
        }else {
            checkbox1.setChecked(false);
            checkbox2.setChecked(true);
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
