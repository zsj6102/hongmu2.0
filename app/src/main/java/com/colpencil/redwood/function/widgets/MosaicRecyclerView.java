package com.colpencil.redwood.function.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class MosaicRecyclerView extends RecyclerView {
    public MosaicRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MosaicRecyclerView(Context context) {
        super(context);
    }

    public MosaicRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
