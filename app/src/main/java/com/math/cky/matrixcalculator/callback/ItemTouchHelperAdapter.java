package com.math.cky.matrixcalculator.callback;

/**
 * Created by Administrator on 2016/5/2.
 */
public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
