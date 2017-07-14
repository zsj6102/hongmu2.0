package com.property.colpencil.colpencilandroidlibrary.Ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.property.colpencil.colpencilandroidlibrary.R;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 15 03
 */
public class WrapHeightListView extends ListView {
    public WrapHeightListView(Context context) {
        super(context);
        init();
    }

    public WrapHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WrapHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    private void init() {
        this.setSelector(R.drawable.nothing);  // 什么都没有
    }
}

