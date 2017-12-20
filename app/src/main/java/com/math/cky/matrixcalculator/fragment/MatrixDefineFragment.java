package com.math.cky.matrixcalculator.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.text.Html;
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
 * Created by chen on 2017/6/11.
 */
public class MatrixDefineFragment extends Fragment implements View.OnLongClickListener{

    private ImageView imageView;
    private CardView cardview1;
    private LinearLayout defineLayout1,defineLayout2,defineLayout4;
    private TextView matrixDefineInfo4;
    private WeChatShare weChatShare;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.matrix_define_layout,container,false);
        TextView textView2= (TextView) view.findViewById(R.id.matrix_define_info2);
        TextView textView3= (TextView) view.findViewById(R.id.matrix_define_info3);

        defineLayout1= (LinearLayout) view.findViewById(R.id.define_layout1);
        defineLayout2= (LinearLayout) view.findViewById(R.id.define_layout2);
        defineLayout4= (LinearLayout) view.findViewById(R.id.define_layout4);
        matrixDefineInfo4= (TextView) view.findViewById(R.id.matrix_define_info4);

        textView2.setText(Html.fromHtml("由 m × n 个数a<sub>ij</sub>排成的m行n列的数表称为m行n列的矩阵，简称m × n矩阵。记作："));
        textView3.setText(Html.fromHtml("这m×n 个数称为矩阵A的元素，简称为元，数a<sub>ij</sub>位于矩阵A的第i行第j列，称为矩阵A的(i,j)元，以数 a<sub>ij</sub>为(i,j)元的矩阵可记为(a<sub>ij</sub>)或(a<sub>ij</sub>)<sub>m×n</sub>，m×n矩阵A也记作A<sub>mn</sub>。"));
        imageView= (ImageView) view.findViewById(R.id.matrix_img);


        if (!TextUtils.isEmpty(Preference.newInstance(getActivity()).getString(Settings.DAY_NIGHT_MODE))){
            if (Preference.newInstance(getActivity()).getString(Settings.DAY_NIGHT_MODE).equals(Settings.NIGHT_MODE)){
                imageView.setImageResource(R.mipmap.night_matrix1);
            }else {
                imageView.setImageResource(R.mipmap.matrix1);
            }
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpaceImageDetailActivity.class);
                intent.putExtra("image", R.mipmap.matrix1);
                int[] location = new int[2];
                imageView.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", imageView.getWidth());
                intent.putExtra("height", imageView.getHeight());
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
            }
        });


        defineLayout1.setOnLongClickListener(this);
        defineLayout2.setOnLongClickListener(this);
        matrixDefineInfo4.setOnLongClickListener(this);
        defineLayout4.setOnLongClickListener(this);
        weChatShare=new WeChatShare(getActivity());
        return view;

    }


    @Override
    public boolean onLongClick(View v) {
        weChatShare.initAlertDialog(v);
        return false;
    }
}
