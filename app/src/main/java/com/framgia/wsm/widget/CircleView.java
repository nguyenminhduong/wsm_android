package com.framgia.wsm.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tri on 27/06/2017.
 */

public class CircleView extends View {
    private static final int DEFAULT_CIRCLE_COLOR = Color.RED;

    private int mCircleColor = DEFAULT_CIRCLE_COLOR;
    private Paint mPaint;

    public CircleView(Context context) {
        super(context);
        initView(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setCircleColor(int circleColor) {
        mCircleColor = circleColor;
        invalidate();
    }

    public int getCircleColor() {
        return mCircleColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int usableWidth = width - (paddingLeft + paddingRight);
        int usableHeight = height - (paddingTop + paddingBottom);

        int radius = Math.min(usableWidth, usableHeight) / 2 - 2;
        int cx = paddingLeft + (usableWidth / 2);
        int cy = paddingTop + (usableHeight / 2);

        mPaint.setColor(mCircleColor);
        canvas.drawCircle(cx, cy, radius, mPaint);
    }
}
