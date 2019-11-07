package com.taxidriver.santos.widget.statistics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : WaterFlower.
 * Created on 2017/11/16.
 * Desc :
 */

public class CircleShareView extends View {

    private static final String TAG = "CircleShareView";

    private int width;
    private int height;
    private int halfWidth;
    private int halfHeight;
    private int bigRadius;
    private int smallRadius;
    private Paint mBigPaint;
    private RectF rectF;
    private Paint mSmallPaint;

    private List<Float> startAngles = new ArrayList<>();

    private float defaultStartAngle = -90;
    private List<Float> shares;
    private List<Integer> colors;

    public void setDefaultStartAngle(float defaultStartAngle) {
        this.defaultStartAngle = defaultStartAngle;
        invalidate();
    }

    public void setShareAndColor(List<Float> shares, List<Integer> colors) {
        this.shares = shares;
        this.colors = colors;
        invalidate();
    }

    private void setSmallCircleColor(@ColorInt int color) {
        mSmallPaint.setColor(color);
        invalidate();
    }


    public CircleShareView(Context context) {
        this(context, null);
    }

    public CircleShareView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CircleShareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBigPaint();
        initSmallPaint();
    }
//
//    public CircleShareView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initBigPaint();
//        initSmallPaint();
//    }

    private void initBigPaint() {
        mBigPaint = new Paint();
        mBigPaint.setAntiAlias(true);
        mBigPaint.setStyle(Paint.Style.FILL);
        mBigPaint.setColor(Color.RED);


    }

    private void initSmallPaint() {
        mSmallPaint = new Paint();
        mSmallPaint.setAntiAlias(true);
        mSmallPaint.setStyle(Paint.Style.FILL);
        mSmallPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initParams();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        initParams();
    }

    private void initParams() {
        width = getWidth();
        height = getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;
        bigRadius = Math.min(halfHeight, halfWidth);
        smallRadius = bigRadius / 3;
        //RectF是以本身这个View为参照
        rectF = new RectF(halfWidth - bigRadius, halfHeight - bigRadius,
                halfWidth + bigRadius, halfHeight + bigRadius);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (shares == null || shares.isEmpty()) {
//            throw new RuntimeException("List<Float> shares can not be null and its size can not be zero");
            Log.e(TAG, "List<Float> shares can not be null and its size can not be zero" );
            return;
        }

        if (colors == null || colors.isEmpty()) {
//            throw new RuntimeException("List<Integer> colors can not be null and its size can not be zero");
            Log.e(TAG, "List<Integer> colors can not be null and its size can not be zero" );
            return;
        }

        if (colors.size() != shares.size()) {
//            throw new RuntimeException("List<Integer> colors's size must be same with List<Float> shares's  ");
            Log.e(TAG, "List<Integer> colors's size must be same with List<Float> shares's  " );
            return;
        }
        startAngles.clear();

        for (int i = 0; i < shares.size(); i++) {
            if (i == 0) {
                startAngles.add(defaultStartAngle);
            } else {
                startAngles.add(startAngles.get(i - 1) + shares.get(i - 1) * 360);
            }

        }


        for (int i = 0; i < colors.size(); i++) {
            mBigPaint.setColor(colors.get(i));
            canvas.drawArc(rectF, startAngles.get(i), shares.get(i) * 360, true, mBigPaint);
        }
        canvas.drawCircle(halfWidth, halfHeight, smallRadius, mSmallPaint);


    }
}
