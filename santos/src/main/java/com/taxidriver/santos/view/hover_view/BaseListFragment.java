package com.taxidriver.santos.view.hover_view;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.taxidriver.santos.utils.log.LogUtils;
import com.taxidriver.santos.view.BaseFragment;
import com.taxidriver.santos.widget.listener.OnKeyEventDispatchListener;

/**
 * FileName :  BaseListFragment
 * Author   :  zhizhongbiao
 * Date     :  2018/11/16
 * Describe :
 */

public abstract class BaseListFragment extends BaseFragment
        implements OnKeyEventDispatchListener, Runnable {

    protected Handler handler;
    protected LinearLayoutManager layoutManager;
    protected int currentSelectedPos;

    protected abstract ListBaseAdapter getAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        handler = new Handler(Looper.getMainLooper());
        layoutManager = new LinearLayoutManager(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
    }

    @Override
    public boolean onKeyEventDispatch(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            LogUtils.e("keyCode=" + event.getKeyCode());
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    updateFocusState(false);
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    updateFocusState(true);
                    return true;
            }
        }
        return false;
    }

    protected void updateFocusState(boolean isRight) {
        if (getAdapter() == null) {
            return;
        }
        handler.removeCallbacks(this);
        handler.postDelayed(this, 2800);
        if (getAdapter().getHoveredPosition() == -1) {
            getAdapter().setHoveredPosition(currentSelectedPos);
        } else {
            getAdapter().setHoveredPosition(isRight);
            currentSelectedPos = getAdapter().getHoveredPosition();
        }

        scrollToSelectedPos(currentSelectedPos);
        getAdapter().notifyDataSetChanged();

    }

    protected void scrollToSelectedPos(int position) {
        LogUtils.e("scrollToSelectedPos: position==" + position);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        if (position < firstItemPosition) {
            layoutManager.scrollToPositionWithOffset(position, 0);
        } else if (lastVisibleItemPosition < position) {
//            layoutManager.scrollToPositionWithOffset(position, rvContactsHeight - 95);
            layoutManager.scrollToPositionWithOffset(position, 380);
        }
    }

    @Override
    public void run() {
        if (getAdapter() != null) {
            getAdapter().setHoveredPosition(-1);
            getAdapter().notifyDataSetChanged();
        }

    }


}
