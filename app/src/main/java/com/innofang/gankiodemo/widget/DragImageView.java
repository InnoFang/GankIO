package com.innofang.gankiodemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Author: Inno Fang
 * Time: 2017/2/16 14:59
 * Description:
 */

public class DragImageView extends ImageView {
    private static final String TAG = "DragImageView";

    private float mLastX;
    private float mLastY;

    private float mStartX;
    private float mStartY;

    public DragImageView(Context context) {
        super(context);
        init();
    }

    public DragImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStartX = getTop();
        mStartY = getLeft();
        mLastX = 0;
        mLastY = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                AnimatorSet set = new AnimatorSet();
                set.setInterpolator(new LinearInterpolator());
                set.playTogether(
                        ObjectAnimator.ofFloat(this, "translationX", mStartX),
                        ObjectAnimator.ofFloat(this, "translationY", mStartY)
                );
                set.setDuration(500).start();

                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;
                float translateX = ViewHelper.getTranslationX(this) + deltaX;
                float translateY = ViewHelper.getTranslationY(this) + deltaY;
                ViewHelper.setTranslationX(this, translateX);
                ViewHelper.setTranslationY(this, translateY);
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}
