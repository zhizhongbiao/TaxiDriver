package com.taxidriver.santos.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Author : WaterFlower.
 * Created on 2017/8/15.
 * Desc :  添加isRefreshing方法
 */

public class RefreshLayout extends SolveRefreshLayout{

    private boolean mIsRefreshing;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isOnRefreshing() {
        return mIsRefreshing;
    }


    @Override
    public void refreshFinish() {
        super.refreshFinish();
        mIsRefreshing = false;
    }

    @Override
    public void froceRefreshToState(boolean refresh) {
        if (refresh) {
            mIsRefreshing = true;
        }
        super.froceRefreshToState(refresh);
    }
}
