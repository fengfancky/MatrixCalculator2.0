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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.conf.OperationType;
import com.math.cky.matrixcalculator.utils.FormatString;
import com.math.cky.matrixcalculator.utils.Utils;

/**
 * Created by office on 2017/9/9.
 */

public class AddAndSubOperationActivity extends AppCompatActivity implements View.OnFocusChangeListener,View.OnClickListener,View.OnLongClickListener{

    private String title;
    private EditText editRowNum,editColNum,editMatrix;
    private TextView clear,ok,result,result1,lastMatrix;
    private CardView matrixResult,matrixResult1;
    private int flag=0;//用于判断是第一个矩阵还是第二个矩阵；
    private RadioButton radioButton,radioButton2;
    private FloatingActionButton fab;
    private RadioGroup radioGroup;
    private LinearLayout content;
    private String type="TYPE_NULL";
    private final static String ADD="ADD";
    private final static String SUB="SUB";
    private final static String TIMES="time";
    private static String TYPE_NULL="TYPE_NULL";
    private boolean isClose=true;
    private String matrix1,matrix2;
    private int oneRowNum,oneColNum,twoRowNum,twoColNum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sub_layout);

        initIntent();
        initToobar();
        initView();
        initListener();

    }

    private void initIntent(){
        title=getIntent().getStringExtra(OperationType.OPERATION_TYPE);
        if (title.equals(OperationType.TIME)){
            type=TIMES;
        }
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
        editMatrix= (EditText) findViewById(R.id.edit_matrix);
        clear= (TextView) findViewById(R.id.clear_but);
        ok= (TextView) findViewById(R.id.ok_but);
        result= (TextView) findViewById(R.id.result_text);
        result1= (TextView) findViewById(R.id.result_text1);
        matrixResult= (CardView) findViewById(R.id.matrix_result);
        matrixResult1= (CardView) findViewById(R.id.matrix_result1);
        radioButton= (RadioButton) findViewById(R.id.radioButton);
        radioButton2= (RadioButton) findViewById(R.id.radioButton2);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        content= (LinearLayout) findViewById(R.id.last_content);
        lastMatrix= (TextView) findViewById(R.id.last_matrix);
        if (title.equals(OperationType.TIME)){
            fab.setImageResource(R.mipmap.ic_menu_mul_press);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton:
                        fab.setImageResource(R.mipmap.operation_add);
                        type=ADD;
                        break;
                    case R.id.radioButton2:
                        fab.setImageResource(R.mipmap.operation_sub);
                        type=SUB;
                        break;
                }

            }
        });
    }

    private void initListener(){
        editMatrix.setOnFocusChangeListener(this);
        clear.setOnClickListener(this);
        ok.setOnClickListener(this);
        fab.setOnClickListener(this);
        matrixResult.setOnLongClickListener(this);
        matrixResult1.setOnLongClickListener(this);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.edit_matrix:
                if (hasFocus){
                    if (editRowNum.getText().length()==0||editColNum.getText().length()==0){
                        Toast.makeText(AddAndSubOperationActivity.this, "请先输入行数和列数", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if (editRowNum.getText().length()==0){
                    Toast.makeText(AddAndSubOperationActivity.this, "行数不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (editRowNum.getText().length()==0){
                    Toast.makeText(AddAndSubOperationActivity.this, "列数不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (editMatrix.getText().length()==0){
                    Toast.makeText(AddAndSubOperationActivity.this, "矩阵不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(matrix1)||TextUtils.isEmpty(matrix2)){
                    Toast.makeText(AddAndSubOperationActivity.this, "请输入矩阵", Toast.LENGTH_LONG).show();
                    return;
                }

                if (title.equals(OperationType.TIME)){
                    if (oneColNum!=twoRowNum){
                        Toast.makeText(AddAndSubOperationActivity.this, "第一个矩阵列数不等于第二个矩阵的行数", Toast.LENGTH_LONG).show();
                        return;
                    }
                    lastMatrix.setText(FormatString.muls(matrix1,matrix2,oneRowNum,twoRowNum));
                    showLast();
                }else {
                    if(isClose){
                        showAnim();
                        isClose=!isClose;
                    }else {
                        hideAnim();
                        isClose=!isClose;
                        if (oneRowNum!=twoRowNum){
                            Toast.makeText(AddAndSubOperationActivity.this, "矩阵行数不相等", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (oneColNum!=twoColNum){
                            Toast.makeText(AddAndSubOperationActivity.this, "矩阵列数不相等", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (type.equals(ADD)){
                            lastMatrix.setText(FormatString.add(matrix1,matrix2,Integer.parseInt(editRowNum.getText().toString())));
                            showLast();
                        }else if (type.equals(SUB)){
                            lastMatrix.setText(FormatString.sub(matrix1,matrix2,Integer.parseInt(editRowNum.getText().toString())));
                            showLast();
                        }else {

                        }
                    }
                }

                break;
            case R.id.clear_but:
                editRowNum.setText("");
                editColNum.setText("");
                editMatrix.setText("");
                break;
            case R.id.ok_but:

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editMatrix.getWindowToken(), 0);

                if (TextUtils.isEmpty(editRowNum.getText().toString())){
                    Toast.makeText(AddAndSubOperationActivity.this, "行数不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(editColNum.getText().toString())){
                    Toast.makeText(AddAndSubOperationActivity.this, "列数不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(editMatrix.getText().toString())){
                    Toast.makeText(AddAndSubOperationActivity.this, "矩阵不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                if(FormatString.isCorrectMatrix(editMatrix.getText().toString(),Integer.parseInt(editRowNum.getText().toString()),Integer.parseInt(editColNum.getText().toString()))){
                    if (flag==0){
                        matrix1=FormatString.formatString(editMatrix.getText().toString());
                        result.setText(FormatString.addLineFeed(matrix1,Integer.parseInt(editRowNum.getText().toString()),Integer.parseInt(editColNum.getText().toString())));
                        resultAnim(matrixResult);
                        oneRowNum=Integer.parseInt(editRowNum.getText().toString());
                        oneColNum=Integer.parseInt(editColNum.getText().toString());
                        flag=1;
                    }else if (flag==1){
                        matrix2=FormatString.formatString(editMatrix.getText().toString());
                        result1.setText(FormatString.addLineFeed(matrix2,Integer.parseInt(editRowNum.getText().toString()),Integer.parseInt(editColNum.getText().toString())));
                        resultAnim(matrixResult1);
                        twoRowNum=Integer.parseInt(editRowNum.getText().toString());
                        twoColNum=Integer.parseInt(editColNum.getText().toString());
                        flag=0;
                    }
                }else {
                    Toast.makeText(AddAndSubOperationActivity.this, "输入的数据不能形成一个矩阵", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void resultAnim(View view){
        view.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(500).setInterpolator(new BounceInterpolator()).start();
    }

    private void showAnim(){
            radioGroup.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).translationY(-Utils.dip2px(100,this)).setDuration(500).start();
    }

    private void hideAnim(){
            radioGroup.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).translationY(Utils.dip2px(100,this)).setDuration(500).start();
    }

    private void showLast(){
        content.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(500).setInterpolator(new BounceInterpolator()).start();
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
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.matrix_result:
                flag=0;
                matrix1="";
                result.setText("");
                v.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setDuration(500).setInterpolator(new BounceInterpolator()).start();
                oneColNum=0;
                oneRowNum=0;
                break;
            case R.id.matrix_result1:
                flag=1;
                matrix2="";
                result1.setText("");
                v.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setDuration(500).setInterpolator(new BounceInterpolator()).start();
                twoColNum=0;
                twoRowNum=0;
                break;
        }
        return false;
    }
}
