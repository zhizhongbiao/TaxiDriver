package com.taxidriver.santos.widget.statistics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

import com.taxidriver.santos.R;


/**
 * Author : WaterFlower.
 * Created on 2017/11/16.
 * Desc :
 */

public class CircleView extends View {

    private static final String TAG = "CircleView";

    private int width;
    private int height;
    private int halfWidth;
    private int halfHeight;
    private Paint mPaint;
    private int radius;
    private int circleColor;


    private void setCircleColor(@ColorInt int color) {
        mPaint.setColor(color);
        invalidate();
    }


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView, defStyleAttr, 0);
        circleColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.RED);
        initPaint();
        typedArray.recycle();


    }
//
//    public CircleShareView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initBigPaint();
//        initPaint();
//    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(circleColor);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initParams();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
//        initParams();
    }

    private void initParams() {
        width = getWidth();
        height = getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;
        radius = Math.min(halfHeight, halfWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(halfWidth, halfHeight, radius, mPaint);

    }
}
