package com.math.cky.matrixcalculator.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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
import android.widget.TextView;
import android.widget.Toast;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.conf.OperationType;
import com.math.cky.matrixcalculator.conf.Settings;
import com.math.cky.matrixcalculator.database.MyDataHelper;
import com.math.cky.matrixcalculator.database.MyDatebase;
import com.math.cky.matrixcalculator.utils.FormatString;
import com.math.cky.matrixcalculator.utils.Preference;

/**
 * Created by office on 2017/10/18.
 */

public class SingleOperationActivity extends AppCompatActivity implements View.OnFocusChangeListener,View.OnClickListener,View.OnLongClickListener{

    private String title;
    private EditText editRowNum,editColNum,editMatrix,num;
    private TextView clear,ok;
    private CardView matrixResult,matrix_num;
    private FloatingActionButton fab;
    private LinearLayout content,resultLayout,lastMatrixLayout;
    private String matrix;
    private int oneRowNum,oneColNum;
    private String type="TYPE_NULL";
    private MyDatebase myDatebase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_layout);
        initIntent();
        initToolbar();
        initView();
        initListener();

        if (!TextUtils.isEmpty(Preference.newInstance(this).getString(Settings.DAY_NIGHT_MODE))){
            if (Preference.newInstance(this).getString(Settings.DAY_NIGHT_MODE).equals(Settings.NIGHT_MODE)){
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }

    private void initIntent(){
        title=getIntent().getStringExtra(OperationType.OPERATION_TYPE);
    }

    private void initToolbar(){
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
        resultLayout= (LinearLayout) findViewById(R.id.result_layout);
        matrixResult= (CardView) findViewById(R.id.matrix_result);
        matrix_num= (CardView) findViewById(R.id.matrix_num);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        content= (LinearLayout) findViewById(R.id.last_content);
        lastMatrixLayout= (LinearLayout) findViewById(R.id.last_matrix_layout);


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
        }else if (title.equals(OperationType.INVERSE)){
            fab.setImageResource(R.mipmap.icon_inverse_press);
        }else if (title.equals(OperationType.LUDECOMPOSITION)){
            fab.setImageResource(R.mipmap.ic_triangle_press);
        }else if (title.equals(OperationType.QRDECOMPOSITION)){
            fab.setImageResource(R.mipmap.ic_qr_press);
        }else if (title.equals(OperationType.SDECOMPOSITION)){
            fab.setImageResource(R.mipmap.ic_s_press);
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

                myDatebase=new MyDatebase(SingleOperationActivity.this, "matrix", null, 1);
                MyDataHelper myDataHelper=new MyDataHelper(myDatebase,SingleOperationActivity.this);
                long time=System.currentTimeMillis();
                String arg1="";
                String arg2="";
                String historyResult="";
                String history_type="";

                if (title.equals(OperationType.NUM_TIME)){
                    //数乘
                    showLastMatrix(FormatString.newInstance(this).numTimes(matrix,oneRowNum,Double.parseDouble(num.getText().toString())),oneRowNum,oneColNum);

                    history_type=OperationType.NUM_TIME;
                    arg1=matrix+","+oneRowNum+","+oneColNum;
                    arg2=Double.parseDouble(num.getText().toString())+"";
                    historyResult=FormatString.newInstance(this).numTimes(matrix,oneRowNum,Double.parseDouble(num.getText().toString()))+","+oneRowNum+","+oneColNum;

                }else if(title.equals(OperationType.DEL)){
                    //行列式
                    if (oneRowNum==oneColNum){
                        oneLastValue(FormatString.newInstance(this).det(matrix,oneRowNum));
                        history_type=OperationType.DEL;
                        arg1=matrix+","+oneRowNum+","+oneColNum;
                        arg2="00";
                        historyResult=FormatString.newInstance(this).det(matrix,oneRowNum);
                    }else {
                        Toast.makeText(this, "请输入方阵", Toast.LENGTH_SHORT).show();
                    }

                }else if(title.equals(OperationType.EIGEN_VALUES)){
                    //特征值与特征向量
                    if (oneRowNum==oneColNum){
                        String[] result=FormatString.newInstance(this).values(matrix,oneRowNum);
                        oneLastValue(result[0]+"\n"+result[1]);

                        history_type=OperationType.EIGEN_VALUES;
                        arg1=matrix+","+oneRowNum+","+oneColNum;
                        arg2="00";
                        historyResult=result[0]+"\n"+result[1];
                    }else {
                        Toast.makeText(this, "请输入方阵", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else if(title.equals(OperationType.RANK)){
                    //秩
                    oneLastValue(FormatString.newInstance(this).rank(matrix,oneRowNum)+"");

                    history_type=OperationType.RANK;
                    arg1=matrix+","+oneRowNum+","+oneColNum;
                    arg2="00";
                    historyResult=FormatString.newInstance(this).rank(matrix,oneRowNum)+"";

                }else if (title.equals(OperationType.INVERSE)){
                    //逆
                    if (oneRowNum==oneColNum){
                        showLastMatrix(FormatString.newInstance(this).inverse(matrix,oneRowNum),oneRowNum,oneColNum);

                        history_type=OperationType.INVERSE;
                        arg1=matrix+","+oneRowNum+","+oneColNum;
                        arg2="00";
                        historyResult=FormatString.newInstance(this).inverse(matrix,oneRowNum)+"";

                    }else {
                        Toast.makeText(this, "请输入方阵", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else if (title.equals(OperationType.LUDECOMPOSITION)){
                    //三角分解
                    if(oneRowNum==oneColNum){
                        showDecompositionMatrix("L矩阵：","U矩阵：",FormatString.newInstance(this).luDecomposition(matrix,oneRowNum),oneRowNum,oneColNum);

                        history_type=OperationType.LUDECOMPOSITION;
                        arg1=matrix+","+oneRowNum+","+oneColNum;
                        arg2="00";
                        historyResult=FormatString.newInstance(this).luDecomposition(matrix,oneRowNum)[0]+","+FormatString.newInstance(this).luDecomposition(matrix,oneRowNum)[1]+","+oneRowNum+","+oneColNum;
                    }else {
                        Toast.makeText(this, "请输入方阵", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else if (title.equals(OperationType.QRDECOMPOSITION)){
                    //QR分解
                    showDecompositionMatrix("Q矩阵：","R矩阵：",FormatString.newInstance(this).qrDecomposition(matrix,oneRowNum),oneRowNum,oneColNum);

                    history_type=OperationType.QRDECOMPOSITION;
                    arg1=matrix+","+oneRowNum+","+oneColNum;
                    arg2="00";
                    historyResult=FormatString.newInstance(this).qrDecomposition(matrix,oneRowNum)[0]+","+FormatString.newInstance(this).qrDecomposition(matrix,oneRowNum)[1]+","+oneRowNum+","+oneColNum;
                }else if (title.equals(OperationType.SDECOMPOSITION)){
                    //SVD分解
                    sDecomposittion(FormatString.newInstance(this).svdDecomposition(matrix,oneRowNum),oneRowNum,oneColNum);

                    history_type=OperationType.SDECOMPOSITION;
                    arg1=matrix+","+oneRowNum+","+oneColNum;
                    arg2="00";
                    historyResult=FormatString.newInstance(this).svdDecomposition(matrix,oneRowNum)[0]+","+FormatString.newInstance(this).svdDecomposition(matrix,oneRowNum)[1]+","+FormatString.newInstance(this).svdDecomposition(matrix,oneRowNum)[2]+","+oneRowNum+","+oneColNum;
                }
                showLast();
                myDataHelper.insert(history_type, time, arg1, arg2,historyResult);
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

                if (FormatString.newInstance(this).isCorrectMatrix(editMatrix.getText().toString(), Integer.parseInt(editRowNum.getText().toString()), Integer.parseInt(editColNum.getText().toString()))) {
                    matrix = FormatString.newInstance(this).formatString(editMatrix.getText().toString());
                    oneRowNum = Integer.parseInt(editRowNum.getText().toString());
                    oneColNum = Integer.parseInt(editColNum.getText().toString());

                    showResultMatrix(matrix,oneRowNum,oneColNum);
                    resultAnim(matrixResult);

                } else {
                    Toast.makeText(SingleOperationActivity.this, "输入的数据不能形成一个矩阵", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }



    /**
     * 展示输入的矩阵
     * @param matrix
     * @param row
     * @param col
     */
    private void showResultMatrix(String matrix,int row,int col){
        resultLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(SingleOperationActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.newInstance(this).getStringArrayByString(matrix,row,col)[i]);
            resultLayout.addView(textView);
        }
    }

    private void showDecompositionMatrix(String str1,String str2,String[] strings,int row,int col){
        lastMatrixLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(5,5,5,5);
        linearLayout.setLayoutParams(layoutParams);

        TextView textViewL=new TextView(this);
        textViewL.setLayoutParams(layoutParams);
        textViewL.setPadding(10,10,10,10);
        textViewL.setGravity(Gravity.LEFT);
        textViewL.setTextSize(18);
        textViewL.setText(str1);
        linearLayout.addView(textViewL);

        LinearLayout linearLayout1=new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setLayoutParams(layoutParams);
        linearLayout1.setPadding(10,0,20,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(SingleOperationActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.newInstance(this).getStringArrayByString(FormatString.newInstance(this).formatString(strings[0]),row,col)[i]);
            linearLayout1.addView(textView);
        }
        linearLayout.addView(linearLayout1);

        LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        View view=new View(this);
        view.setLayoutParams(layoutParams1);
        view.setBackgroundColor(getResources().getColor(R.color.grayCCCCCC));
        linearLayout.addView(view);

        TextView textViewU=new TextView(this);
        textViewU.setLayoutParams(layoutParams);
        textViewU.setPadding(10,10,10,10);
        textViewU.setGravity(Gravity.LEFT);
        textViewU.setTextSize(18);
        textViewU.setText(str2);
        linearLayout.addView(textViewU);

        LinearLayout linearLayout2=new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setLayoutParams(layoutParams);
        linearLayout2.setPadding(20,0,10,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(SingleOperationActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.newInstance(this).getStringArrayByString(FormatString.newInstance(this).formatString(strings[1]),row,col)[i]);
            linearLayout2.addView(textView);
        }
        linearLayout.addView(linearLayout2);

        lastMatrixLayout.addView(linearLayout);
    }

    private void sDecomposittion(String[] strings,int row,int col){
        lastMatrixLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(5,5,5,5);
        linearLayout.setLayoutParams(layoutParams);

        TextView textViewS=new TextView(this);
        textViewS.setLayoutParams(layoutParams);
        textViewS.setPadding(10,10,10,10);
        textViewS.setGravity(Gravity.LEFT);
        textViewS.setTextSize(18);
        textViewS.setText("S矩阵：");
        linearLayout.addView(textViewS);

        LinearLayout linearLayout1=new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setLayoutParams(layoutParams);
        linearLayout1.setPadding(10,0,20,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(SingleOperationActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.newInstance(this).getStringArrayByString(FormatString.newInstance(this).formatString(strings[0]),row,col)[i]);
            linearLayout1.addView(textView);
        }
        linearLayout.addView(linearLayout1);

        LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        View view=new View(this);
        view.setLayoutParams(layoutParams1);
        view.setBackgroundColor(getResources().getColor(R.color.grayCCCCCC));
        linearLayout.addView(view);

        TextView textViewV=new TextView(this);
        textViewV.setLayoutParams(layoutParams);
        textViewV.setPadding(10,10,10,10);
        textViewV.setGravity(Gravity.LEFT);
        textViewV.setTextSize(18);
        textViewV.setText("V矩阵：");
        linearLayout.addView(textViewV);

        LinearLayout linearLayout2=new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setLayoutParams(layoutParams);
        linearLayout2.setPadding(20,0,10,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(SingleOperationActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.newInstance(this).getStringArrayByString(FormatString.newInstance(this).formatString(strings[1]),row,col)[i]);
            linearLayout2.addView(textView);
        }
        linearLayout.addView(linearLayout2);

        View view2=new View(this);
        view2.setLayoutParams(layoutParams1);
        view2.setBackgroundColor(getResources().getColor(R.color.grayCCCCCC));
        linearLayout.addView(view2);

        TextView textViewU=new TextView(this);
        textViewU.setLayoutParams(layoutParams);
        textViewU.setPadding(10,10,10,10);
        textViewU.setGravity(Gravity.LEFT);
        textViewU.setTextSize(18);
        textViewU.setText("U矩阵：");
        linearLayout.addView(textViewU);

        LinearLayout linearLayout3=new LinearLayout(this);
        linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout3.setLayoutParams(layoutParams);
        linearLayout3.setPadding(20,0,10,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(SingleOperationActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.newInstance(this).getStringArrayByString(FormatString.newInstance(this).formatString(strings[2]),row,col)[i]);
            linearLayout3.addView(textView);
        }
        linearLayout.addView(linearLayout3);

        lastMatrixLayout.addView(linearLayout);
    }


    /**
     * 展示运算结果matrix
     * @param matrix
     * @param row
     * @param col
     */
    private void showLastMatrix(String matrix,int row,int col){
        lastMatrixLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(SingleOperationActivity.this);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.newInstance(this).getStringArrayByString(FormatString.newInstance(this).formatString(matrix),row,col)[i]);
            lastMatrixLayout.addView(textView);
        }
    }

    /**
     * 展示运算结果value
     * @param value
     */
    private void oneLastValue(String value){
        lastMatrixLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView=new TextView(SingleOperationActivity.this);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(10,10,10,10);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(18);
        textView.setText(value);
        lastMatrixLayout.addView(textView);
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
        view.setAlpha(0.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(400).start();
    }

    private void showLast(){
        content.setAlpha(0.0f);
        content.setScaleX(0.0f);
        content.setScaleY(0.0f);
        content.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(400).start();
    }
}
