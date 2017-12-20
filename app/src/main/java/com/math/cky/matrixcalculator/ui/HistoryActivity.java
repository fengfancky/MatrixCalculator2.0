package com.math.cky.matrixcalculator.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.adapter.HistoryReclcyerAdapter;
import com.math.cky.matrixcalculator.callback.SimpleItemTouchHelperCallback2;
import com.math.cky.matrixcalculator.conf.Settings;
import com.math.cky.matrixcalculator.database.MatrixHistoryBean;
import com.math.cky.matrixcalculator.database.MyDataHelper;
import com.math.cky.matrixcalculator.database.MyDatebase;
import com.math.cky.matrixcalculator.utils.Preference;

import java.util.ArrayList;

/**
 * Created by office on 2017/12/4.
 */

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRecycler;
    private MyDatebase myDatebase;
    private ArrayList<MatrixHistoryBean> arrayList1;
    private TextView historyEmpty;
    private MyDataHelper myDataHelper;
    private  HistoryReclcyerAdapter reclcyerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        initToolbar();
        historyRecycler=(RecyclerView)findViewById(R.id.history_recyclerview);
        historyEmpty= (TextView) findViewById(R.id.history_empty);
        initData();
        initRecycler();

        if (!TextUtils.isEmpty(Preference.newInstance(this).getString(Settings.DAY_NIGHT_MODE))){
            if (Preference.newInstance(this).getString(Settings.DAY_NIGHT_MODE).equals(Settings.NIGHT_MODE)){
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.history_toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setTitle("计算历史");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void initData(){
        myDatebase=new MyDatebase(HistoryActivity.this, "matrix", null, 1);
        myDataHelper=new MyDataHelper(myDatebase,HistoryActivity.this);
        ArrayList<MatrixHistoryBean> arrayList=myDataHelper.getAllHistroy();

        if (arrayList.size()==0){
            historyEmpty.setVisibility(View.VISIBLE);
        }else {
            historyEmpty.setVisibility(View.GONE);
        }
        arrayList1=new ArrayList<>();

        for (int i=0;i<arrayList.size();i++){
            arrayList1.add(arrayList.get(arrayList.size()-1-i));
        }
    }

    private void initRecycler(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(linearLayoutManager);

        reclcyerAdapter=new HistoryReclcyerAdapter(HistoryActivity.this,arrayList1);
        historyRecycler.setAdapter(reclcyerAdapter);

        reclcyerAdapter.setOnItemClickLister(new HistoryReclcyerAdapter.OnItemClickLister() {
            @Override
            public void onClickItem(View v, int position) {
                arrayList1.get(position).setIsClick(!arrayList1.get(position).isClick());
                reclcyerAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_delete:
                if (arrayList1.size()==0){
                    Toast.makeText(HistoryActivity.this, "暂无历史记录", Toast.LENGTH_SHORT).show();
                }else {
                    initAlertDialog();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history_main, menu);
        return true;
    }

    private void initAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(HistoryActivity.this);
        builder.setMessage("清空所有记录");
        builder.setPositiveButton(null, null);
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDataHelper.deleteAll();
                arrayList1.clear();
                reclcyerAdapter.notifyDataSetChanged();
                historyEmpty.setVisibility(View.VISIBLE);
            }
        });

        builder.setCancelable(false);
        builder.create().show();
    }
}
