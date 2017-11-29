package com.math.cky.matrixcalculator.utils;

import android.util.Log;


import Jama.Matrix;

/**
 * Created by fengfancky on 2017/9/20.
 */

public class FormatString {

    private static final String TAG = "FormatString";

    /**
     * 将字符串中的其他字符替换成空格
     * @param str
     * @return
     */
    public static String formatString(String str){
        String string ="";
        string=str.trim().replaceAll("[\\t\\n\\r]", " ");
        string=string.replaceAll(" +"," ");
        string.trim();
        return string;
    }

    /**
     * 判断输入的字符串是否满足行列的要求
     * @param string
     * @param row
     * @param col
     * @return
     */
    public static boolean isCorrectMatrix(String string,int row,int col){
        if (formatString(string.trim()).split(" ").length==(row*col)){
            return true;
        }
        return false;
    }

    /**
     * 根据列数换行
     * 最后一行不换行
     * @param string
     * @param colNum
     * @return
     */
    public static String addLineFeed(String string,int row,int colNum){
        StringBuffer result=new StringBuffer();
        String[] strings=string.trim().split(" ");
        for (int i=0;i<strings.length;i++){
            if ((i+1)%colNum==0&&(i+1)/colNum!=row){
                result.append(strings[i]).append("\n");
            }else {
                result.append(strings[i]+" ");
            }
        }
        Log.i(TAG, "addLineFeed: "+result.toString());
        return result.toString().trim();
    }

    /**
     * 去除字符串开始的空格
     * @param string
     * @return
     */
    public static String cutStartBlank(String string){
        if (string.startsWith(" ")){
            string=string.substring(1);
            return cutStartBlank(string);
        }
        return string;
    }


    /**
     * 将字符串转换为一维数组
     * @param str
     * @return
     */
    public static double[] getDoubleByString(String str){

        String[] array=str.split(" ");
        double[] doubles=new double[array.length];
        for (int i=0;i<array.length;i++){
            try{
                doubles[i]=Double.parseDouble(array[i]);
            }catch(Exception e){
            }
        }
        return doubles;
    }


    /**
     * 将一维数组转换成二维数组
     * @param doubles
     * @param num
     * @return
     */
    public static double[][] getDoubleByDouble(double[] doubles,int num){
        double[][] doublesMatrix=new double[num][doubles.length/num];
        for ( int i=0;i<doubles.length;i++){
            doublesMatrix[i/(doubles.length/num)][i%(doubles.length/num)]=doubles[i];
        }
        return doublesMatrix;
    }

    /**
     *   矩阵加法
     */
    public static Matrix plus(double[][] matrix1,double[][] matrix2){
        Matrix matrix;
        Matrix matrix_plus1=new Matrix(matrix1);
        Matrix matrix_plus2=new Matrix(matrix2);
        matrix=matrix_plus1.plus(matrix_plus2);
        return matrix;
    }
    public static String add(String string1,String string2,int rowNum){
        double[][]  double1=getDoubleByDouble(getDoubleByString(string1),rowNum);
        double[][]  double2=getDoubleByDouble(getDoubleByString(string2),rowNum);
        return getLastResult(plus(double1,double2));
    }

    /**
     * 矩阵减法
     * @param matrix1
     * @param matrix2
     * @return
     */
    public static Matrix minus(double[][] matrix1,double[][] matrix2){
        Matrix matrix;
        Matrix matrix_minus1=new Matrix(matrix1);
        Matrix matrix_minus2=new Matrix(matrix2);
        matrix=matrix_minus1.minus(matrix_minus2);
        return matrix;
    }

    public static String sub(String string1,String string2,int rowNum){
        double[][]  double1=getDoubleByDouble(getDoubleByString(string1),rowNum);
        double[][]  double2=getDoubleByDouble(getDoubleByString(string2),rowNum);
        return getLastResult(minus(double1,double2));
    }

    /**
     * 矩阵乘法
     */
    public static Matrix times(double[][] matrix1,double[][] matrix2){
        Matrix matrix;
        Matrix matrix_time1=new Matrix(matrix1);
        Matrix matrix_time2=new Matrix(matrix2);
        matrix=matrix_time1.times(matrix_time2);
        return matrix;
    }

    public static String muls(String string1,String string2,int rowNum1,int rowNum2){
        double[][]  double1=getDoubleByDouble(getDoubleByString(string1),rowNum1);
        double[][]  double2=getDoubleByDouble(getDoubleByString(string2),rowNum2);
        return getLastResult(times(double1,double2));
    }


    /**
     * 矩阵数乘
     * @param matrix1
     * @param value
     * @return
     */
    public static Matrix timesEquals(double[][] matrix1,double value){
        Matrix matrix;
        Matrix matrix_timesEquals1=new Matrix(matrix1);
        matrix=matrix_timesEquals1.timesEquals(value);
        return matrix;
    }

    public static String numTimes(String string1,int rowNum1,double value){
        double[][]  double1=getDoubleByDouble(getDoubleByString(string1),rowNum1);
        return getLastResult(timesEquals(double1,value));
    }

    /**
     * 行列式
     */
    public static String det(String string1,int rowNum1){
        double[][] double1=getDoubleByDouble(getDoubleByString(string1),rowNum1);
        Matrix matrix_det1=new Matrix(double1);
        return matrix_det1.det()+"";
    }

    /**
     * 特征值
     */
    public static String values(String string1,int rowNum1){
        String result="";
        double[][] double1=getDoubleByDouble(getDoubleByString(string1),rowNum1);
        Matrix matrix_det1=new Matrix(double1);
        Matrix matrix=matrix_det1.eig().getD();
        double[][] doubles=matrix.getArray();
        for (int g=0;g<doubles.length;g++){
            if (doubles[g][g]>0)
                result+=doubles[g][g]+"  ";
        }
        return result;
    }


    /**
     * 将矩阵转换成二维字符串
     * @param matrix
     * @return
     */
    public static String[][] getStringMatrix(Matrix matrix) {
        double[][] doubles=matrix.getArray();
        String[][] strMatrix=new String[doubles.length][doubles[0].length];
        for (int i=0;i<doubles.length;i++){
            for (int j=0;j<doubles[0].length;j++){
                strMatrix[i][j]=doubles[i][j]+"";
            }
        }
        return strMatrix;
    }


    /**
     * 将二维字符串转换成字符串
     * @param matrix
     * @return
     */
    public static String printMatrix(String[][] matrix){
        String str="";
        for (int i=0;i<matrix.length;i++){
            for (int j=0;j<matrix[0].length;j++){
                str+=matrix[i][j]+" ";
            }
            if (i!=matrix.length-1)
                str+="\n";
        }
        return str;
    }

    /**
     * 输出矩阵用于文本显示
     * @return
     */
    public static String getLastResult(Matrix matrix){
        return printMatrix(getStringMatrix(matrix)).trim();
    }


}