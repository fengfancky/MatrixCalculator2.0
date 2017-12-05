package com.math.cky.matrixcalculator.database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MyDataHelper {
    private MyDatebase myDatebase;
    private Context context;

    public MyDataHelper(MyDatebase myDatebase,Context context){
        this.myDatebase=myDatebase;
        this.context=context;
    }
    public ArrayList<MatrixHistoryBean> getAllHistroy(){
        ArrayList<MatrixHistoryBean> arrayList=new ArrayList<>();
        Cursor cursor= myDatebase.getReadableDatabase().rawQuery("select *from matrix_history",null);
        cursor.move(-1);
        while (cursor.moveToNext()){
            String type=cursor.getString(1);
            long time=cursor.getLong(2);
            String arg1=cursor.getString(3);
            String arg2=cursor.getString(4);
            String result=cursor.getString(5);
            MatrixHistoryBean historyBean=new MatrixHistoryBean(type,time,arg1,arg2,result);
            arrayList.add(historyBean);
        }
        return arrayList;
    }


    public void insert(String type,long time,String arg1,String arg2,String result){
        myDatebase.getWritableDatabase().execSQL("insert into matrix_history(type,time,arg1,arg2,result) values('"+type+"',"+time+",'"+arg1+"','"+arg2+"','"+result+"')");
    }

    public void delete(long time){
        myDatebase.getWritableDatabase().execSQL("delete from matrix_history where time="+time);
    }

    public void deleteAll(){
        myDatebase.getWritableDatabase().execSQL("delete from matrix_history ");
    }

}
