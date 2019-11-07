package com.taxidriver.santos.widget.scrollable;

import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : WaterFlower.
 * Created on 2017/8/24.
 *
 */
public class MyNonScrollRecyclerView extends RecyclerView {
    public MyNonScrollRecyclerView(Context context) {
        super(context);
    }

    public MyNonScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNonScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 不对触摸时间做事件,只交给父布局来处理
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){

        return false;
    }
}
