package com.taxidriver.santos.presenter;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.taxidriver.santos.model.IModel;
import com.taxidriver.santos.view.IView;

/**
 * Filename :  IPresenter
 * Author   :  zhizhongbiao
 * Date     :  2018/7/7
 * Describe :
 */

public interface IPresenter<V extends IView,M extends IModel> extends LifecycleObserver {

    void attachView(V view);

    M initModel();

    boolean isViewAttached();


    V getView();


    void detachView();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();


}
