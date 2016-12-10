package com.smartown.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Author:Tiger
 * <p>
 * CrateTime:2016-11-02 16:45
 * <p>
 * Description:
 */
public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.Item> implements View.OnClickListener {

    private Context context;
    private AdapterHelper helper;

    public SimpleTextAdapter(Context context, AdapterHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Item(LayoutInflater.from(context).inflate(R.layout.item_simple_text, null), helper.getItemValues());
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {
        holder.textView.setText(helper.getItem(position));
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return helper.getSize();
    }

    @Override
    public void onClick(View view) {
        helper.onClick((Integer) view.getTag());
    }

    public static abstract class AdapterHelper {

        private ItemValues itemValues;

        public AdapterHelper(ItemValues itemValues) {
            this.itemValues = itemValues;
        }

        public abstract int getSize();

        public abstract String getItem(int position);

        public abstract void onClick(int position);

        public ItemValues getItemValues() {
            return itemValues;
        }

        public void setItemValues(ItemValues itemValues) {
            this.itemValues = itemValues;
        }

        public static class ItemValues {

            private int textSize;
            private int textColor;

            public ItemValues(int textSize, int textColor) {
                this.textSize = textSize;
                this.textColor = textColor;
            }

            public int getTextSize() {
                return textSize;
            }

            public void setTextSize(int textSize) {
                this.textSize = textSize;
            }

            public int getTextColor() {
                return textColor;
            }

            public void setTextColor(int textColor) {
                this.textColor = textColor;
            }
        }

    }

    public static class Item extends RecyclerView.ViewHolder {

        private TextView textView;

        public Item(View itemView, AdapterHelper.ItemValues itemValues) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.simple_text);
            final int screenWidth = itemView.getContext().getResources().getDisplayMetrics().widthPixels;
            final double scale = itemView.getContext().getResources().getDisplayMetrics().density;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, itemValues.getTextSize());
            textView.setTextColor(itemValues.getTextColor());
            textView.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, (int) (scale * 48)));
        }
    }

    public static class ItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable drawable;

        public ItemDecoration(int color) {
            drawable = new ColorDrawable(color);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if (drawable != null) {
                drawDivider(c, parent);
            }
        }

        //画横线, 这里的parent其实是显示在屏幕显示的这部分
        private void drawDivider(Canvas c, RecyclerView parent) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);

                //获得child的布局信息
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + 1;
                drawable.setBounds(left, top, right, bottom);
                drawable.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 1);
        }
    }

}
