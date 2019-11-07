package com.taxidriver.santos.view.adapter;



import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author : WaterFlower.
 * Created on 2017/8/24.
 * Desc : 只是适配一下
 */

public abstract class AutoBaseQuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T,K> {

    public AutoBaseQuickAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public AutoBaseQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    public AutoBaseQuickAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected K createBaseViewHolder(View view) {
        return super.createBaseViewHolder(view);
    }
}
