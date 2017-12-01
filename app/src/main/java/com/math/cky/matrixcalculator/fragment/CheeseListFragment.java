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
 * Created by chen on 2017/6/10.
 */
public class CheeseListFragment extends Fragment {
    private String title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_layout,container,false);
        TextView des= (TextView) view.findViewById(R.id.des);
        des.setText(title);
        return view;
    }
    public void setTitle(String title){
        this.title=title;
    }
}
