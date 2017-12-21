package com.math.cky.matrixcalculator.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.conf.Settings;
import com.math.cky.matrixcalculator.utils.Preference;

/**
 * Created by office on 2017/12/21.
 */

public class SettingTextActivity extends AppCompatActivity {


    private RadioGroup radio_group;
    private RadioButton s_radio,n_radio,m_radio,l_radio;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_text_layout);
        initToolbar();
        radio_group= (RadioGroup) findViewById(R.id.radio_group);
        s_radio= (RadioButton) findViewById(R.id.s_radio);
        n_radio= (RadioButton) findViewById(R.id.n_radio);
        m_radio= (RadioButton) findViewById(R.id.m_radio);
        l_radio= (RadioButton) findViewById(R.id.l_radio);

        settings();

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.s_radio:
                        Preference.newInstance(SettingTextActivity.this).put(Settings.TEXTSIZE,0.8f);
                        recreate();
                        break;
                    case R.id.n_radio:
                        Preference.newInstance(SettingTextActivity.this).put(Settings.TEXTSIZE,1f);
                        recreate();
                        break;
                    case R.id.m_radio:
                        Preference.newInstance(SettingTextActivity.this).put(Settings.TEXTSIZE,1.2f);
                        recreate();
                        break;
                    case R.id.l_radio:
                        Preference.newInstance(SettingTextActivity.this).put(Settings.TEXTSIZE,1.5f);
                        recreate();
                        break;
                }
            }
        });
    }

    @Override
    public Resources getResources() {
        Resources res =super.getResources();
        Configuration config = res.getConfiguration();
        float scale=Preference.newInstance(SettingTextActivity.this).getFloat(Settings.TEXTSIZE,1.0f);
        config.fontScale=scale;//1 设置正常字体大小的倍数

        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setTitle("文字大小");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }


    private void settings(){

        if (!TextUtils.isEmpty(Preference.newInstance(this).getString(Settings.DAY_NIGHT_MODE))){
            if (Preference.newInstance(this).getString(Settings.DAY_NIGHT_MODE).equals(Settings.NIGHT_MODE)){
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        float scale=Preference.newInstance(SettingTextActivity.this).getFloat(Settings.TEXTSIZE,1.0f);
        if (scale==0.8f){
            s_radio.setChecked(true);
        }else if (scale==1.0f){
            n_radio.setChecked(true);
        }else if (scale==1.2f){
            m_radio.setChecked(true);
        }else {
            l_radio.setChecked(true);
        }
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
