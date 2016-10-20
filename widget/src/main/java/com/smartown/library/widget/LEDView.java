package com.smartown.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：Tiger
 * <p>
 * 时间：2016-09-14 10:50
 * <p>
 * 描述：
 */
public abstract class LEDView extends View {

    private int color;
    private String number;
    private Paint paint;
    //最小单位点的大小
    private int pointWidth;
    //间隔大小
    private int intervalWidth;
    //每行最多现实多少个字符
    private int maxColumnSize;
    //每行的高度
    private int rowHeight;
    private boolean autoScroll;
    private boolean singleLine;

    private int screenWidth;

    public LEDView(Context context) {
        this(context, null);
    }

    public LEDView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        color = Color.BLACK;
        number = "";
        pointWidth = 4;
        intervalWidth = 1;
        autoScroll = false;
        singleLine = false;
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LEDView);
            color = typedArray.getColor(R.styleable.LEDView_ledColor, Color.BLACK);
            number = typedArray.getString(R.styleable.LEDView_ledNumber);
            pointWidth = typedArray.getDimensionPixelSize(R.styleable.LEDView_ledPointWidth, 4);
            intervalWidth = typedArray.getDimensionPixelSize(R.styleable.LEDView_ledIntervalWidth, 1);
            autoScroll = typedArray.getBoolean(R.styleable.LEDView_ledAutoScroll, false);
            singleLine = typedArray.getBoolean(R.styleable.LEDView_ledSingleLine, false);
            typedArray.recycle();
        }
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        rowHeight = (getRowNum() + 2) * (pointWidth + intervalWidth);
        int rowWidth;
        int rowSize = 1;
        maxColumnSize = (screenWidth / (pointWidth + intervalWidth) - 1) / (getColumnNum() + 1);
        int length = number.length();
        if (length > maxColumnSize) {
            rowWidth = screenWidth;
            if (length % maxColumnSize > 0) {
                rowSize = length / maxColumnSize + 1;
            } else {
                rowSize = length / maxColumnSize;
            }
        } else {
            rowWidth = ((length * (getColumnNum() + 1)) + 1) * (pointWidth + intervalWidth);
        }
        setMeasuredDimension(rowWidth, rowSize * rowHeight);
    }

    /**
     * @return 行数
     */
    protected abstract int getRowNum();

    /**
     * @return 列数
     */
    protected abstract int getColumnNum();

    /**
     * @param s
     * @return 绘图点阵数组
     */
    protected abstract int[][] getPointsArray(String s);

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        switch (heightMode) {
//            case MeasureSpec.EXACTLY:
//                final int height = MeasureSpec.getSize(heightMeasureSpec);
//                measure(height);
//                break;
//            default:
//                measure(42);
//                break;
//        }
//    }
//
//    private void measure(int height) {
//        intervalWidth = height / 42;
//        pointWidth = 4 * intervalWidth;
//        int length = number.length();
//        setMeasuredDimension(((length * 5) + 1) * pointWidth + (length * 3) * intervalWidth, height);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = pointWidth + intervalWidth;
        int y = pointWidth + intervalWidth;
        final int length = number.length();
        for (int i = 0; i < length; i++) {
            drawPoints(getPointsArray(number.substring(i, i + 1)), x, y, canvas);
            if ((i + 1) % maxColumnSize == 0) {
                x = pointWidth + intervalWidth;
                y = pointWidth + intervalWidth + ((i + 1) / maxColumnSize * rowHeight);
            } else {
                x += ((getColumnNum() + 1) * (pointWidth + intervalWidth));
            }
        }
    }

    private void drawPoints(int[][] points, int x, int y, Canvas canvas) {
        for (int i = 0; i < getRowNum(); i++) {
            final int[] row = points[i];
            for (int j = 0; j < getColumnNum(); j++) {
                if (row[j] == 1) {
                    drawPoint(i, j, x, y, canvas);
                }
            }
        }
    }

    private void drawPoint(int column, int row, int x, int y, Canvas canvas) {
        final int left = x + (row * (pointWidth + intervalWidth));
        final int top = y + (column * (pointWidth + intervalWidth));
        canvas.drawRect(left, top, left + pointWidth, top + pointWidth, paint);
    }

}