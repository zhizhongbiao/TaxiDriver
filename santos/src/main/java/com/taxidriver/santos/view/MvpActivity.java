package com.taxidriver.santos.view;

import android.os.Bundle;


import com.taxidriver.santos.presenter.IPresenter;
import com.taxidriver.santos.utils.log.LogUtils;

import java.lang.reflect.ParameterizedType;

/**
 * Filename :  MvpActivity
 * Author   :  zhizhongbiao
 * Date     :  2018/7/7
 * Describe :
 */

public abstract class MvpActivity<P extends IPresenter> extends BaseActivity
        implements IView<P> {

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.e("MvpActivity  onCreate");
        presenter = initPresenter();
        getLifecycle().addObserver(presenter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public P initPresenter() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<? extends IPresenter> presenterClass = (Class<? extends IPresenter>) type.getActualTypeArguments()[0];
        try {
            this.presenter = (P) presenterClass.newInstance();
            presenter.attachView(this);
            return presenter;
        } catch (Exception e) {
            LogUtils.e("initPresenter  Exception=" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onStart() {
        LogUtils.e("MvpActivity  onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        LogUtils.e("MvpActivity  onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        LogUtils.e("MvpActivity  onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        LogUtils.e("MvpActivity  onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.e("MvpActivity  onDestroy");
        super.onDestroy();
    }
}
