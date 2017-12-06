package com.math.cky.matrixcalculator.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.callback.ItemTouchHelperAdapter;
import com.math.cky.matrixcalculator.conf.OperationType;
import com.math.cky.matrixcalculator.database.MatrixHistoryBean;
import com.math.cky.matrixcalculator.database.MyDataHelper;
import com.math.cky.matrixcalculator.database.MyDatebase;
import com.math.cky.matrixcalculator.utils.FormatString;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


/**
 * Created by Administrator on 2016/5/6.
 */
public class HistoryReclcyerAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<MatrixHistoryBean> arrayList;
    private final int HISTORY=1;
    private final int HISTORY_DET=2;

    public HistoryReclcyerAdapter(Context context, ArrayList<MatrixHistoryBean> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    private OnItemClickLister onItemClickLister;

    public interface OnItemClickLister{
        void onClickItem(View v, int position);
    }

    public void setOnItemClickLister(OnItemClickLister onItemClickLister){
        this.onItemClickLister=onItemClickLister;
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).isClick())
            return HISTORY_DET;
        else
            return HISTORY;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=null;
        if (viewType==HISTORY){
            v= LayoutInflater.from(context).inflate(R.layout.history_recycler_item,null);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(lp);
            return new HistoryHolder(v);
        }else{
            v= LayoutInflater.from(context).inflate(R.layout.history_detail_item,null);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(lp);
            return new HistoryDetHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HistoryHolder){
            HistoryHolder historyHolder=(HistoryHolder)holder;
            historyHolder.cardTime.setText(getTime(arrayList.get(position).getTime())+"计算过：");
            historyHolder.cardType.setText(arrayList.get(position).getType());
            if (("刚刚").equals(getTime(arrayList.get(position).getTime()))){
                historyHolder.cardTime.setTextColor(context.getResources().getColor(R.color.redDE5347));
            }else if (("不久之前").equals(getTime(arrayList.get(position).getTime()))){
                historyHolder.cardTime.setTextColor(context.getResources().getColor(R.color.yellowFF9224));
            }else {
                historyHolder.cardTime.setTextColor(context.getResources().getColor(R.color.green73B534));
            }
            historyHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLister.onClickItem(v,position);
                }
            });
        }else {
            final HistoryDetHolder historyDetHolder=(HistoryDetHolder)holder;

            String type=arrayList.get(position).getType();
            //标题
            historyDetHolder.historyTitle.setText("计算 "+type);

            //参数1
            String[] strings=arrayList.get(position).getArg1().split(",");
            showResultMatrix(historyDetHolder.historyMatrixResultLayout,strings[0],Integer.valueOf(strings[1]),Integer.valueOf(strings[2]));


            //参数2
            if (("00").equals(arrayList.get(position).getArg2())){
                historyDetHolder.historyMatrixContext2.setVisibility(View.GONE);
            }else {
                historyDetHolder.historyMatrixContext2.setVisibility(View.VISIBLE);
                if (type.equals(OperationType.NUM_TIME)){
                    String stringsss=arrayList.get(position).getArg2();
                    oneLastValue(historyDetHolder.historyMatrixResultLayout2,stringsss);
                }else {
                    String[] stringsss=arrayList.get(position).getArg2().split(",");
                    showResultMatrix(historyDetHolder.historyMatrixResultLayout2,stringsss[0],Integer.valueOf(stringsss[1]),Integer.valueOf(stringsss[2]));
                }

            }

            //结果
            if (type.equals(OperationType.NUM_TIME)){
                String[] stringResult=arrayList.get(position).getResult().split(",");
                showLastMatrix(historyDetHolder.historyMatrixResultLayout3,stringResult[0],Integer.valueOf(stringResult[1]),Integer.valueOf(stringResult[2]));
            }else if (type.equals(OperationType.DEL)||type.equals(OperationType.EIGEN_VALUES)||type.equals(OperationType.RANK)||type.equals(OperationType.INVERSE)){
                String result=arrayList.get(position).getResult();
                oneLastValue(historyDetHolder.historyMatrixResultLayout3,result);
            } else if (type.equals(OperationType.LUDECOMPOSITION)) {
                String[] stringResult=arrayList.get(position).getResult().split(",");
                showDecompositionMatrix(historyDetHolder.historyMatrixResultLayout3,"L矩阵：","U矩阵：",stringResult[0],stringResult[1],Integer.valueOf(stringResult[2]),Integer.valueOf(stringResult[3]));
            }else if(type.equals(OperationType.QRDECOMPOSITION)){
                String[] stringResult=arrayList.get(position).getResult().split(",");
                showDecompositionMatrix(historyDetHolder.historyMatrixResultLayout3,"Q矩阵：","R矩阵：",stringResult[0],stringResult[1],Integer.valueOf(stringResult[2]),Integer.valueOf(stringResult[3]));
            }else if (type.equals(OperationType.SDECOMPOSITION)){
                String[] stringResult=arrayList.get(position).getResult().split(",");
                sDecomposittion(historyDetHolder.historyMatrixResultLayout3,stringResult[0],stringResult[1],stringResult[2],Integer.valueOf(stringResult[3]),Integer.valueOf(stringResult[4]));
            }else {
                String[] stringResult=arrayList.get(position).getResult().split(",");
                showLastMatrix(historyDetHolder.historyMatrixResultLayout3,stringResult[0],Integer.valueOf(stringResult[1]),Integer.valueOf(stringResult[2]));
            }

            historyDetHolder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onItemClickLister.onClickItem(v, position);
             }
         });
        }
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder{
        private TextView cardTime,cardType;
        public HistoryHolder(View v){
            super(v);
            cardTime=(TextView)v.findViewById(R.id.card_time);
            cardType=(TextView)v.findViewById(R.id.card_type);
        }
    }

    public static class HistoryDetHolder extends RecyclerView.ViewHolder{
        private TextView historyTitle;
        private CardView historyMatrixContext2,cardDet;
        private LinearLayout resutl,historyLayout,historyMatrixResultLayout,historyMatrixResultLayout3,historyMatrixResultLayout2;
        private ScrollView historyScroll;
        public HistoryDetHolder(View v){
            super(v);
            historyMatrixResultLayout=(LinearLayout)v.findViewById(R.id.history_matrix_result_layout);
            historyMatrixResultLayout2=(LinearLayout)v.findViewById(R.id.history_matrix_result_layout2);
            historyMatrixContext2=(CardView)v.findViewById(R.id.history_matrix_context2);
            resutl=(LinearLayout)v.findViewById(R.id.resutl);
            historyMatrixResultLayout3=(LinearLayout)v.findViewById(R.id.history_matrix_result_layout3);
            historyTitle=(TextView)v.findViewById(R.id.history_title);
            cardDet=(CardView)v.findViewById(R.id.card_det);
            historyLayout=(LinearLayout)v.findViewById(R.id.history_layout);
        }
    }

    public String getTime(long time){
        long nowTime=System.currentTimeMillis();
        String str="";
        Date date=new Date(time);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        if (!(nowTime-time>10*60*1000)){
            str="刚刚";
        }else if ((nowTime-time<60*60*1000)&&nowTime-time>10*60*1000){
            str="不久之前";
        }else if((nowTime-time>60*60*1000)&&(nowTime-time<120*60*1000)){
            str="一小时之前";
        }else {
            str = (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        }
        return str;
    }



    /**
     * 展示输入的矩阵
     * @param matrix
     * @param row
     * @param col
     */
    private void showResultMatrix(LinearLayout view,String matrix,int row,int col){
        view.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.getStringArrayByString(matrix,row,col)[i]);
            view.addView(textView);
        }
    }
    /**
     * 展示运算结果matrix
     * @param matrix
     * @param row
     * @param col
     */
    private void showLastMatrix(LinearLayout view,String matrix,int row,int col){
        view.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.getStringArrayByString(FormatString.formatString(matrix),row,col)[i]);
            view.addView(textView);
        }
    }

    /**
     * 展示运算结果value
     * @param value
     */
    private void oneLastValue(LinearLayout view,String value){
        view.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView=new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(10,10,10,10);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(18);
        textView.setText(value);
        view.addView(textView);
    }

    private void showDecompositionMatrix(LinearLayout layout,String str1,String str2,String strings1,String strings2,int row,int col){
        layout.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(5,5,5,5);
        linearLayout.setLayoutParams(layoutParams);

        TextView textViewL=new TextView(context);
        textViewL.setLayoutParams(layoutParams);
        textViewL.setPadding(10,10,10,10);
        textViewL.setGravity(Gravity.LEFT);
        textViewL.setTextSize(18);
        textViewL.setText(str1);
        linearLayout.addView(textViewL);

        LinearLayout linearLayout1=new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setLayoutParams(layoutParams);
        linearLayout1.setPadding(10,0,20,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.getStringArrayByString(FormatString.formatString(strings1),row,col)[i]);
            linearLayout1.addView(textView);
        }
        linearLayout.addView(linearLayout1);

        LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        View view=new View(context);
        view.setLayoutParams(layoutParams1);
        view.setBackgroundColor(context.getResources().getColor(R.color.grayCCCCCC));
        linearLayout.addView(view);

        TextView textViewU=new TextView(context);
        textViewU.setLayoutParams(layoutParams);
        textViewU.setPadding(10,10,10,10);
        textViewU.setGravity(Gravity.LEFT);
        textViewU.setTextSize(18);
        textViewU.setText(str2);
        linearLayout.addView(textViewU);

        LinearLayout linearLayout2=new LinearLayout(context);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setLayoutParams(layoutParams);
        linearLayout2.setPadding(20,0,10,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.getStringArrayByString(FormatString.formatString(strings2),row,col)[i]);
            linearLayout2.addView(textView);
        }
        linearLayout.addView(linearLayout2);

        layout.addView(linearLayout);
    }

    private void sDecomposittion(LinearLayout layout,String strings1,String strings2,String strings3,int row,int col){
        layout.removeAllViews();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(5,5,5,5);
        linearLayout.setLayoutParams(layoutParams);

        TextView textViewS=new TextView(context);
        textViewS.setLayoutParams(layoutParams);
        textViewS.setPadding(10,10,10,10);
        textViewS.setGravity(Gravity.LEFT);
        textViewS.setTextSize(18);
        textViewS.setText("S矩阵：");
        linearLayout.addView(textViewS);

        LinearLayout linearLayout1=new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setLayoutParams(layoutParams);
        linearLayout1.setPadding(10,0,20,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.getStringArrayByString(FormatString.formatString(strings1),row,col)[i]);
            linearLayout1.addView(textView);
        }
        linearLayout.addView(linearLayout1);

        LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        View view=new View(context);
        view.setLayoutParams(layoutParams1);
        view.setBackgroundColor(context.getResources().getColor(R.color.grayCCCCCC));
        linearLayout.addView(view);

        TextView textViewV=new TextView(context);
        textViewV.setLayoutParams(layoutParams);
        textViewV.setPadding(10,10,10,10);
        textViewV.setGravity(Gravity.LEFT);
        textViewV.setTextSize(18);
        textViewV.setText("V矩阵：");
        linearLayout.addView(textViewV);

        LinearLayout linearLayout2=new LinearLayout(context);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setLayoutParams(layoutParams);
        linearLayout2.setPadding(20,0,10,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.getStringArrayByString(FormatString.formatString(strings2),row,col)[i]);
            linearLayout2.addView(textView);
        }
        linearLayout.addView(linearLayout2);

        View view2=new View(context);
        view2.setLayoutParams(layoutParams1);
        view2.setBackgroundColor(context.getResources().getColor(R.color.grayCCCCCC));
        linearLayout.addView(view2);

        TextView textViewU=new TextView(context);
        textViewU.setLayoutParams(layoutParams);
        textViewU.setPadding(10,10,10,10);
        textViewU.setGravity(Gravity.LEFT);
        textViewU.setTextSize(18);
        textViewU.setText("U矩阵：");
        linearLayout.addView(textViewU);

        LinearLayout linearLayout3=new LinearLayout(context);
        linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout3.setLayoutParams(layoutParams);
        linearLayout3.setPadding(20,0,10,0);
        for (int i=0;i<col;i++){
            TextView textView=new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.LEFT);
            textView.setTextSize(18);
            textView.setText(FormatString.getStringArrayByString(FormatString.formatString(strings3),row,col)[i]);
            linearLayout3.addView(textView);
        }
        linearLayout.addView(linearLayout3);

        layout.addView(linearLayout);
    }
}
