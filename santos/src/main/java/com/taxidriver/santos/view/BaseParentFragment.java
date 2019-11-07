package com.taxidriver.santos.view;


import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.taxidriver.santos.utils.log.LogUtils;

/**
 * Filename :  CommonRvFragment
 * Author   :  zhizhongbiao
 * Date     :  2018/6/29
 * Describe :
 */

public abstract class BaseParentFragment extends BaseFragment {

    protected FragmentManager fm;
    private BaseFragment lastFragemnt;

    protected abstract  int getFgContainerResId();


    @Override
    protected void initView(Bundle savedInstanceState, Bundle args) {
        fm = getChildFragmentManager();
    }


    protected void showFg(BaseFragment fragment) {
        LogUtils.e("showFg     fragment=" + fragment);

        if (fragment == null || lastFragemnt == fragment) {
            return;
        }
        FragmentTransaction transaction = fm.beginTransaction();

        if (lastFragemnt != null) {
            transaction.hide(lastFragemnt);
        }

        String myTag = fragment.getMyTag();
        if (!fragment.isAdded() && null == fm.findFragmentByTag(myTag)) {
            transaction.add(getFgContainerResId(), fragment, myTag);
        }

        //调用commitAllowingStateLoss是为了防止，fragment在错误的时间切换导致的状态异常：
        transaction.show(fragment).commitAllowingStateLoss();
        lastFragemnt = fragment;

    }


}
