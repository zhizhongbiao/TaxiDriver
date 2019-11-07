package com.taxidriver.santos.widget.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Author : WaterFlower.
 * Created on 2017/9/14.
 * Desc :
 */

public class LoadingImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = "LoadingImageView";
    private float progress;
    private int mWidth;
    private int mHeight;
    private int xCenter;
    private int yCenter;
    private int mRadius;
    private Paint mPaint;
    private Paint mTextPaint;

    private RectF mBorder;
    private Rect mTextRect;

    private int mColor = Color.parseColor("#3d232323");
    private int mTextColor = Color.RED;
    private float mStrokeWidth = 15f;
    private float mTextSize = 40f;

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public void setmStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        Log.e(TAG, "setProgress: progress=" + progress);
        invalidate();
    }

    public LoadingImageView(Context context) {
        this(context, null);
//        initPaint();
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
//        initPaint();
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

//    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initPaint();
//    }

    private void initPaint() {
        mPaint = new Paint();
        mTextPaint = new Paint();

        mPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);

        mPaint.setColor(mColor);
        mTextPaint.setColor(mTextColor);

        mPaint.setStrokeWidth(mStrokeWidth);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);

    }

    /**
     * 第一顺序执行,能获取child实例,但无法获取宽高,
     * getHeight()和getWidth()和getMeasuredHeight()和getMeasuredWidth()都不能获取到;
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    /**
     * 初始化时候,第二顺序执行,能获取child实例和getMeasuredHeight()和getMeasuredWidth(),
     * 不能获取getHeight()和getWidth();
     * 该方法有可能被多次执行,在非初始化时候,也能获取getHeight()和getWidth();
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 初始化时候,第三顺序执行,能获取child实例和getMeasuredHeight()和getMeasuredWidth(),
     * 不能获取getHeight()和getWidth();
     * 该方法有可能被多次执行,在非初始化时候,也能获取getHeight()和getWidth();
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 初始化时候,第三顺序执行,能获取child实例和getMeasuredHeight()和getMeasuredWidth(),
     * 也能获取getHeight()和getWidth(),该方法有可能被多次执行;
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 初始化时候,第四顺序执行,能获取child实例和getMeasuredHeight()和getMeasuredWidth(),
     * 也能获取getHeight()和getWidth(),该方法有可能跟随onLayout(boolean changed, int left, int top, int right, int bottom)方法被多次执行;
     */
    @Override
    public void onGlobalLayout() {
        initParams();
    }


    /**
     * 初始化时候,第五顺序执行,能获取child实例和getMeasuredHeight()和getMeasuredWidth(),
     * 也能获取getHeight()和getWidth();
     *
     * @param hasWindowFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }


    private void initParams() {
        mTextRect = new Rect();
        mWidth = getWidth();
        mHeight = getHeight();
        if (mHeight == 0 || mWidth == 0) return;
        xCenter = mWidth / 2;
        yCenter = mHeight / 2;
        mRadius = (mHeight > mWidth ? mWidth / 2 : mHeight / 2) - 5;
        mBorder = new RectF(xCenter - mRadius, yCenter - mRadius, xCenter + mRadius, yCenter + mRadius);
//        根据半径mRadius制定字体大小
        mTextPaint.setTextSize(mRadius/4);
//        Log.e(TAG, "onGlobalLayout: mRadius=" + mRadius);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRadius == 0 || progress >= 1 || progress <= 0) {
            return;
        }

        canvas.drawArc(mBorder, 0, 360 * progress, true, mPaint);
        String text = "正在上传" + Math.round(progress * 100) + "%";

//        mTextPaint.getTextBounds(text, 0, text == null ? 0 : text.length(), mTextRect);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textHeight = (fontMetrics.bottom - fontMetrics.top) / 2;
        float textWidth = mTextPaint.measureText(text);
        canvas.drawText(text, xCenter - (textWidth / 2), yCenter + (textHeight / 2), mTextPaint);

    }


}
