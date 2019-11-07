package com.taxidriver.santos.view.adapter;

import android.content.Context;

import android.view.View;

import androidx.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * FileName :  MyBaseAdapter
 * Author   :  zhizhongbiao
 * Date     :  2019/6/27
 * Describe :
 */

public abstract class MyBaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {


    protected Context context;
    protected int type;

    protected abstract @LayoutRes
    int getLayoutResId();

    public MyBaseAdapter() {
        super(0);
        super.mLayoutResId = getLayoutResId();
    }


    @Override
    protected BaseViewHolder createBaseViewHolder(View view) {
        return new BaseViewHolder(view);
    }


    public <A extends MyBaseAdapter, D> void init(Context context, int type) {
        this.context=context;
        this.type=type;
    }
}
