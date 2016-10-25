package com.dc.appdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by niedaocai on 25/10/2016.
 */

public class CustomView extends View {
    private static final String TAG = "CustomView";
    private Paint mPaintCircle;
    private Paint mPaintArc;
    private Paint mPaintText;
    private Paint mPaintRect;
    private int mProgressVal;

    private final int mStartX = 500;
    private final int mBaseY = 500;
    //每个小矩形的宽度
    private final int mRectWidth = 20;
    //相邻两个小矩形间的空白区域宽度
    private final int mRectGap = 40;
    //相邻两个小矩形的距离
    private final int mRectXPlus = mRectWidth + mRectGap;
    // 画矩形的个数
    private final int mRectCount = 8;
    // 矩形的最大高度
    private final int mRectHeightMax = 400;
    private Random mRandom;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        mRandom = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgressBar(canvas);
        drawAudioBar(canvas);
    }

    /**
     * 在 (500,500), (500*8*mRectXPlus,500)的范围内画矩形
     *
     * @param canvas
     */
    private void drawAudioBar(Canvas canvas) {
        int startX = mStartX;
        for (int i = 0; i < mRectCount; i++, startX += mRectXPlus) {
            canvas.drawRect(startX, mBaseY - mRandom.nextInt(mRectHeightMax),
                    startX + mRectWidth, mBaseY, mPaintRect);
        }
        canvas.drawLine(mStartX - 50, mBaseY + 10, startX, mBaseY + 10, mPaintText);
    }

    private void drawProgressBar(Canvas canvas) {
        canvas.drawArc(50, 50, 400, 400, 0, mProgressVal, false, mPaintArc);
        canvas.drawCircle(230, 230, 120, mPaintCircle);
        canvas.drawText(100 * mProgressVal / 360 + " %", 180, 230, mPaintText);
    }

    private void initPaint() {
        mPaintArc = new Paint();
        mPaintArc.setAntiAlias(true);
        mPaintArc.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaintArc.setStyle(Paint.Style.STROKE);
        mPaintArc.setStrokeWidth(20);

        mPaintCircle = new Paint(mPaintArc);
        mPaintCircle.setStyle(Paint.Style.FILL);

        mPaintText = new Paint(mPaintArc);
        mPaintText.setStrokeWidth(4);
        mPaintText.setTextSize(40);
        mPaintText.setColor(getResources().getColor(android.R.color.holo_orange_light));

        mPaintRect = new Paint();
        mPaintRect.setStyle(Paint.Style.FILL);
        mPaintRect.setStrokeWidth(20);
        mPaintRect.setShader(new LinearGradient(mStartX, mBaseY, mStartX, mBaseY - mRectHeightMax,
                Color.BLUE, Color.RED, Shader.TileMode.CLAMP));
    }

    public void setProgress() {
        mProgressVal += 10;
        if (mProgressVal > 360) {
            mProgressVal -= 360;
        }
    }
}
