package com.colpencil.redwood.function.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 描述：RecyclerView 间距
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 14 56
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}
