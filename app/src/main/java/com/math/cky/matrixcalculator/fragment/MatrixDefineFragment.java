package com.math.cky.matrixcalculator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.math.cky.matrixcalculator.R;

/**
 * Created by chen on 2017/6/11.
 */
public class MatrixDefineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.matrix_define_layout,container,false);
        TextView textView2= (TextView) view.findViewById(R.id.matrix_define_info2);
        TextView textView3= (TextView) view.findViewById(R.id.matrix_define_info3);
        textView2.setText(Html.fromHtml("由 m × n 个数a<sub>ij</sub>排成的m行n列的数表称为m行n列的矩阵，简称m × n矩阵。记作："));
        textView3.setText(Html.fromHtml("这m×n 个数称为矩阵A的元素，简称为元，数a<sub>ij</sub>位于矩阵A的第i行第j列，称为矩阵A的(i,j)元，以数 a<sub>ij</sub>为(i,j)元的矩阵可记为(a<sub>ij</sub>)或(a<sub>ij</sub>)<sub>m×n</sub>，m×n矩阵A也记作A<sub>mn</sub>。"));
        return view;
    }
}
