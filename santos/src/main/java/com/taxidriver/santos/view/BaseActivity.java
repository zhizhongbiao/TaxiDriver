package com.taxidriver.santos.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.leakcanary.RefWatcher;
import com.taxidriver.santos.app.MyApp;
import com.taxidriver.santos.utils.log.LogUtils;

import butterknife.ButterKnife;

/**
 * Author : WaterFlower.
 * Created on 2017/8/11.
 * Desc :
 */

public abstract class BaseActivity extends AppCompatActivity {

    private MyApp application;
    protected Handler mainThreadHandler;

    /**
     * @return 布局resourceId
     */
    public abstract int getViewLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        application = (MyApp) getApplication();
        application.addActivity(this);
        initHandler();
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // 透明状态栏
        //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //             透明导航栏
        //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(getViewLayout());
        ButterKnife.bind(this);
        initView(savedInstanceState, getIntent());
    }

    private void initHandler() {
        mainThreadHandler = MyApp.getMainThreadHandler();
    }

    /**
     * 从intent获取数据，初始化View。或者其他view级第三方控件的初始化,及相关点击事件的绑定
     *
     * @param savedInstanceState
     * @param args
     */
    protected abstract void initView(Bundle savedInstanceState, Intent args);

    @Override
    protected void onDestroy() {
        application.removeActivity(this);
        super.onDestroy();

        RefWatcher memoryWatcher = MyApp.getMemoryWatcher(this);
        if (memoryWatcher == null) return;
        memoryWatcher.watch(this);
    }


    protected <T extends View> T findThisActivityView(@IdRes int viewId, Class<T> clz) {
        return ((T) findViewById(viewId));
    }

    protected <T extends View> T findViewInParent(View parentView, @IdRes int viewId, Class<T> clz) {
        if (clz == null || null == parentView || viewId < 0) {
            return null;
        }

        return ((T) parentView.findViewById(viewId));
    }

    protected void killAllActivity() {
        application.killAllActivity();
    }


    public String getMyTag() {
        String name = this.getClass().getName();
        LogUtils.e("getMyTag   name=" + name);
        return name;
    }

}
