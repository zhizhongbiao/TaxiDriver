package com.taxidriver.santos.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import com.taxidriver.santos.R;

import java.util.HashMap;


public class SelectedTextView extends TextView {
    private final Bitmap mSelectBitmap;
    Paint paint;
    private String[] codes = {
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"
    };
    private int width = 0;
    private int textHeight = 30;
    private int textSelectSize = 24;
    private int textSize = 20;
    private int selectColor;
    private int normalColor;
    private int disableColor;
    private int enable;
    private int lightIndex = -1;
    private HashMap<String, Integer> posMap = new HashMap();
    private double stepX = 16, stepY = 16;
    private boolean isTouch = false;
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            handler.removeMessages(0);
            if (stepX > 0) {
                stepX -= 5;
                handler.sendEmptyMessageDelayed(0, 13);
            } else {
                stepX = 0;
                isTouch = false;
            }
            invalidate();
        }
    };
    private OnTextChangeListener listener;
    private String lastLetter;

    public SelectedTextView(Context context) {
        this(context, null);
    }

    public SelectedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        selectColor = Color.parseColor("#FFFFFF");
        normalColor = Color.parseColor("#a3acb7");
        disableColor = Color.parseColor("#00FF00");
        enable = Color.parseColor("#a3acb7");
        mSelectBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bluetoothtel_btn_letters_s);
        textHeight = mSelectBitmap.getHeight() - 2;
        textSelectSize = mSelectBitmap.getHeight() - 2;
        textSize = mSelectBitmap.getHeight() - 4;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        Log.v("hh", "onSizeChanged:" + width);
    }

    public void setKey(HashMap<String, Integer> posMap) {
        this.posMap.clear();
        this.posMap = posMap;
    }

    public void setDisable(String ch) {

    }

    public void setLightChar(String ch) {
//    	System.out.println("ch:" + ch);
        lightIndex = 26;
        char c = ch.charAt(0);
        if (c >= 'A' && c <= 'Z') {
            lightIndex = c - 'A' /*+ 1*/;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int len = codes.length;
        int codeX;
        paint.setTextSize(textSize - 4);
        for (int i = 0; i < len; i++) {
            double offset = Math.abs(i - lightIndex);

            int y = (textHeight * (i + 1));

            if (offset == 0) {
                paint.setTextSize(textSelectSize);
                paint.setColor(!isEnabled() ? enable : selectColor);
                canvas.drawBitmap(mSelectBitmap, 0, textHeight * i, paint);
            } else {
                paint.setTextSize(textSize);
                paint.setColor(!isEnabled() ? enable : normalColor);
            }
            codeX = width / 2 - (int) paint.measureText(codes[i]) / 2;
            canvas.drawText(codes[i], codeX, y - 2, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return true;
        }
        float y = event.getY();
        int index = (int) ((y) / textSize);
        if (index >= codes.length)
            index = codes.length - 1;
        else if (index < 0) {
            index = 0;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            handler.removeMessages(0);
            stepX = 16;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            isTouch = true;
            if (lightIndex != index) {
                lightIndex = index;
                if (listener != null) {
                    String temp = codes[lightIndex];
                    boolean isExist = listener.onTextChange(temp);
                    if (isExist) {
                        lastLetter = temp;
                    }
                }
                invalidate();
            }

            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_CANCEL) {

            if (listener != null) {
                listener.hideCenteText();
            }

            if (null != lastLetter) {
                setLightChar(lastLetter);
            }

            invalidate();
            handler.sendEmptyMessage(0);
        }
        return true;
    }

    public void setOnTextChangeListener(OnTextChangeListener listener) {
        this.listener = listener;
    }

    public interface OnTextChangeListener {
        boolean onTextChange(String text);

        void hideCenteText();
    }
}
