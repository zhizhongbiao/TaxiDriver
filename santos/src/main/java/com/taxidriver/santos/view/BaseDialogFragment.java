package com.taxidriver.santos.view;


import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.squareup.leakcanary.RefWatcher;
import com.taxidriver.santos.app.MyApp;
import com.taxidriver.santos.utils.log.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Filename :  BaseDialogFragment
 * Author   :  zhizhongbiao
 * Date     :  2018/7/7
 * Describe :
 */

public abstract class BaseDialogFragment extends DialogFragment
        implements DialogInterface.OnShowListener
        , DialogInterface.OnKeyListener {

    private View contentView;
    private double[] factors = new double[2];
    private Unbinder mUnbinder;
    protected Handler mainThreadHandler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initHandler();
        super.onCreate(savedInstanceState);
    }

    protected void initHandler() {
        mainThreadHandler = MyApp.getMainThreadHandler();
    }

    /**
     * @return 布局resourceId
     */
    public abstract int getViewLayout();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(getViewLayout(), container, false);
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //关闭点击外面消失的效果
        getDialog().setCanceledOnTouchOutside(true);
        //去掉低版本部分机型顶部出现一条蓝线的现象
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置监听对话框显示
        getDialog().setOnShowListener(this);
        //监听显示Dialog时的键盘事件
        getDialog().setOnKeyListener(this);

        mUnbinder = ButterKnife.bind(this, contentView);
        return contentView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState, getArguments());
    }

    /**
     * 初始化View。或者其他view级第三方控件的初始化,及相关点击事件的绑定
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState, Bundle args);


    /**
     * 在此方法设置参数有效
     */
    @Override
    public void onResume() {
        setLayoutParams();
        super.onResume();
    }


    private void setLayoutParams() {
        double[] layoutFactors = getLayoutFactor(factors);
        if (layoutFactors == null || layoutFactors.length == 0) return;
        double w = 0, h = 0;
        w = layoutFactors[0] > 0 ? layoutFactors[0] : layoutFactors[1] > 0 ? layoutFactors[1] : -1;
        if (w < 0) return;
        h = layoutFactors[1] > 0 ? layoutFactors[1] : w;
        setDgFragmentLayoutParams(w, h);
    }


    /**
     * 获取子类返回的比例因子,元素0为宽的比例因子,元素1为高的比列因子
     *
     * @param factors
     * @return
     */
    protected abstract double[] getLayoutFactor(double[] factors);


    /**
     * 设置Dialogd的大小，在dialog.show()之后调用
     *
     * @param hFactor 高的比例因子
     * @param wFactor 宽的比例因子
     */
    private void setDgFragmentLayoutParams(double wFactor, double hFactor) {
        int width = ((int) (getScreenWidth() * wFactor));
//        int height = ((int) ((getScreenHeight()-statusBarHeight) * hFactor));
        int height = ((int) (getScreenHeight() * hFactor));
        getDialog().getWindow().setLayout(width, height);
//        getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 获取屏幕宽度像素
     *
     * @return
     */
    protected int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度像素
     *
     * @return
     */
    protected int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void onShow(DialogInterface dialog) {

    }


    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        LogUtils.e("onKey  keyCode=" + event.getKeyCode());
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
        RefWatcher memoryWatcher = MyApp.getMemoryWatcher(getContext());
        if (memoryWatcher == null) return;
        memoryWatcher.watch(this);
    }


}
