package com.smartown.library.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author:Tiger[https://github.com/KungFuBrother]
 * <p>
 * CreateTime:2016-10-20 13:24:29
 * <p>
 * Description:IndicatorView
 */
public class IndicatorView extends View {

    /**
     * count of points
     */
    private int count = 3;
    /**
     * size of normal point
     */
    private float pointWidth = 32;
    private int pointColor = Color.GRAY;
    /**
     * size of selected point
     */
    private float selectedPointWidth = 48;
    private int selectedPointColor = Color.RED;
    /**
     * linked line height
     */
    private float lineHeight = 4;
    private float textSize = 24;

    private int animationMode = 0;
    private int animationDuration = 400;

    private boolean showLine = false;
    private boolean showNumber = false;

    private float nodeWidth;
    private float pointArea;
    private float intervalLineWidth;

    private Paint paint;
    private Paint textPaint;
    private float textHeight;

    private int lastSelection = -1;
    private int selection = -1;
    private OnSelectPartListener onSelectPartListener;

    private ValueAnimator animator;
    private float animationValue = 0;

    public IndicatorView(Context context) {
        super(context);
        init(null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IndicatorView);
            count = typedArray.getInt(R.styleable.IndicatorView_IndicatorView_count, 3);
            pointWidth = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_IndicatorView_pointWidth, 32);
            pointColor = typedArray.getColor(R.styleable.IndicatorView_IndicatorView_pointColor, Color.GRAY);
            selectedPointWidth = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_IndicatorView_selectedPointWidth, 48);
            selectedPointColor = typedArray.getColor(R.styleable.IndicatorView_IndicatorView_selectedPointColor, Color.RED);
            lineHeight = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_IndicatorView_lineHeight, 4);
            textSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_IndicatorView_textSize, 24);
            animationMode = typedArray.getInt(R.styleable.IndicatorView_IndicatorView_animationMode, 0);
            animationDuration = typedArray.getInt(R.styleable.IndicatorView_IndicatorView_animationDuration, 400);
            showLine = typedArray.getBoolean(R.styleable.IndicatorView_IndicatorView_showLine, false);
            showNumber = typedArray.getBoolean(R.styleable.IndicatorView_IndicatorView_showNumber, false);
            typedArray.recycle();
        }
        nodeWidth = Math.max(pointWidth, selectedPointWidth);
        pointArea = (float) (Math.PI * Math.pow(selectedPointWidth / 2, 2));
        initPaint();
        initTextPaint();
        initAnimator();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    private void initTextPaint() {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.bottom - fontMetrics.top;
    }

    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0, animationDuration);
        animator.setDuration(animationDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animationValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width = MeasureSpec.getSize(widthMeasureSpec);
        float height;
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            default:
                height = nodeWidth;
                break;
        }
        intervalLineWidth = (width - nodeWidth) / (count - 1);
        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            selectPoint(event.getX());
        }
        return true;
    }

    private void selectPoint(float x) {
        int position = (int) (x / intervalLineWidth);
        if (selection == position) {
            return;
        }
        float pointEndX = nodeWidth + intervalLineWidth * position;
        if (x < pointEndX) {
            setSelection(position);
        }
    }

    public void setSelection(int selection) {
        lastSelection = this.selection;
        this.selection = selection;
//        refresh();
        animator.start();
        if (onSelectPartListener != null) {
            onSelectPartListener.onSelectPart(this, selection);
        }
    }

    private void refresh() {
        switch (animationMode) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(pointColor);
        if (showLine) {
            drawLine(canvas);
        }
        for (int i = 0; i < count; i++) {
            drawPoint(i, canvas);
        }
        paint.setColor(selectedPointColor);
        drawAnimation(canvas);
        if (showNumber) {
            for (int i = 0; i < count; i++) {
                drawNumber(i, canvas);
            }
        }
    }

    private void drawLine(Canvas canvas) {
        canvas.drawRect(nodeWidth / 2,
                (getHeight() - lineHeight) / 2,
                getWidth() - nodeWidth / 2,
                (getHeight() + lineHeight) / 2, paint);
    }

    private void drawPoint(int position, Canvas canvas) {
        float x = nodeWidth / 2 + intervalLineWidth * position;
        float y = getHeight() / 2;
        RectF rectF = new RectF(x - pointWidth / 2,
                y - pointWidth / 2,
                x + pointWidth / 2,
                y + pointWidth / 2);
        canvas.drawOval(rectF, paint);
    }

    private void drawAnimation(Canvas canvas) {
        if (lastSelection == -1) {
            drawSelectedPoint(selection, (float) Math.sqrt(pointArea / animationDuration * animationValue / Math.PI) * 2, canvas);
            return;
        }
        final float linkLineArea = Math.abs(lastSelection - selection) * intervalLineWidth * lineHeight;
        final float animationSpeed = (pointArea + linkLineArea) / animationDuration;
        final float animatedArea = animationSpeed * animationValue;
        float lastPointArea = 0;
        float lineArea = 0;
        float nextPointArea = 0;
        if (linkLineArea >= pointArea) {
            if (animatedArea >= pointArea) {
                if (animatedArea >= linkLineArea) {
                    nextPointArea = animatedArea - linkLineArea;
                    lineArea = pointArea - nextPointArea;
                } else {
                    //selected color all on line
                    lineArea = pointArea;
                }
            } else {
                lastPointArea = pointArea - animatedArea;
                lineArea = animatedArea;
            }
        } else {
//            linkLineArea < pointArea
            if (animatedArea >= linkLineArea) {
                if (animatedArea >= pointArea) {
                    nextPointArea = animatedArea - linkLineArea;
                    lineArea = pointArea - nextPointArea;
                } else {
                    lastPointArea = pointArea - animatedArea;
                    lineArea = linkLineArea;
                    nextPointArea = animatedArea - linkLineArea;
                }
            } else {
                lastPointArea = pointArea - animatedArea;
                lineArea = animatedArea;
            }
        }
        System.out.println("animate/" + pointArea + "/" + linkLineArea + "/" + animationValue + "/" + lastPointArea + "/" + lineArea + "/" + nextPointArea);
        if (lastPointArea > 0) {
            final float lastPointWidth = (float) Math.sqrt(lastPointArea / Math.PI) * 2;
            drawSelectedPoint(lastSelection, lastPointWidth, canvas);
        }
        if (nextPointArea > 0) {
            final float nextPointWidth = (float) Math.sqrt(nextPointArea / Math.PI) * 2;
            drawSelectedPoint(selection, nextPointWidth, canvas);
        }
        if (lineArea > 0) {
            if (lineArea >= linkLineArea) {
                final float left = nodeWidth / 2 + intervalLineWidth * Math.min(lastSelection, selection);
                final float right = nodeWidth / 2 + intervalLineWidth * Math.max(lastSelection, selection);
                drawSelectedLine(left, right, canvas);
            } else {
                if (animatedArea < linkLineArea) {
                    if (animatedArea > lineArea) {
                        if (selection > lastSelection) {
                            final float right = nodeWidth / 2 + intervalLineWidth * lastSelection + (animatedArea / lineHeight);
                            final float left = right - (lineArea / lineHeight);
                            drawSelectedLine(left, right, canvas);
                        } else {
                            final float left = nodeWidth / 2 + intervalLineWidth * lastSelection - (animatedArea / lineHeight);
                            final float right = left + (lineArea / lineHeight);
                            drawSelectedLine(left, right, canvas);
                        }
                    } else {
                        if (selection > lastSelection) {
                            final float left = nodeWidth / 2 + intervalLineWidth * lastSelection;
                            final float right = left + (lineArea / lineHeight);
                            drawSelectedLine(left, right, canvas);
                        } else {
                            final float right = nodeWidth / 2 + intervalLineWidth * lastSelection;
                            final float left = right - (lineArea / lineHeight);
                            drawSelectedLine(left, right, canvas);
                        }
                    }
                } else {
                    if (selection > lastSelection) {
                        final float right = nodeWidth / 2 + intervalLineWidth * selection;
                        final float left = right - (lineArea / lineHeight);
                        drawSelectedLine(left, right, canvas);
                    } else {
                        final float left = nodeWidth / 2 + intervalLineWidth * selection;
                        final float right = left + (lineArea / lineHeight);
                        drawSelectedLine(left, right, canvas);
                    }
                }
            }
        }
    }

    private void drawSelectedPoint(int position, float width, Canvas canvas) {
        float x = nodeWidth / 2 + intervalLineWidth * position;
        float y = getHeight() / 2;
        RectF rectF = new RectF(x - width / 2,
                y - width / 2,
                x + width / 2,
                y + width / 2);
        canvas.drawOval(rectF, paint);
    }

    private void drawSelectedLine(float left, float right, Canvas canvas) {
        canvas.drawRect(left,
                (getHeight() - lineHeight) / 2,
                right,
                (getHeight() + lineHeight) / 2, paint);
    }

    private void drawNumber(int position, Canvas canvas) {
        float x = nodeWidth / 2 + intervalLineWidth * position;
        canvas.drawText((position + 1) + "", x, (getHeight() + textHeight / 2) / 2, textPaint);
    }

    public void setOnSelectPartListener(OnSelectPartListener onSelectPartListener) {
        this.onSelectPartListener = onSelectPartListener;
    }

    public interface OnSelectPartListener {

        void onSelectPart(IndicatorView view, int position);

    }

    public static class AnimateHelper {

    }

    public static class AnimateResult {

        public float lastPointWidth;
        public float lineLeft;
        public float lineRight;
        public float nextPointWidth;

        public AnimateResult(float lastPointWidth, float lineLeft, float lineRight, float nextPointWidth) {
            this.lastPointWidth = lastPointWidth;
            this.lineLeft = lineLeft;
            this.lineRight = lineRight;
            this.nextPointWidth = nextPointWidth;
        }

    }

//    public enum AnimationMode {NONE, SCALE, FLOW}
}
