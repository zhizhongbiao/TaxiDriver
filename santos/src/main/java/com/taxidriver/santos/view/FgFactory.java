package com.taxidriver.santos.view;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.taxidriver.santos.utils.log.LogUtils;

/**
 * FileName :  FgFactory
 * Author   :  zhizhongbiao
 * Date     :  2019/5/15
 * Describe :
 */

public class FgFactory {

    public static <F extends BaseFragment> F getFgInstance(FragmentManager fm, Class<F> fgClz) {

        if (fm == null && null == fgClz) {
            LogUtils.e("getFgInstance  生成Fg出错  fgClz=" + fgClz + "    fm=" + fm);
            return null;
        }

        Fragment fragment = fm.findFragmentByTag(fgClz.getName());

        LogUtils.e("getFgInstance  从FgM中找出来的Fg  fgClz=" + fgClz + "    fragment=" + fragment);

        if (fragment == null) {
            try {
                return fgClz.newInstance();
            } catch (Exception e) {
                LogUtils.e("getFgInstance  生成Fg出错  fgClz=" + fgClz.getName());
                e.printStackTrace();
            }
        }


        return ((F) fragment);
    }
}
