package com.taxidriver.santos.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * FileName :  MyScrollView
 * Author   :  zhizhongbiao
 * Date     :  2019/1/15
 * Describe :
 */

public class ListenedScrollView extends ScrollView {
    public ListenedScrollView(Context context) {
        this(context, null);
    }

    public ListenedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListenedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.onScrollChanged(t);
        }

    }

    private OnScrollChangedListener listener;

    public void setListener(OnScrollChangedListener listener) {
        this.listener = listener;
    }

   public interface OnScrollChangedListener {
        void onScrollChanged(int distanceFromTop);
    }
}
