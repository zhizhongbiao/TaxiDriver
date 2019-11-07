package com.taxidriver.santos.view.adapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;


import androidx.viewpager.widget.PagerAdapter;

import com.taxidriver.santos.utils.ui.UIUtils;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Author : WaterFlower.
 * Created on 2017/8/11.
 * Desc :
 */
public abstract class CommonPagerAdapter<T> extends PagerAdapter {
    protected List<T> mDataList;
    protected WeakReference<Context> contextRef;
    private int resourceId;

    public CommonPagerAdapter(List<T> mDataList, Context context) {
        this.mDataList = mDataList;
        this.contextRef = new WeakReference<Context>(context);
    }

    public CommonPagerAdapter(List<T> mDataList, Context context, int resourceId) {
        this.mDataList = mDataList;
        this.contextRef = new WeakReference<Context>(context);
        this.resourceId = resourceId;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T data = mDataList.get(position);
        View contentView = null;
        if (resourceId != 0) {
            contentView = UIUtils.inflate(resourceId);
        }
        View view = bindView(contextRef.get(), contentView, data);
        container.addView(view);
        return view;
    }

    /**
     * 保定数据到内容view
     *
     * @param context
     * @param contentView 根据resourceId inflate出来的View,只有在resourceId不为0的情况下才有值
     * @param dataVo      position对应的数据对象
     * @return 绑定好数据及其他操作的的View
     */
    protected abstract View bindView(Context context, View contentView, T dataVo);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList) {
        this.mDataList = dataList;
    }
}
