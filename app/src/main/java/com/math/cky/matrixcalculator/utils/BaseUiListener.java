package com.math.cky.matrixcalculator.utils;

import android.content.Context;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by office on 2017/12/11.
 */

public class BaseUiListener implements IUiListener {

    private Context context;

    public BaseUiListener(Context context){
        this.context=context;
    }

    @Override
    public void onComplete(Object o) {
        Toast.makeText(context, o.toString()+"", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context, uiError.errorMessage+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
    }
}
