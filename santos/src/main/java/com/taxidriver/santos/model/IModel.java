package com.taxidriver.santos.model;


import com.taxidriver.santos.presenter.MvpCallback;

/**
 * Filename :  IModel
 * Author   :  zhizhongbiao
 * Date     :  2018/7/7
 * Describe :
 */

public interface IModel<C extends MvpCallback> {
    void setMvpCallback(C mvpCallback);
    void onDetachedPresenter();
}
