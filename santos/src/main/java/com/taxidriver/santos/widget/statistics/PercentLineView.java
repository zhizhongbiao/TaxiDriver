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

public class PercentLineView extends View {


    private static final String TAG = "PercentLineView";

    private int width;
    private int height;


    private Paint mPaint;
    private float temp;

    private float percentage = 1f;
    private int halfHeight;
    private int mLineColor;
    private int mLineHeight;

    public PercentLineView setPercentage(float percentage) {
        this.percentage = percentage;
        updateView();
        return this;
    }

    private PercentLineView setLinetColor(@ColorInt int color) {
        mPaint.setColor(color);
        updateView();
        return this;
    }


    private void updateView() {
        invalidate();
    }


    public PercentLineView(Context context) {
        this(context, null);
    }

    public PercentLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentLineView, defStyleAttr, 0);
        mLineColor = typedArray.getColor(R.styleable.PercentLineView_line_color, Color.RED);
        mLineHeight = typedArray.getDimensionPixelSize(R.styleable.PercentLineView_line_height, 10);
        initPaint(mLineColor, mLineHeight);
        typedArray.recycle();
    }

//    public PercentLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//
//    }


    private void initPaint(int lineColor, int lineHeight) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(lineHeight);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(lineColor);
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
        halfHeight = height / 2;
        temp = width / 10f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (percentage < 0) {
            percentage = 0 - percentage;
            if (percentage > 1) {
                percentage = 1;
            }
            mPaint.setColor(Color.CYAN);
        } else {
            mPaint.setColor(mLineColor);
        }
        canvas.drawLine(temp / 2, halfHeight, temp / 2 + (width - temp) * percentage, halfHeight, mPaint);


    }
}
