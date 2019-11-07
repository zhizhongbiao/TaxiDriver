package com.taxidriver.santos.view;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.leakcanary.RefWatcher;
import com.taxidriver.santos.app.MyApp;
import com.taxidriver.santos.utils.log.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author : WaterFlower.
 * Created on 2017/8/11.
 * Desc :
 */

public abstract class BaseFragment extends Fragment {
    protected Unbinder mUnbinder;
    protected View contentView;
    protected Handler mainThreadHandler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initHandler();
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    protected void initHandler() {
        mainThreadHandler = MyApp.getMainThreadHandler();
    }

    /**
     * @return 布局resourceId
     */
    public abstract int getViewLayout();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getViewLayout(), container, false);
            mUnbinder = ButterKnife.bind(this, contentView);
        }
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState, getArguments());

    }

    protected <T extends View> T findThisFgView(@IdRes int viewId, Class<T> clz) {
        if (clz == null || null == contentView || viewId < 0) {
            return null;
        }
        return ((T) contentView.findViewById(viewId));
    }

    protected <T extends View> T findViewInParent(View parentView, @IdRes int viewId, Class<T> clz) {
        if (clz == null || null == parentView || viewId < 0) {
            return null;
        }

        return ((T) parentView.findViewById(viewId));
    }

    /**
     * 初始化View。或者其他view级第三方控件的初始化,及相关点击事件的绑定
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState, Bundle args);


    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
        RefWatcher memoryWatcher = MyApp.getMemoryWatcher(getContext());
        if (memoryWatcher == null) return;
        memoryWatcher.watch(this);
    }


    public String getMyTag() {
        String name = this.getClass().getName();
        LogUtils.e("getMyTag   name=" + name);
        return name;
    }
}
