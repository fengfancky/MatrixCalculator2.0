package com.math.cky.matrixcalculator.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.ui.SpaceImageDetailActivity;

/**
 * Created by chen on 2017/6/14.
 */
public class MulFragment extends Fragment implements View.OnClickListener {

    private ImageView matrix_mul_img,matrix_mul_exam_img;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.mul_layout,container,false);
        matrix_mul_img= (ImageView) view.findViewById(R.id.matrix_mul_img);
        matrix_mul_exam_img= (ImageView) view.findViewById(R.id.matrix_mul_exam_img);
        matrix_mul_img.setOnClickListener(this);
        matrix_mul_exam_img.setOnClickListener(this);
        return view;
    }

    private void goToImageDetail(View view,int res){
        Intent intent = new Intent(getActivity(), SpaceImageDetailActivity.class);
        intent.putExtra("image", res);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        intent.putExtra("locationX", location[0]);
        intent.putExtra("locationY", location[1]);
        intent.putExtra("width", view.getWidth());
        intent.putExtra("height", view.getHeight());
        startActivity(intent);
        getActivity().overridePendingTransition(0,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.matrix_mul_img:
                goToImageDetail(matrix_mul_img,R.mipmap.matrix_mul);
                break;
            case R.id.matrix_mul_exam_img:
                goToImageDetail(matrix_mul_exam_img,R.mipmap.matrix_mul_exam);
                break;
        }
    }
}
