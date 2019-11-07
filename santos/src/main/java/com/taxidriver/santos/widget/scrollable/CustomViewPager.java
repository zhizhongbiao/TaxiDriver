package com.taxidriver.santos.widget.scrollable;

import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Author : WaterFlower.
 * Created on 2017/8/24.
 * Desc :  不消费不拦截
 */

public class CustomViewPager extends ViewPager {


    private boolean mIsScrollable = true;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mIsScrollable) return false;
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mIsScrollable)return false;
        return super.onInterceptTouchEvent(ev);
    }

    public void isScrollable(boolean isScrollable) {

        this.mIsScrollable = isScrollable;
    }


}
