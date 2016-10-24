package com.dc.appdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Demo 通过组合现有的控件来实现新的复合控件
 * Created by niedaocai on 22/10/2016.
 */

public class TopBar extends RelativeLayout {
    private static final String TAG = "TopBar";
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mTitle;
    private Button mLeftButton;
    private Button mRightButton;
    private TextView mTitleView;
    private LayoutParams mLeftParams;
    private LayoutParams mRightParams;
    private LayoutParams mTitleParams;
    private TopBarClickListener mClickListener;

    /**
     * 参考ViewGroup 或者RelativeLayout的构造函数，实现从代码创建TopBar
     *
     * @param context
     */
    public TopBar(Context context) {
        this(context, null);
    }

    /**
     * 从XML创建TopBar
     *
     * @param context
     * @param attrs
     */
    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromAttributes(context, attrs, defStyleAttr);
    }

    /**
     * 从XML中提取TopBar自定义的属性，Android定义的属性为父类去提取
     * 提取出属性来做对各个控件进行初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initFromAttributes(
            Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.TopBar_leftTextColor:
                    mLeftTextColor = a.getColor(attr, 0);
                    break;
                case R.styleable.TopBar_leftBackground:
                    mLeftBackground = a.getDrawable(attr);
                    break;
                case R.styleable.TopBar_leftText:
                    mLeftText = a.getString(attr);
                    break;
                case R.styleable.TopBar_rightTextColor:
                    mRightTextColor = a.getColor(attr, 0);
                    break;
                case R.styleable.TopBar_rightBackground:
                    mRightBackground = a.getDrawable(attr);
                    break;
                case R.styleable.TopBar_rightText:
                    mRightText = a.getString(attr);
                    break;
                case R.styleable.TopBar_topbarTitleTextSize:
                    mTitleTextSize = a.getDimension(attr, 10);
                    break;
                case R.styleable.TopBar_topbarTitleTextColor:
                    mTitleTextColor = a.getColor(attr, 0);
                    break;
                case R.styleable.TopBar_topbarTitle:
                    mTitle = a.getString(attr);
                    break;
            }
        }
        a.recycle();

        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new TextView(context);

        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setText(mLeftText);
        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.leftClick();
            }
        });

        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);
        mRightButton.setText(mRightText);
        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.rightClick();
            }
        });

        mTitleView.setText(mTitle);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setGravity(Gravity.CENTER);

        mLeftParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mLeftButton, mLeftParams);

        mRightParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mRightButton, mRightParams);

        mTitleParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTitleView, mTitleParams);
    }

    /*public TopBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    public void setTopBarClickListener(TopBarClickListener l) {
        mClickListener = l;
    }

    /**
     * 设置对应的Button是否显示
     *
     * @param id   Button id, 0 mLeftButton, 1 mRightButton
     * @param flag true for View.VISIBLE, false for View.GONE
     */
    public void setButtonVisable(int id, boolean flag) {
        if (flag) {
            if (id == 0) {
                mLeftButton.setVisibility(View.VISIBLE);
            } else {
                mRightButton.setVisibility(View.VISIBLE);
            }
        } else {
            if (id == 0) {
                mLeftButton.setVisibility(View.GONE);
            } else {
                mRightButton.setVisibility(View.GONE);
            }
        }
    }

    public interface TopBarClickListener {
        void leftClick();

        void rightClick();
    }
}