package com.dc.appdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by niedaocai on 22/10/2016.
 */

public class CustomTextView extends TextView {
    private static final String TAG = "CustomTextView";
    private Paint mPaint1;
    private Paint mPaint2;


    public CustomTextView(Context context) {
        super(context);
        initPaint();
        Log.d(TAG, "CustomTextView: 1");
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        Log.d(TAG, "CustomTextView: attrs=" + attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "CustomTextView: 3");
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0 ,getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        canvas.drawRect(10, 10, getMeasuredWidth()-10, getMeasuredHeight()-10, mPaint2);
        canvas.save();
        canvas.translate(10, 0);
        super.onDraw(canvas);
        canvas.restore();
    }

    private void initPaint() {
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2 = new Paint();
        mPaint2.setColor(getResources().getColor(android.R.color.holo_orange_light));
        mPaint1.setStyle(Paint.Style.FILL);
    }
}
