package com.taxidriver.santos.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.taxidriver.santos.presenter.IPresenter;
import com.taxidriver.santos.utils.log.LogUtils;

import java.lang.reflect.ParameterizedType;

/**
 * FileName :  MvpFragment
 * Author   :  zhizhongbiao
 * Date     :  2018/7/20
 * Describe :
 */

public abstract class MvpFragment<P extends IPresenter> extends BaseFragment
        implements IView<P>{

    protected P presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
            LogUtils.e("initPresenter succeeded presenter=" +presenter);
            return presenter;
        } catch (Exception e) {
            LogUtils.e("initPresenter failed  Exception=" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


}
