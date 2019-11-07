package com.taxidriver.santos.view;


import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.taxidriver.santos.R;
import com.taxidriver.santos.presenter.BasePresenter;
import com.taxidriver.santos.presenter.IPresenter;
import com.taxidriver.santos.reveiver.NetworkStateEvent;
import com.taxidriver.santos.utils.log.LogUtils;
import com.taxidriver.santos.utils.ui.ToastUtil;


import java.lang.reflect.ParameterizedType;


/**
 * Author : WaterFlower.
 * Created on 2017/8/21.
 * Desc :
 */

public abstract class MvpDialogFragment<P extends IPresenter> extends BaseDialogFragment implements IView<P> {

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
            LogUtils.e("initPresenter 成功 presenter=" + presenter);
            return presenter;
        } catch (Exception e) {
            LogUtils.e("initPresenter  Exception=" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
