package com.smartown.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：Tiger
 * <p>
 * 时间：2016-08-23 15:43
 * <p>
 * 描述：饼状图控件
 */
public class BingView extends View {

    private Paint paint;
    private ValueGetter valueGetter;

    private int strokeWidth;
    private int strokeColor;

    private float innerCircleRadius;

    public BingView(Context context) {
        super(context);
        init(null);
    }

    public BingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BingView);
            strokeWidth = typedArray.getDimensionPixelSize(R.styleable.BingView_strokeWidth, 0);
            strokeColor = typedArray.getColor(R.styleable.BingView_strokeColor, Color.GRAY);
            innerCircleRadius = typedArray.getFloat(R.styleable.BingView_innerCircleRadius, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (valueGetter == null) {
            return;
        }
        if (valueGetter.getSize() <= 0) {
            return;
        }
        if (getWidth() > getHeight()) {
            drawArc((getWidth() - getHeight()) / 2, 0, (getWidth() + getHeight()) / 2, getHeight(), canvas);
            drawStroke(canvas, getHeight() / 2);
            drawInnerCircle(canvas, getHeight() / 2);
        } else {
            drawArc(0, (getHeight() - getWidth()) / 2, getWidth(), (getHeight() + getWidth()) / 2, canvas);
            drawStroke(canvas, getWidth() / 2);
            drawInnerCircle(canvas, getWidth() / 2);
        }
    }

    /**
     * 画各部分扇形
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param canvas
     */
    private void drawArc(float left, float top, float right, float bottom, Canvas canvas) {
        RectF rectF = new RectF(left, top, right, bottom);
        paint.reset();
        paint.setStyle(Paint.Style.FILL);
        int size = valueGetter.getSize();
        float totalCount = getTotalCount();
        float totalAngle = 0;
        for (int i = 0; i < size; i++) {
            paint.setColor(valueGetter.getColor(i));
            float angle = valueGetter.getCount(i) / totalCount * 360;
            canvas.drawArc(rectF, totalAngle, (i == size - 1) ? (360 - totalAngle) : angle, true, paint);
            totalAngle += angle;
        }
    }

    /**
     * 描边
     *
     * @param canvas
     * @param radius
     */
    private void drawStroke(Canvas canvas, int radius) {
        if (strokeWidth <= 0) {
            return;
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(strokeColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius - (strokeWidth / 2), paint);
    }

    private void drawInnerCircle(Canvas canvas, int radius) {
        if (innerCircleRadius <= 0) {
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, innerCircleRadius * radius, paint);
        drawStroke(canvas, radius / 2 + strokeWidth);
    }

    private float getTotalCount() {
        int size = valueGetter.getSize();
        int totalCount = 0;
        for (int i = 0; i < size; i++) {
            totalCount += valueGetter.getCount(i);
        }
        return totalCount;
    }

    public void setValueGetter(ValueGetter valueGetter) {
        this.valueGetter = valueGetter;
    }

    public interface ValueGetter {

        int getSize();

        int getColor(int position);

        float getCount(int position);

    }

}
