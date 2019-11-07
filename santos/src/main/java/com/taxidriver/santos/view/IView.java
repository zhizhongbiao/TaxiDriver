package com.taxidriver.santos.view;


import com.taxidriver.santos.presenter.IPresenter;

/**
 * Filename :  IView
 * Author   :  zhizhongbiao
 * Date     :  2018/7/7
 * Describe :
 */

public interface IView<P extends IPresenter> {

    P initPresenter();

    void initLoadingDialog();

    boolean isLoadingDialogNeeded(boolean isNeeded);

    void startLoading();

    void stopLoading();
}
