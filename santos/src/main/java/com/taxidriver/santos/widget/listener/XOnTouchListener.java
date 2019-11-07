package com.taxidriver.santos.widget.listener;

import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * FileName :  XOnTouchListener
 * Author   :  zhizhongbiao
 * Date     :  2019/5/13
 * Describe :
 */

public class XOnTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ((TextView) view).setTextSize(((TextView) view).getTextSize() + 4);
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
            ((TextView) view).setTextSize(((TextView) view).getTextSize() - 4);
        }
        return false;
    }


}
