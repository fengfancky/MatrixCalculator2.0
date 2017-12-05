package com.math.cky.matrixcalculator.database;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MatrixHistoryBean {
    private int id;
    private String type;
    private long time;
    private String arg1;
    private String arg2;
    private String result;
    private boolean isClick;

    public MatrixHistoryBean(String type,long time,String arg1,String arg2,String result){
        this.type=type;
        this.time=time;
        this.arg1=arg1;
        this.arg2=arg2;
        this.result=result;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setIsClick(boolean isClick) {
        this.isClick = isClick;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
