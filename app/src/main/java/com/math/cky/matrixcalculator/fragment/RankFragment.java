package com.math.cky.matrixcalculator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.math.cky.matrixcalculator.R;

/**
 * Created by chen on 2017/6/14.
 */
public class RankFragment extends Fragment {

    private TextView trans1,trans2,trans3,trans4,trans5,trans6,trans7;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.rank_layout,container,false);

        trans1= (TextView) view.findViewById(R.id.matrix_rank_trans1);
        trans2= (TextView) view.findViewById(R.id.matrix_rank_trans2);
        trans3= (TextView) view.findViewById(R.id.matrix_rank_trans3);
        trans4= (TextView) view.findViewById(R.id.matrix_rank_trans4);
        trans5= (TextView) view.findViewById(R.id.matrix_rank_trans5);
        trans6= (TextView) view.findViewById(R.id.matrix_rank_trans6);
        trans7= (TextView) view.findViewById(R.id.matrix_rank_trans7);
        trans1.setText("（1）转置后秩不变");
        trans2.setText("（2）r(A)<=min(m,n),A是m*n型矩阵");
        trans3.setText("（3）r(kA)=r(A),k不等于0");
        trans4.setText("（4）r(A)=0 <=> A=0");
        trans5.setText("（5）r(A+B)<=r(A)+r(B)");
        trans6.setText("（6）r(A+B)<=min(r(A),r(B))");
        trans7.setText("（7）r(A)+r(B)-n<=r(AB)");
        return view;
    }
}
