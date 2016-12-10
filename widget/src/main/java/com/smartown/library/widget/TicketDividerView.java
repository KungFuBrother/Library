package com.smartown.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author:Tiger[https://github.com/KungFuBrother]
 * <p>
 * CreateTime:2016-10-20 13:24:29
 * <p>
 * Description:TicketDividerView
 */
public class TicketDividerView extends View {

    private Paint paint;
    private int endColor;
    private int lineColor;
    private float lineHeight;
    private float strokeWidth;

    private float width;
    private float height;

    public TicketDividerView(Context context) {
        super(context);
        init(null);
    }

    public TicketDividerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);

        endColor = Color.GRAY;
        lineColor = Color.GRAY;
        lineHeight = 2;
        strokeWidth = 8;

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TicketDividerView);
            endColor = typedArray.getColor(R.styleable.TicketDividerView_td_endColor, Color.GRAY);
            lineColor = typedArray.getColor(R.styleable.TicketDividerView_td_lineColor, Color.GRAY);
            lineHeight = typedArray.getDimensionPixelSize(R.styleable.TicketDividerView_td_lineHeight, 2);
            strokeWidth = typedArray.getDimensionPixelSize(R.styleable.TicketDividerView_td_strokeWidth, 8);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            default:
                height = 24;
                break;
        }
        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(-(height / 2), 0, height / 2, height, -90, canvas);
        drawArc(width - (height / 2), 0, width + height / 2, height, 90, canvas);
        drawStrokeLine(canvas);
    }

    private void drawArc(float left, float top, float right, float bottom, float startAngle, Canvas canvas) {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(endColor);
        RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawArc(rectF, startAngle, 180, true, paint);

        Path path = new Path();
        path.addArc(rectF, startAngle, 180);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        canvas.drawPath(path, paint);
    }

    private void drawStrokeLine(Canvas canvas) {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineHeight);

        Path path = new Path();
        path.moveTo(height / 2, height / 2);
        path.lineTo(width - height / 2, height / 2);
        PathEffect effects = new DashPathEffect(new float[]{strokeWidth, strokeWidth}, strokeWidth);
        paint.setPathEffect(effects);

        canvas.drawPath(path, paint);
    }

}
