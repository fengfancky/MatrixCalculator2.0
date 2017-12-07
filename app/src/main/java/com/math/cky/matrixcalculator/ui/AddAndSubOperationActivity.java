package com.math.cky.matrixcalculator.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.math.cky.matrixcalculator.database.MyDataHelper;
import com.math.cky.matrixcalculator.database.MyDatebase;
import com.math.cky.matrixcalculator.utils.FormatString;
import com.math.cky.matrixcalculator.utils.Utils;

/**
 * Created by office on 2017/9/9.
 */

public class AddAndSubOperationActivity extends AppCompatActivity implements View.OnFocusChangeListener,View.OnClickListener,View.OnLongClickListener{

    private String title;
    private EditText editRowNum,editColNum,editMatrix;
    private TextView clear,ok;
    private CardView matrixResult,matrixResult1;
    private int flag=0;//用于判断是第一个矩阵还是第二个矩阵；
    private RadioButton radioButton,radioButton2;
    private FloatingActionButton fab;
    private RadioGroup radioGroup;
    private LinearLayout content,resultTextLayout,resultTextLayout1,last_matrix_layout;
    private String type="TYPE_NULL";
    private final static String ADD="ADD";
    private final static String SUB="SUB";
    private final static String TIMES="time";
    private static String TYPE_NULL="TYPE_NULL";
    private boolean isClose=true;
    private String matrix1,matrix2;
    private int oneRowNum,oneColNum,twoRowNum,twoColNum;
    private MyDatebase myDatebase;


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
        resultTextLayout= (LinearLayout) findViewById(R.id.result_text_layout);
        resultTextLayout1= (LinearLayout) findViewById(R.id.result_text_layout1);


        matrixResult= (CardView) findViewById(R.id.matrix_result);
        matrixResult1= (CardView) findViewById(R.id.matrix_result1);
        radioButton= (RadioButton) findViewById(R.id.radioButton);
        radioButton2= (RadioButton) findViewById(R.id.radioButton2);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        content= (LinearLayout) findViewById(R.id.last_content);
        last_matrix_layout= (LinearLayout) findViewById(R.id.last_matrix_layout);

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

                myDatebase=new MyDatebase(AddAndSubOperationActivity.this, "matrix", null, 1);
                MyDataHelper myDataHelper=new MyDataHelper(myDatebase,AddAndSubOperationActivity.this);
                long time=System.currentTimeMillis();
                String arg1="";
                String arg2="";
                String historyResult="";
                String history_type="";

                if (title.equals(OperationType.TIME)){
                    if (oneColNum!=twoRowNum){
                        Toast.makeText(AddAndSubOperationActivity.this, "第一个矩阵列数不等于第二个矩阵的行数", Toast.LENGTH_LONG).show();
                        return;
                    }
                    showLastMatrix(last_matrix_layout,FormatString.newInstance(this).muls(matrix1,matrix2,oneRowNum,twoRowNum),oneRowNum,twoColNum);
                    showLast();

                    history_type=OperationType.TIME;
                    arg1=matrix1+","+oneRowNum+","+oneColNum;
                    arg2=matrix2+","+twoRowNum+","+twoColNum;
                    historyResult=FormatString.newInstance(this).muls(matrix1,matrix2,oneRowNum,twoRowNum)+","+oneRowNum+","+twoColNum;
                    myDataHelper.insert(history_type, time, arg1, arg2,historyResult);
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
                            showLastMatrix(last_matrix_layout,FormatString.newInstance(this).add(matrix1,matrix2,Integer.parseInt(editRowNum.getText().toString())),oneRowNum,oneColNum);
                            showLast();

                            history_type="矩阵加法";
                            arg1=matrix1+","+oneRowNum+","+oneColNum;
                            arg2=matrix2+","+twoRowNum+","+twoColNum;
                            historyResult=FormatString.newInstance(this).add(matrix1,matrix2,Integer.parseInt(editRowNum.getText().toString()))+","+oneRowNum+","+oneColNum;
                            myDataHelper.insert(history_type, time, arg1, arg2,historyResult);

                        }else if (type.equals(SUB)){
                            showLastMatrix(last_matrix_layout,FormatString.newInstance(this).sub(matrix1,matrix2,Integer.parseInt(editRowNum.getText().toString())),oneRowNum,oneColNum);
                            showLast();

                            history_type="矩阵减法";
                            arg1=matrix1+","+oneRowNum+","+oneColNum;
                            arg2=matrix2+","+twoRowNum+","+twoColNum;
                            historyResult=FormatString.newInstance(this).sub(matrix1,matrix2,Integer.parseInt(editRowNum.getText().toString()))+","+oneRowNum+","+oneColNum;
                            myDataHelper.insert(history_type, time, arg1, arg2,historyResult);
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

                if(FormatString.newInstance(this).isCorrectMatrix(editMatrix.getText().toString(),Integer.parseInt(editRowNum.getText().toString()),Integer.parseInt(editColNum.getText().toString()))){
                    if (flag==0){
                        matrix1=FormatString.newInstance(this).formatString(editMatrix.getText().toString());
                        oneRowNum=Integer.parseInt(editRowNum.getText().toString());
                        oneColNum=Integer.parseInt(editColNum.getText().toString());

                        showResultMatrix(resultTextLayout,matrix1,oneRowNum,oneColNum);
                        resultAnim(matrixResult);
                        flag=1;
                    }else if (flag==1){
                        matrix2=FormatString.newInstance(this).formatString(editMatrix.getText().toString());
                        twoRowNum=Integer.parseInt(editRowNum.getText().toString());
                        twoColNum=Integer.parseInt(editColNum.getText().toString());

                        showResultMatrix(resultTextLayout1,matrix2,twoRowNum,twoColNum);
                        resultAnim(matrixResult1);
                        flag=0;
                    }
                }else {
                    Toast.makeText(AddAndSubOperationActivity.this, "输入的数据不能形成一个矩阵", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void showResultMatrix(LinearLayout view,String matrix,int row,int col){
        view.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(AddAndSubOperationActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(5,5,5,5);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.newInstance(this).getStringArrayByString(matrix,row,col)[i]);
            view.addView(textView);
        }
    }

    private void showLastMatrix(LinearLayout view,String matrix,int row,int col){
        view.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(AddAndSubOperationActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.newInstance(this).getStringArrayByString(FormatString.newInstance(this).formatString(matrix),row,col)[i]);
            view.addView(textView);
        }
    }

    private void resultAnim(View view){
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(500).setInterpolator(new BounceInterpolator()).start();
    }

    private void showAnim(){
            radioGroup.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).translationY(-Utils.dip2px(100,this)).setDuration(500).start();
    }

    private void hideAnim(){
            radioGroup.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).translationY(Utils.dip2px(100,this)).setDuration(500).start();
    }

    private void showLast(){
        content.setAlpha(0.0f);
        content.setScaleX(0.0f);
        content.setScaleY(0.0f);
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
                v.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setDuration(400).start();
                oneColNum=0;
                oneRowNum=0;
                break;
            case R.id.matrix_result1:
                flag=1;
                matrix2="";
                v.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setDuration(400).start();
                twoColNum=0;
                twoRowNum=0;
                break;
        }
        return false;
    }
}
