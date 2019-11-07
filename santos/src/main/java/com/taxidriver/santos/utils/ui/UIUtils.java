package com.taxidriver.santos.utils.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.taxidriver.santos.app.MyApp;
import com.taxidriver.santos.utils.SystemUtils;


public class UIUtils {

    public static Context getContext() {
        return MyApp.getInstance();
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public static int sp2px( float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return MyApp.getMainThreadHandler();
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    public static View inflate(int resId, ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(resId, parent, false);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    public static long getMainThreadId() {
        return MyApp.getMainThreadId();
    }


    //判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * 显示软键盘
     *
     * @param text
     */
    public static void showKeyboard(EditText text) {
        InputMethodManager inputMethodManager = (InputMethodManager) text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!inputMethodManager.isActive()) {
            inputMethodManager.showSoftInput(text, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param text
     */
    public static void hideKeyboard(EditText text) {
        InputMethodManager inputMethodManager = (InputMethodManager) text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(text.getApplicationWindowToken(), 0);
        }
    }

    public static <T extends View> T findView(View contentView, int viewId) {
        return (T) contentView.findViewById(viewId);
    }

    public static void resizeView(float width, float height, View targetView) {
        float scaleFactor = width / SystemUtils.getWidthPixel();
        float actualHeight = height / scaleFactor;
        targetView.getLayoutParams().height = (int) actualHeight;
    }

    public static void resizeImageView(Drawable drawable, ImageView targetView) {
        float scaleFactor = drawable.getIntrinsicWidth() / SystemUtils.getWidthPixel();
        float actualHeight = drawable.getIntrinsicHeight() / scaleFactor;
        targetView.getLayoutParams().height = (int) actualHeight;
    }

    public static void resizeImageView(int resourseId, ImageView targetView) {
        Drawable drawable = UIUtils.getDrawable(resourseId);
        resizeImageView(drawable, targetView);
    }

    public static void resizeImageViewSmall(Drawable drawable, ImageView targetView) {
        float scaleFactor = drawable.getIntrinsicWidth() / SystemUtils.getWidthPixel();
        float actualHeight;
        if (scaleFactor > 1) {
            actualHeight = drawable.getIntrinsicHeight() / scaleFactor;
        } else {
            if (targetView.getMeasuredWidth() == SystemUtils.getWidthPixel()) {
                actualHeight = drawable.getIntrinsicHeight() / scaleFactor;
            } else {
                float bili = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
                actualHeight = targetView.getMeasuredWidth() / bili;
            }
        }
        targetView.getLayoutParams().height = (int) actualHeight;
    }
}
