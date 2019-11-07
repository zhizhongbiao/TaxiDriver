package com.taxidriver.santos.third_party.ui.toast;


import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

/**
 * Created by 朱志强 on 2017/11/13.
 */

public interface PlainToastSetting {
    PlainToastSetting backgroundColor(@ColorInt int color);
    PlainToastSetting backgroundColorRes(@ColorRes int colorRes);
    PlainToastSetting textColor(@ColorInt int color);
    PlainToastSetting textColorRes(@ColorRes int color);
    PlainToastSetting textSizeSp(int sp);
    PlainToastSetting textBold(boolean bold);
    PlainToastSetting processPlainView(ProcessViewCallback callback);
}
