/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */
package com.math.cky.matrixcalculator.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.math.cky.matrixcalculator.R;
import com.math.cky.matrixcalculator.utils.WeChatShare;

import java.util.List;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class AnimationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int COUNT = 100;

    private final Context mContext;
    private int mCurrentItemId = 0;
    private List<String> timeList;
    private List<String> infoList;
    private WeChatShare weChatShare;


    public AnimationListAdapter(Context context, List<String> timeList, List<String> infoList, WeChatShare weChatShare) {
        mContext = context;
        this.timeList=timeList;
        this.infoList=infoList;
        this.weChatShare=weChatShare;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView info;
        public final CardView card;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.history_time);
            info = (TextView) view.findViewById(R.id.history_info);
            card = (CardView) view.findViewById(R.id.card);


        }
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.simple_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleViewHolder simpleViewHolder= (SimpleViewHolder) holder;
        simpleViewHolder.info.setText(infoList.get(position));
        simpleViewHolder.title.setText(timeList.get(position));
        simpleViewHolder.info.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                weChatShare.initAlertDialog(v);
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }

}
