package com.math.cky.matrixcalculator.utils;

import android.support.v7.widget.CardView;
import android.util.Log;


import java.text.DecimalFormat;

import Jama.LUDecomposition;
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
     * 将字符从新按列归类
     * @param str
     * @return
     */
    public static String[] getStringArrayByString(String str,int row,int col){
        String[] result=new String[col];
        String[] array=str.trim().split(" ");
        for (int j=0;j<result.length;j++){
            int count=0;
            StringBuffer stringBuffer=new StringBuffer("");
            for (int i=0;i<array.length;i++){
                if (i%col==j){
                    count++;
                    if (count<row){
                        stringBuffer.append(array[i]).append("\n");
                    }else {
                        stringBuffer.append(array[i]);
                    }
                }else {
                    continue;
                }
            }

            result[j]=stringBuffer.toString();
        }
        return result;
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
     * 特征值与特征向量
     */
    public static String[] values(String string1,int rowNum1){

        DecimalFormat df1 = new DecimalFormat("#E0");
        String[] result=new String[2];
        double[][] double1=getDoubleByDouble(getDoubleByString(string1),rowNum1);
        Matrix matrix_det1=new Matrix(double1);
        Matrix matrixD=matrix_det1.eig().getD();
        Matrix matrixV=matrix_det1.eig().getV();
        double[] matrixImg=matrix_det1.eig().getImagEigenvalues();
        double[] matrixReal=matrix_det1.eig().getRealEigenvalues();

        //特征值
        StringBuffer eigenvalues=new StringBuffer("特征值：\n");

        for (int i=0;i<matrixReal.length;i++){
            String realStr=matrixReal[i]+"";
            String imgStr="";
            if (matrixImg[i]==0.0){
                 imgStr="";
            }else if (matrixImg[i]>0){
                 imgStr="+"+matrixImg[i]+"i";
            }else {
                 imgStr=matrixImg[i]+"i";
            }
            eigenvalues.append(realStr+imgStr);
            if (i==matrixReal.length-1){
                eigenvalues.append("\n");
            }else {
                eigenvalues.append(",  ");
            }
        }
        result[0]=eigenvalues.toString();

        //特征向量
        StringBuffer eigenvector=new StringBuffer("特征向量：\n");

        double[][] matrixStr=matrixV.getArray();
        for (int j=0;j<matrixStr.length;j++){
            StringBuffer vector=new StringBuffer("向量"+(j+1)+":\n ");
            for (int k=0;k<matrixStr.length;k++){
                if (k==matrixStr.length-1){
                    vector.append(matrixStr[k][j]+"  ");
                }else {
                    vector.append(matrixStr[k][j]+",  ");
                }
            }
            eigenvector.append(vector);
            eigenvector.append("\n");
        }
        result[1]=eigenvector.toString();
        return result;
    }

    /**
     * 求矩阵的秩
     */
    public static int rank(String string1,int rowNum1){
        double[][] double1=getDoubleByDouble(getDoubleByString(string1),rowNum1);
        Matrix matrix=new Matrix(double1);
        return matrix.rank();
    }

    /**
     * 求矩阵的逆
     * @param string1
     * @param rowNum1
     * @return
     */
    public static String inverse(String string1,int rowNum1){
        String result="";
        double[][] double1=getDoubleByDouble(getDoubleByString(string1),rowNum1);
        Matrix matrix=new Matrix(double1);
        if (matrix.det()==0){
            result="矩阵不可逆，为奇异矩阵。";
        }else {
            result=printMatrix(getStringMatrix(matrix.inverse()));
        }
        return result;
    }

    /**
     * lu分解，获取上三角、下三角矩阵
     * @param string1
     * @param rowNum1
     * @return
     */
    public static String[] luDecomposition(String string1,int rowNum1){
        String[] results=new String[2];
        double[][] double1=getDoubleByDouble(getDoubleByString(string1),rowNum1);
        Matrix matrix=new Matrix(double1);
        LUDecomposition lu=null;
        try{
            lu=new LUDecomposition(matrix);
        }catch (ArrayIndexOutOfBoundsException e){

        }

        try{
            Matrix l=lu.getL();
            results[0]=printMatrix(getStringMatrix(l));
        }catch (Exception e){
            e.printStackTrace();
            results[0]="计算出错";
        }

        try{
            Matrix u=lu.getU();
            results[1]=printMatrix(getStringMatrix(u));
        }catch (Exception e){
            results[1]="计算出错";
        }

        return results;
    }
    public static String[] qrDecomposition(String string1,int rowNum1){
        String[] results=new String[2];
        double[][] double1=getDoubleByDouble(getDoubleByString(string1),rowNum1);
        Matrix matrix=new Matrix(double1);

        try{
            Matrix q=matrix.qr().getQ();
            results[0]=printMatrix(getStringMatrix(q));
        } catch (Exception e){
            results[0]="计算错误";
        }

        try{
            Matrix r=matrix.qr().getR();
            results[1]=printMatrix(getStringMatrix(r));
        }catch (Exception e){
            results[1]="计算错误";
        }

        return results;
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
                str+=matrix[i][j]+"  ";
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

    public static String getStringByDouble(double[] double1){
        String str="";
        for (int i = 0; i < double1.length; i++) {
            str=str+double1[i]+",  ";
        }
        return str;
    }

}
