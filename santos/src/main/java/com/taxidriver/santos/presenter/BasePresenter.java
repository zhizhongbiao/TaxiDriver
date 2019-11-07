package com.taxidriver.santos.presenter;

import android.os.Handler;
import android.os.Looper;


import com.taxidriver.santos.app.MyApp;
import com.taxidriver.santos.model.IModel;
import com.taxidriver.santos.utils.log.LogUtils;
import com.taxidriver.santos.view.IView;

import java.lang.ref.WeakReference;

/**
 * Author : WaterFlower.
 * Created on 2016/9/18.
 * Desc :
 */

public abstract class BasePresenter<V extends IView,M extends IModel> implements IPresenter<V,M> {

    protected String viewClassName;
    protected Handler mainThreadHandler;
    private WeakReference<V> viewReference;

    @Override
    public void attachView(V view) {
        viewClassName = view.getClass().getName();
        viewReference = new WeakReference<V>(view);
    }


    @Override
    public boolean isViewAttached() {
        return viewReference != null && viewReference.get() != null;
    }


    @Override
    public V getView() {
        if (isViewAttached()) {
            return viewReference.get();
        } else {
            throw new RuntimeException(" View is not attached to Presenter ");
        }

    }


    @Override
    public void detachView() {
        if (viewReference != null) {
            viewReference.clear();
            viewReference = null;
        }
    }


    @Override
    public void onCreate() {
        initHandler();
        LogUtils.e(this.getClass().getSimpleName() + " ---  BasePresenter  onCreate");
    }

    private void initHandler() {
        mainThreadHandler = MyApp.getMainThreadHandler();
    }

    @Override
    public void onStart() {
        LogUtils.e(this.getClass().getSimpleName() + " ---  BasePresenter  onStart");
    }

    @Override
    public void onResume() {
        LogUtils.e(this.getClass().getSimpleName() + " ---  BasePresenter  onResume");
    }

    @Override
    public void onPause() {
        LogUtils.e(this.getClass().getSimpleName() + " ---  BasePresenter  onPause");
    }

    @Override
    public void onStop() {
        LogUtils.e(this.getClass().getSimpleName() + " ---  BasePresenter  onStop");
    }


    @Override
    public void onDestroy() {
        LogUtils.e(this.getClass().getSimpleName()+" ---  BasePresenter  onDestroy");
        detachView();

    }


}
