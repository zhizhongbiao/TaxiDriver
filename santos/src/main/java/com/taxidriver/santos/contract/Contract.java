package com.taxidriver.santos.contract;


import com.taxidriver.santos.presenter.BasePresenter;
import com.taxidriver.santos.presenter.IPresenter;
import com.taxidriver.santos.view.IView;

/**
 * Filename :  Contract
 * Author   :  zhizhongbiao
 * Date     :  2018/7/7
 * Describe :
 */

public interface Contract {

    interface ITalkView<P extends IPresenter> extends IView<P> {


    }

    abstract class BaseTalkPresenter<V extends IView> extends BasePresenter<V>  {


    }

}
