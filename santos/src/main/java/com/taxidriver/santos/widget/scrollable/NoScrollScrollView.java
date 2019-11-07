package com.taxidriver.santos.widget.scrollable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Author : WaterFlower.
 * Created on 2017/8/24.
 * Desc :  拦截事件，防止传给内部嵌套view - 解决recyclerView滑动惯性消失的问题
 */

public class NoScrollScrollView extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;

    public NoScrollScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoScrollScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoScrollScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.onScrollChangedListener == null) return;
        int scrollDistanceY = t - oldt;
        if (oldt > t) {
            onScrollChangedListener.onScrollUp(scrollDistanceY);
        } else if (oldt < t) {
            onScrollChangedListener.onScrollDown(scrollDistanceY);

        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    private OnScrollChangedListener onScrollChangedListener;

    public interface OnScrollChangedListener {
        public abstract void onScrollUp(int scrolledDistanceY);

        public abstract void onScrollDown(int scrolledDistanceY);
    }

}