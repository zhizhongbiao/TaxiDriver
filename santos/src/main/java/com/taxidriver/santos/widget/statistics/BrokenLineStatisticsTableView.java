package com.taxidriver.santos.widget.statistics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;


import com.taxidriver.santos.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Author : WaterFlower.
 * Created on 2017/11/17.
 * Desc : 折线图
 */

public class BrokenLineStatisticsTableView extends View {

    private static final String TAG = "BrokenLineStatisticsTab";
    private int textColor;
    private int lineColor;
    private int circleColor;

    private int width;
    private int height;
    private int halfWidth;
    private int halfHeight;
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private float singleLength;

    private List<Float> xCenterPointsForCircle = new ArrayList<>();
    private List<Float> yPointsForLine = new ArrayList<>();
    private List<Float> yPointsForText = new ArrayList<>();

    private float coordinateX;
    private float coordinateY;
    private List<String> xSubject;
    private int xSubjectSize;
    private float coordinateXStart;
    private float coordinateYStart;
    private List<Integer> ySubject;
    private float biggestY;
    private int radius;
    private float mTextSize;
    private Path mPath;
    private Paint mLinePaint;

    public BrokenLineStatisticsTableView(Context context) {
        super(context);
    }

    public BrokenLineStatisticsTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrokenLineStatisticsTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BrokenLineStatisticsTableView, defStyleAttr, 0);
        circleColor = typedArray.getColor(R.styleable.BrokenLineStatisticsTableView_circle_point_color, Color.RED);
        lineColor = typedArray.getColor(R.styleable.BrokenLineStatisticsTableView_broken_line_color, Color.CYAN);
        textColor = typedArray.getColor(R.styleable.BrokenLineStatisticsTableView_text_color, Color.BLACK);
        mTextSize = typedArray.getDimension(R.styleable.BrokenLineStatisticsTableView_text_size, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, context.getResources().getDisplayMetrics()));


        typedArray.recycle();
        initCirclePaint();
        initLinePaint();
        initTextPaint();
    }

    private void initCirclePaint() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCirclePaint.setColor(circleColor);
    }

    private void initLinePaint() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(lineColor);
    }


    private void initTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(textColor);
    }


    public void setData(@NonNull List<String> xSubject, @NonNull List<Integer> ySubject) {
        if (xSubject == null || xSubject.isEmpty()) {
            Log.e(TAG, "setData: xSubject == null || xSubject.isEmpty()");
            return;
        }
        if (ySubject == null || ySubject.isEmpty()) {
            Log.e(TAG, "setData: ySubject == null || ySubject.isEmpty()");
            return;
        }

        if (ySubject.size() != xSubject.size()) {
            throw new RuntimeException("ySubject.size() must equal to xSubject.size() !!!");
        }

        this.xSubject = xSubject;
        this.ySubject = ySubject;
        xSubjectSize = xSubject.size();
        biggestY = getBiggest(ySubject);

        initParams(xSubjectSize);
        invalidate();
    }

    /**
     * 获取Y值的最大值,其他按照比例画出高度
     *
     * @param ySubject
     * @return
     */
    private float getBiggest(List<Integer> ySubject) {
        float biggest = 0;
        for (Integer item : ySubject) {
            biggest = biggest > item ? biggest : item;
        }
        return biggest;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initParams(xSubjectSize);
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        initParams(xSubjectSize);
    }

    private void initParams(float xSubjectSize) {
        width = getWidth();
        height = getHeight();
        //X轴占据的宽度
        coordinateX = width * 0.9f;
        //X轴开始边距
        coordinateXStart = width * 0.1f;
        //Y轴还在占据的高度
        coordinateY = height * 0.65f;
        //Y轴开始的边距
        coordinateYStart = height * 0.1f;
        halfWidth = width / 2;
        halfHeight = height / 2;
        //小圆点半径
        radius = width / 90;
        mPath = new Path();
        if (xSubjectSize <= 0) return;
        //X轴单位长度
//        singleLength = coordinateX / (xSubjectSize+1);
        singleLength = width / (xSubjectSize+1);

    }

    /**
     * 测量字体宽度
     *
     * @param text
     * @param paint
     * @return
     */
    private float getTextHalfLength(String text, Paint paint) {
        if (!TextUtils.isEmpty(text)) {
            return paint.measureText(text) / 2;
        }
        return 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (xSubject == null || xSubject.isEmpty()) {
            Log.e(TAG, "onDraw: xSubject == null || xSubject.isEmpty()");
            return;
        }

        if (ySubject == null || ySubject.isEmpty()) {
            Log.e(TAG, "onDraw: ySubject == null || ySubject.isEmpty()");
            return;
        }

        if (ySubject.size() != xSubject.size()) {
            throw new RuntimeException("ySubject.size() must equal to xSubject.size() !!!");
        }

        drawXSubjectText(canvas);
        drawCircle(canvas);
        drawBrokenLine(canvas);
        drawYSubjectText(canvas);


    }

    /**
     * 画折线
     *
     * @param canvas
     */
    private void drawBrokenLine(Canvas canvas) {
        mPath.reset();
        mLinePaint.setStrokeWidth(radius / 2f);
        mPath.moveTo(xCenterPointsForCircle.get(0), yPointsForLine.get(0));
        for (int i = 0; i < yPointsForText.size(); i++) {
            mPath.lineTo(xCenterPointsForCircle.get(i), yPointsForLine.get(i));

        }
        canvas.drawPath(mPath, mLinePaint);
    }

    /**
     * 画Y值
     *
     * @param canvas
     */
    private void drawYSubjectText(Canvas canvas) {
        mTextPaint.setTextSize(mTextSize / 1.2f);
        for (int i = 0; i < ySubject.size(); i++) {
            float xPoint = xCenterPointsForCircle.get(i) - getTextHalfLength(ySubject.get(i) + "", mTextPaint);
            canvas.drawText(ySubject.get(i) + "", xPoint, yPointsForText.get(i), mTextPaint);
        }
    }

    /**
     * 画圆点
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        yPointsForText.clear();
        yPointsForLine.clear();
        for (int i = 0; i < ySubject.size(); i++) {
            float yPoint = height - coordinateYStart * 1.8f - (coordinateY * (ySubject.get(i) / biggestY));
            canvas.drawCircle(xCenterPointsForCircle.get(i), yPoint, radius, mCirclePaint);
            yPointsForText.add(yPoint - radius / 2f * 3f);
            yPointsForLine.add(yPoint);
        }
    }

    /**
     * 画X轴的数据
     *
     * @param canvas
     */
    private void drawXSubjectText(Canvas canvas) {
        mTextPaint.setTextSize(mTextSize);
        xCenterPointsForCircle.clear();

        for (int i = 0; i < xSubject.size(); i++) {
//            float xCenterPointForCircle = coordinateXStart + singleLength * i;
            float xCenterPointForCircle = singleLength + singleLength * i;
            float xCenterPointForText = xCenterPointForCircle - getTextHalfLength(xSubject.get(i), mTextPaint);
            canvas.drawText(xSubject.get(i), xCenterPointForText, height - coordinateYStart / 2f, mTextPaint);
            xCenterPointsForCircle.add(xCenterPointForCircle);

        }
    }

}
