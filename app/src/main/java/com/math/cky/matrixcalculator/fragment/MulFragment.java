package com.math.cky.matrixcalculator.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.conf.Settings;
import com.math.cky.matrixcalculator.ui.SpaceImageDetailActivity;
import com.math.cky.matrixcalculator.utils.Preference;
import com.math.cky.matrixcalculator.utils.WeChatShare;

/**
 * Created by chen on 2017/6/14.
 */
public class MulFragment extends Fragment implements View.OnClickListener,View.OnLongClickListener {

    private ImageView matrix_mul_img,matrix_mul_exam_img,mull_img;
    private LinearLayout mul_layout1,mul_layout2,mul_layout3;
    private WeChatShare weChatShare;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.mul_layout,container,false);
        matrix_mul_img= (ImageView) view.findViewById(R.id.matrix_mul_img);
        matrix_mul_exam_img= (ImageView) view.findViewById(R.id.matrix_mul_exam_img);
        mull_img= (ImageView) view.findViewById(R.id.mull_img);

        mul_layout1= (LinearLayout) view.findViewById(R.id.mul_layout1);
        mul_layout2= (LinearLayout) view.findViewById(R.id.mul_layout2);
        mul_layout3= (LinearLayout) view.findViewById(R.id.mul_layout3);

        mul_layout1.setOnLongClickListener(this);
        mul_layout2.setOnLongClickListener(this);
        mul_layout3.setOnLongClickListener(this);

        matrix_mul_img.setOnClickListener(this);
        matrix_mul_exam_img.setOnClickListener(this);
        weChatShare=new WeChatShare(getActivity());

        if (!TextUtils.isEmpty(Preference.newInstance(getActivity()).getString(Settings.DAY_NIGHT_MODE))){
            if (Preference.newInstance(getActivity()).getString(Settings.DAY_NIGHT_MODE).equals(Settings.NIGHT_MODE)){
                matrix_mul_img.setImageResource(R.mipmap.night_matrix_mul);
                matrix_mul_exam_img.setImageResource(R.mipmap.night_matrix_mul_exam);
                mull_img.setImageResource(R.mipmap.night_mulll);
            }else {
                matrix_mul_img.setImageResource(R.mipmap.matrix_mul);
                matrix_mul_exam_img.setImageResource(R.mipmap.matrix_mul_exam);
                mull_img.setImageResource(R.mipmap.mulll);
            }
        }

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

    @Override
    public boolean onLongClick(View v) {
        weChatShare.initAlertDialog(v);
        return false;
    }

}
