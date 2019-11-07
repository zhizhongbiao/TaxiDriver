package com.taxidriver.santos.view;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.squareup.leakcanary.RefWatcher;
import com.taxidriver.santos.R;
import com.taxidriver.santos.R2;
import com.taxidriver.santos.app.MyApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author : WaterFlower.
 * Created on 2017/8/11.
 * Desc :
 */

public abstract class BaseCommonToolActivity extends AppCompatActivity {


    protected static final int DEFAULT_TOOLBAR = 0;
    protected static final int NO_TOOLBAR = -1;

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    private static final String LAYOUT_RADIOGROUP = "RadioGroup";
    private static final String LAYOUT_CARDVIEW = "CardView";

    private Unbinder mUnbinder;
    private View contentView;

    private ToolbarHolder toolbarHolder;
    private FrameLayout containerView;


    /**
     * @return 布局resourceId
     */
    public abstract int getViewLayout();


    /**
     * 子类Toolbar需要复写该方法
     *
     * @return
     */
    protected int getToolBarLayout() {
        return NO_TOOLBAR;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//             透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_base);
        containerView = (FrameLayout) findViewById(R.id.flContainer);
        initToolBar();
        contentView = LayoutInflater.from(this).inflate(getViewLayout(), null, false);
        mUnbinder = ButterKnife.bind(this, contentView);
        addContentView(contentView);
        initView(savedInstanceState, toolbarHolder, getIntent());
    }

    private void initToolBar() {
        RelativeLayout toolBar = (RelativeLayout) findViewById(R.id.rlToobar);
        if (getToolBarLayout() == NO_TOOLBAR) {
            toolBar.setVisibility(View.GONE);
        } else {
            toolBar.setVisibility(View.VISIBLE);
            toolbarHolder = new ToolbarHolder(toolBar);
        }
    }

    protected ToolbarHolder getToolbarHolder() {
        if (toolbarHolder == null) {
            initToolBar();
        }
        return toolbarHolder;
    }


    /**
     * 从intent获取数据，初始化View。或者其他view级第三方控件的初始化,及相关点击事件的绑定
     *
     * @param savedInstanceState
     * @param args
     */
    protected abstract void initView(Bundle savedInstanceState, ToolbarHolder tbHolder, Intent args);

//    @Override
//    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        View view = null;
//        if (name.equals(LAYOUT_FRAMELAYOUT)) {
//            view = new AutoFrameLayout(context, attrs);
//        } else if (name.equals(LAYOUT_LINEARLAYOUT)) {
//            view = new AutoLinearLayout(context, attrs);
//        } else if (name.equals(LAYOUT_RELATIVELAYOUT)) {
//            view = new AutoRelativeLayout(context, attrs);
//        } else if (name.equals(LAYOUT_RADIOGROUP)) {
////            view = new AutoRadioGroup(context, attrs);
//        } else if (name.equals(LAYOUT_CARDVIEW)) {
////            view = new AutoCardView(context, attrs);
//        }
//
//        if (view != null) {
//            return view;
//        }
//        return super.onCreateView(name, context, attrs);
//    }

    protected void addContentView(View view) {
        containerView.addView(view, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
        RefWatcher memoryWatcher = MyApp.getMemoryWatcher(this);
        if (memoryWatcher == null) return;
        memoryWatcher.watch(this);
    }


    public class ToolbarHolder implements View.OnClickListener {
        @BindView(R2.id.ivBack)
        public ImageView ivBack;
        @BindView(R2.id.tvCenterTitle)
        public TextView tvCenterTitle;
        @BindView(R2.id.tvRightTitle)
        public TextView tvRightTitle;
        @BindView(R2.id.tvLeftTitle)
        public TextView tvLeftTitle;
        @BindView(R2.id.ivRight)
        public ImageView ivRight;
        @BindView(R2.id.ivMiddleRight)
        public ImageView ivMiddleRight;
        @BindView(R2.id.rlToobar)
        public RelativeLayout rlToobar;

        public ToolbarHolder(RelativeLayout toolbar) {
            ButterKnife.bind(this, toolbar);
            ivBack.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    }

}
