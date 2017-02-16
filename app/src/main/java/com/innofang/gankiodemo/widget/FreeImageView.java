package com.innofang.gankiodemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Author: Inno Fang
 * Time: 2017/2/15 15:52
 * Description:
 */

public class FreeImageView extends ImageView {
    private static final String TAG = "FreeImageView";

    private Matrix mMatrix;
    private Matrix mSaveMatrix;

    private static final float MAX_SCALE = 15.0F;
    private static final float MIN_SCALE = 1.0F;
    private float mDist;

    private Bitmap mBitmap;
    private DisplayMetrics mDisplayMetrics;

    private PointF mPointF;
    private PointF mMidPointF;

    MODE mode = MODE.NONE;

    public FreeImageView(Context context) {
        super(context);
        init();
    }

    public FreeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FreeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMatrix = new Matrix();
        mSaveMatrix = new Matrix();
        mPointF = new PointF();
        mMidPointF = new PointF();

        mDisplayMetrics = getContext().getResources().getDisplayMetrics();
        BitmapDrawable bd = (BitmapDrawable) getDrawable();
        if (bd != null) {
            mBitmap = bd.getBitmap();
        }
        setScaleType(ScaleType.MATRIX);
        setImageBitmap(mBitmap);
        setImageMatrix(mMatrix);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: action down");
                mSaveMatrix.set(mMatrix);
                mPointF.set(event.getX(), event.getY());
                mode = MODE.DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "onTouchEvent: action pointer down");
                mDist = getDistance(event);
                if (getDistance(event) > 10F) {
                    mSaveMatrix.set(mMatrix);
                    setMidPoint(mMidPointF, event);
                    mode = MODE.ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: action up");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "onTouchEvent: aciton pointer up");
                mode = MODE.NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent: action move");
                if (mode == MODE.DRAG) {
                    mMatrix.set(mSaveMatrix);
                    mMatrix.postTranslate(event.getX() - mPointF.x, event.getY() - mPointF.y);
                } else if (mode == MODE.ZOOM) {
                    float dist = getDistance(event);
                    if (dist > 10.0F) {
                        mMatrix.set(mSaveMatrix);
                        mMatrix.postScale(dist / mDist, dist / mDist, mMidPointF.x, mMidPointF.y);
                    }
                }
                break;
        }
        setImageMatrix(mMatrix);
        zoomImage();
        //resetCenter(true, true);
        return true;
    }

    private void resetCenter(boolean horizontal, boolean vertical) {

        Matrix m = new Matrix();
        m.set(mMatrix);
        RectF rect = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            // 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
            int screenHeight = mDisplayMetrics.heightPixels;
            if (height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = this.getHeight() - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = mDisplayMetrics.widthPixels;
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }
        mMatrix.postTranslate(deltaX, deltaY);
    }

    private void zoomImage() {
        float[] value = new float[9];
        mMatrix.getValues(value);
        if (mode == MODE.ZOOM) {

            if (value[0] < MIN_SCALE) {
                mMatrix.setScale(MIN_SCALE, MIN_SCALE);
            }
            if (value[0] > MAX_SCALE) {
                mMatrix.set(mSaveMatrix);
            }
        }
    }

    private void setMidPoint(PointF midPointF, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        midPointF.set(x / 2, y / 2);
    }

    private float getDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    enum MODE {
        NONE, DRAG, ZOOM
    }
}
