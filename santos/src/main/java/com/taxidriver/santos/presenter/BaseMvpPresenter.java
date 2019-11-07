package com.taxidriver.santos.presenter;

import com.taxidriver.santos.model.IModel;
import com.taxidriver.santos.network.bean.BaseBean;
import com.taxidriver.santos.utils.log.LogUtils;
import com.taxidriver.santos.view.IView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author : WaterFlower.
 * Created on 2017/6/18.
 * Desc :
 */

public class BaseMvpPresenter<V extends IView, M extends IModel>
        extends BasePresenter<V, M>
        implements MvpCallback {

    private M model;

    @Override
    public M initModel() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = type.getActualTypeArguments();
        LogUtils.e("initModel actualTypeArguments=" + actualTypeArguments);
        if (actualTypeArguments == null || actualTypeArguments.length < 2) {
            return null;
        }
        Class<? extends IPresenter> presenterClass = (Class<? extends IPresenter>) actualTypeArguments[0];
        try {
            this.model = (M) presenterClass.newInstance();
            LogUtils.e("initModel succeeded presenter=" + model);
            model.setMvpCallback(this);
            return model;
        } catch (Exception e) {
            LogUtils.e("initModel failed  Exception=" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onCreate() {
        initModel();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        getView().stopLoading();
        if (model != null) {
            model.onDetachedPresenter();
            model.setMvpCallback(null);
            model = null;
        }
        super.onDestroy();
    }


    @Override
    public void beforeRequest() {
        getView().startLoading();
    }

    @Override
    public void onSucceed(BaseBean bean) {
        getView().stopLoading();
    }

    @Override
    public void onError(Throwable e) {
        getView().stopLoading();
    }
}
