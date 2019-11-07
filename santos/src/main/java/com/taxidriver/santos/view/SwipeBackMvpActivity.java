package com.taxidriver.santos.view;

import android.os.Bundle;
import android.view.View;

import com.taxidriver.santos.presenter.IPresenter;
import com.taxidriver.santos.widget.swipe.SwipeBack;
import com.taxidriver.santos.widget.swipe.SwipeBackHelper;
import com.taxidriver.santos.widget.swipe.SwipeBackLayout;
import com.taxidriver.santos.widget.swipe.Utils;


/**
 * Author : WaterFlower.
 * Created on 2017/9/27.
 * Desc :  右滑退出
 */

public abstract class SwipeBackMvpActivity<P extends IPresenter> extends MvpActivity<P> implements SwipeBack {

    private SwipeBackHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    /**
     * 设置禁止滑动退出
     *
     * @param enable
     */
    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

}
