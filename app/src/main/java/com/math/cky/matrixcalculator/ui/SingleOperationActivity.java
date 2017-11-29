package com.math.cky.matrixcalculator.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.conf.OperationType;
import com.math.cky.matrixcalculator.utils.FormatString;

/**
 * Created by office on 2017/10/18.
 */

public class SingleOperationActivity extends AppCompatActivity implements View.OnFocusChangeListener,View.OnClickListener,View.OnLongClickListener{

    private String title;
    private EditText editRowNum,editColNum,editMatrix,num;
    private TextView clear,ok,result,lastMatrix;
    private CardView matrixResult,matrix_num;
    private FloatingActionButton fab;
    private LinearLayout content;
    private String matrix;
    private int oneRowNum,oneColNum;
    private String type="TYPE_NULL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_layout);
        initIntent();
        initToobar();
        initView();
        initListener();
    }

    private void initIntent(){
        title=getIntent().getStringExtra(OperationType.OPERATION_TYPE);

    }

    private void initToobar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
    private void initView(){
        editRowNum= (EditText) findViewById(R.id.edit_num);
        editColNum= (EditText) findViewById(R.id.edit_num2);
        num= (EditText) findViewById(R.id.num);
        editMatrix= (EditText) findViewById(R.id.edit_matrix);
        clear= (TextView) findViewById(R.id.clear_but);
        ok= (TextView) findViewById(R.id.ok_but);
        result= (TextView) findViewById(R.id.result_text);
        matrixResult= (CardView) findViewById(R.id.matrix_result);
        matrix_num= (CardView) findViewById(R.id.matrix_num);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        content= (LinearLayout) findViewById(R.id.last_content);
        lastMatrix= (TextView) findViewById(R.id.last_matrix);

        if (title.equals(OperationType.NUM_TIME)){
            matrix_num.setVisibility(View.VISIBLE);
        }else  {
            matrix_num.setVisibility(View.GONE);
        }

        if (title.equals(OperationType.NUM_TIME)){
            fab.setImageResource(R.mipmap.ic_menu_mul_num_press);
        }else if (title.equals(OperationType.CONJUGATE)){
            fab.setImageResource(R.mipmap.ic_menu_e_press);
        }else if (title.equals(OperationType.DEL)){
            fab.setImageResource(R.mipmap.ic_menu_det_press);
        }else if (title.equals(OperationType.EIGEN_VALUES)){
            fab.setImageResource(R.mipmap.ic_menu_vector_press);
        }else if (title.equals(OperationType.RANK)){
            fab.setImageResource(R.mipmap.ic_menu_rank_press);
        }
    }

    private void initListener(){
        editMatrix.setOnFocusChangeListener(this);
        clear.setOnClickListener(this);
        ok.setOnClickListener(this);
        fab.setOnClickListener(this);
        matrixResult.setOnLongClickListener(this);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if (editRowNum.getText().length()==0){
                    Toast.makeText(SingleOperationActivity.this, "行数不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (editRowNum.getText().length()==0){
                    Toast.makeText(SingleOperationActivity.this, "列数不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (editMatrix.getText().length()==0){
                    Toast.makeText(SingleOperationActivity.this, "矩阵不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(matrix)){
                    Toast.makeText(SingleOperationActivity.this, "请输入矩阵", Toast.LENGTH_LONG).show();
                    return;
                }

                if (title.equals(OperationType.NUM_TIME)&&TextUtils.isEmpty(num.getText().toString())){
                    Toast.makeText(SingleOperationActivity.this, "请输入常数", Toast.LENGTH_LONG).show();
                    return;
                }

                if (title.equals(OperationType.NUM_TIME)){
                    lastMatrix.setText(FormatString.numTimes(matrix,oneRowNum,Double.parseDouble(num.getText().toString())));

                }else if(title.equals(OperationType.CONJUGATE)){

                }else if(title.equals(OperationType.DEL)){
                    lastMatrix.setText(FormatString.det(matrix,oneRowNum));
                }else if(title.equals(OperationType.EIGEN_VALUES)){
                    lastMatrix.setText(FormatString.values(matrix,oneRowNum));
                }else if(title.equals(OperationType.RANK)){

                }
                showLast();
                break;

            case R.id.clear_but:
                editRowNum.setText("");
                editColNum.setText("");
                editMatrix.setText("");
                num.setText("");
                break;

            case R.id.ok_but:

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editMatrix.getWindowToken(), 0);

                if (TextUtils.isEmpty(editRowNum.getText().toString())){
                    Toast.makeText(SingleOperationActivity.this, "行数不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(editColNum.getText().toString())){
                    Toast.makeText(SingleOperationActivity.this, "列数不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(editMatrix.getText().toString())){
                    Toast.makeText(SingleOperationActivity.this, "矩阵不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (title.equals(OperationType.NUM_TIME)&&TextUtils.isEmpty(num.getText().toString())){
                    Toast.makeText(SingleOperationActivity.this, "请输入常数", Toast.LENGTH_LONG).show();
                    return;
                }

                if (FormatString.isCorrectMatrix(editMatrix.getText().toString(), Integer.parseInt(editRowNum.getText().toString()), Integer.parseInt(editColNum.getText().toString()))) {
                    matrix = FormatString.formatString(editMatrix.getText().toString());
                    result.setText(FormatString.addLineFeed(matrix, Integer.parseInt(editRowNum.getText().toString()), Integer.parseInt(editColNum.getText().toString())));
                    resultAnim(matrixResult);
                    oneRowNum = Integer.parseInt(editRowNum.getText().toString());
                    oneColNum = Integer.parseInt(editColNum.getText().toString());

                } else {
                    Toast.makeText(SingleOperationActivity.this, "输入的数据不能形成一个矩阵", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.edit_matrix:
                if (hasFocus){
                    if (editRowNum.getText().length()==0||editColNum.getText().length()==0){
                        Toast.makeText(SingleOperationActivity.this, "请先输入行数和列数", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    private void resultAnim(View view){
        view.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(500).setInterpolator(new BounceInterpolator()).start();
    }

    private void showLast(){
        content.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(500).setInterpolator(new BounceInterpolator()).start();
    }
}
