package com.smartown.library.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 作者：Tiger
 * <p/>
 * 时间：2016-09-14 15:03
 * <p/>
 * 描述：
 */
public class NumberLEDView extends LEDView {

    public NumberLEDView(Context context) {
        super(context);
    }

    public NumberLEDView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getRowNum() {
        return 7;
    }

    @Override
    protected int getColumnNum() {
        return 4;
    }

    @Override
    protected int[][] getPointsArray(String number) {
        switch (number) {
            case ".":
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 1, 0}};
            case ":":
                return new int[][]{
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}};
            case "0":
                return new int[][]{
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}};
            case "1":
                return new int[][]{
                        {0, 0, 1, 0},
                        {0, 1, 1, 0},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {0, 0, 1, 0},
                        {0, 1, 1, 1}};
            case "2":
                return new int[][]{
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {0, 0, 0, 1},
                        {0, 0, 1, 0},
                        {0, 1, 0, 0},
                        {1, 0, 0, 0},
                        {1, 1, 1, 1}};
            case "3":
                return new int[][]{
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {0, 0, 0, 1},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}};
            case "4":
                return new int[][]{
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {1, 1, 1, 1},
                        {0, 0, 0, 1},
                        {0, 0, 0, 1},
                        {0, 0, 0, 1}};
            case "5":
                return new int[][]{
                        {1, 1, 1, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {0, 0, 0, 1},
                        {0, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}};
            case "6":
                return new int[][]{
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 0},
                        {1, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}};
            case "7":
                return new int[][]{
                        {1, 1, 1, 1},
                        {0, 0, 0, 1},
                        {0, 0, 0, 1},
                        {0, 0, 1, 0},
                        {0, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 1, 0, 0}};
            case "8":
                return new int[][]{
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}};
            case "9":
                return new int[][]{
                        {0, 1, 1, 0},
                        {1, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 1},
                        {0, 0, 0, 1},
                        {1, 0, 0, 1},
                        {0, 1, 1, 0}};
        }
        return new int[7][4];
    }

}
