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
import android.widget.TextView;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.conf.Settings;
import com.math.cky.matrixcalculator.ui.SpaceImageDetailActivity;
import com.math.cky.matrixcalculator.utils.Preference;
import com.math.cky.matrixcalculator.utils.WeChatShare;

/**
 * Created by chen on 2017/6/14.
 */
public class DeterminantFragment extends Fragment implements View.OnLongClickListener {

    private ImageView det_img,det_img1;
    private LinearLayout det_layout1,det_layout2;
    private TextView matrix_det_nature1,matrix_det_nature2,matrix_det_nature3,matrix_det_nature4;
    private WeChatShare weChatShare;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.determinant_layout,container,false);
        det_img= (ImageView) view.findViewById(R.id.det_img);
        det_img1= (ImageView) view.findViewById(R.id.det_img1);
        det_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToImageDetail(det_img,R.mipmap.det);
            }
        });

        det_layout1= (LinearLayout) view.findViewById(R.id.det_layout1);
        det_layout2= (LinearLayout) view.findViewById(R.id.det_layout2);

        matrix_det_nature1= (TextView) view.findViewById(R.id.matrix_det_nature1);
        matrix_det_nature2= (TextView) view.findViewById(R.id.matrix_det_nature2);
        matrix_det_nature3= (TextView) view.findViewById(R.id.matrix_det_nature3);
        matrix_det_nature4= (TextView) view.findViewById(R.id.matrix_det_nature4);

        det_layout1.setOnLongClickListener(this);
        det_layout2.setOnLongClickListener(this);
        matrix_det_nature1.setOnLongClickListener(this);
        matrix_det_nature2.setOnLongClickListener(this);
        matrix_det_nature3.setOnLongClickListener(this);
        matrix_det_nature4.setOnLongClickListener(this);
        weChatShare=new WeChatShare(getActivity());

        if (!TextUtils.isEmpty(Preference.newInstance(getActivity()).getString(Settings.DAY_NIGHT_MODE))){
            if (Preference.newInstance(getActivity()).getString(Settings.DAY_NIGHT_MODE).equals(Settings.NIGHT_MODE)){
                det_img.setImageResource(R.mipmap.night_det);
                det_img1.setImageResource(R.mipmap.det1);
            }else {
                det_img.setImageResource(R.mipmap.det);
                det_img1.setImageResource(R.mipmap.det1);
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
    public boolean onLongClick(View v) {
        weChatShare.initAlertDialog(v);
        return false;
    }
}
