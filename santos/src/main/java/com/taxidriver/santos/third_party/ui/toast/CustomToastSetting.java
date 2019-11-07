package com.taxidriver.santos.third_party.ui.toast;


import android.view.View;

import androidx.annotation.LayoutRes;

/**
 * Created by 朱志强 on 2017/11/13.
 */

public interface CustomToastSetting {

    CustomToastSetting view(View view);
    CustomToastSetting view(@LayoutRes int layout);
    CustomToastSetting processCustomView(ProcessViewCallback callback);
}
