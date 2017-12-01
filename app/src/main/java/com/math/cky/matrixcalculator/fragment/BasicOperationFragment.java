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
 * Created by chen on 2017/6/12.
 */
public class BasicOperationFragment extends Fragment implements View.OnClickListener{
    private ImageView add_img,sub_img,num_mul_img,trans_img,mul_other_img,trans_other_img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= LayoutInflater.from(getActivity()).inflate(R.layout.basic_operation_layout,container,false);
        add_img= (ImageView) view.findViewById(R.id.add_img);
        sub_img= (ImageView) view.findViewById(R.id.sub_img);
        num_mul_img= (ImageView) view.findViewById(R.id.num_mul_img);
        trans_img= (ImageView) view.findViewById(R.id.trans_img);
        mul_other_img= (ImageView) view.findViewById(R.id.mul_other_img);
        trans_other_img= (ImageView) view.findViewById(R.id.trans_other_img);
        add_img.setOnClickListener(this);
        sub_img.setOnClickListener(this);
        num_mul_img.setOnClickListener(this);
        trans_img.setOnClickListener(this);
        mul_other_img.setOnClickListener(this);
        trans_other_img.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_img:
                goToImageDetail(add_img,R.mipmap.add);
                break;
            case R.id.sub_img:
                goToImageDetail(sub_img,R.mipmap.sub);
                break;
            case R.id.num_mul_img:
                goToImageDetail(num_mul_img,R.mipmap.mul);
                break;
            case R.id.mul_other_img:
                goToImageDetail(mul_other_img,R.mipmap.mul_other);
                break;
            case R.id.trans_img:
                goToImageDetail(trans_img,R.mipmap.trans);
                break;
            case R.id.trans_other_img:
                goToImageDetail(trans_other_img,R.mipmap.trans_other);
                break;

        }
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
}
