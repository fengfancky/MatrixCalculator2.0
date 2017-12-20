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
 * Created by chen on 2017/6/12.
 */
public class BasicOperationFragment extends Fragment implements View.OnClickListener,View.OnLongClickListener{
    private ImageView add_img,sub_img,num_mul_img,trans_img,mul_other_img,trans_other_img,e_img1,e_img2,e_img3,et_img1,et_img2,et_img3,et_img4;
    private LinearLayout basic_layout_7,basic_layout_6,basic_layout_5,basic_layout_4,basic_layout_3,basic_layout_2,basic_layout_1;
    private WeChatShare weChatShare;

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
        e_img1= (ImageView) view.findViewById(R.id.e_img1);
        e_img2= (ImageView) view.findViewById(R.id.e_img2);
        e_img3= (ImageView) view.findViewById(R.id.e_img3);
        et_img1= (ImageView) view.findViewById(R.id.et_img1);
        et_img2= (ImageView) view.findViewById(R.id.et_img2);
        et_img3= (ImageView) view.findViewById(R.id.et_img3);
        et_img4= (ImageView) view.findViewById(R.id.et_img4);

        basic_layout_1= (LinearLayout) view.findViewById(R.id.basic_layout_1);
        basic_layout_2= (LinearLayout) view.findViewById(R.id.basic_layout_2);
        basic_layout_3= (LinearLayout) view.findViewById(R.id.basic_layout_3);
        basic_layout_4= (LinearLayout) view.findViewById(R.id.basic_layout_4);
        basic_layout_5= (LinearLayout) view.findViewById(R.id.basic_layout_5);
        basic_layout_6= (LinearLayout) view.findViewById(R.id.basic_layout_6);
        basic_layout_7= (LinearLayout) view.findViewById(R.id.basic_layout_7);

        add_img.setOnClickListener(this);
        sub_img.setOnClickListener(this);
        num_mul_img.setOnClickListener(this);
        trans_img.setOnClickListener(this);
        mul_other_img.setOnClickListener(this);
        trans_other_img.setOnClickListener(this);
        e_img1.setOnClickListener(this);
        e_img2.setOnClickListener(this);
        e_img3.setOnClickListener(this);
        et_img1.setOnClickListener(this);
        et_img2.setOnClickListener(this);
        et_img3.setOnClickListener(this);
        et_img4.setOnClickListener(this);

        basic_layout_1.setOnLongClickListener(this);
        basic_layout_2.setOnLongClickListener(this);
        basic_layout_3.setOnLongClickListener(this);
        basic_layout_4.setOnLongClickListener(this);
        basic_layout_5.setOnLongClickListener(this);
        basic_layout_6.setOnLongClickListener(this);
        basic_layout_7.setOnLongClickListener(this);
        weChatShare=new WeChatShare(getActivity());


        if (!TextUtils.isEmpty(Preference.newInstance(getActivity()).getString(Settings.DAY_NIGHT_MODE))){
            if (Preference.newInstance(getActivity()).getString(Settings.DAY_NIGHT_MODE).equals(Settings.NIGHT_MODE)){
                add_img.setImageResource(R.mipmap.add);
                sub_img.setImageResource(R.mipmap.night_sub);
                num_mul_img.setImageResource(R.mipmap.night_mul);
                trans_img.setImageResource(R.mipmap.night_trans);
                mul_other_img.setImageResource(R.mipmap.night_mul_other);
                trans_other_img.setImageResource(R.mipmap.night_trans_other);
                e_img1.setImageResource(R.mipmap.night_e);
                e_img2.setImageResource(R.mipmap.night_et3);
                e_img3.setImageResource(R.mipmap.night_e3);

                et_img1.setImageResource(R.mipmap.night_et1);
                et_img2.setImageResource(R.mipmap.night_et2);
                et_img3.setImageResource(R.mipmap.night_et3);
                et_img4.setImageResource(R.mipmap.night_et4);
            }else {
                add_img.setImageResource(R.mipmap.add);
                sub_img.setImageResource(R.mipmap.sub);
                num_mul_img.setImageResource(R.mipmap.mul);
                trans_img.setImageResource(R.mipmap.trans);
                mul_other_img.setImageResource(R.mipmap.mul_other);
                trans_other_img.setImageResource(R.mipmap.trans_other);
                e_img1.setImageResource(R.mipmap.e);
                e_img2.setImageResource(R.mipmap.et3);
                e_img3.setImageResource(R.mipmap.e3);

                et_img1.setImageResource(R.mipmap.et1);
                et_img2.setImageResource(R.mipmap.et2);
                et_img3.setImageResource(R.mipmap.et3);
                et_img4.setImageResource(R.mipmap.et4);
            }
        }

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
            case R.id.e_img1:
                goToImageDetail(e_img1,R.mipmap.e);
                break;
            case R.id.e_img2:
                goToImageDetail(e_img2,R.mipmap.et3);
                break;
            case R.id.e_img3:
                goToImageDetail(e_img3,R.mipmap.e3);
                break;
            case R.id.et_img1:
                goToImageDetail(et_img1,R.mipmap.et1);
                break;
            case R.id.et_img2:
                goToImageDetail(et_img2,R.mipmap.et2);
                break;
            case R.id.et_img3:
                goToImageDetail(et_img3,R.mipmap.et3);
                break;
            case R.id.et_img4:
                goToImageDetail(et_img4,R.mipmap.et4);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        weChatShare.initAlertDialog(v);
        return false;
    }

    private void goToImageDetail(View view, int res){
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
