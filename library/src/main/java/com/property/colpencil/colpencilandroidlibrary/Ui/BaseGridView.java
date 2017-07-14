package com.property.colpencil.colpencilandroidlibrary.Ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.property.colpencil.colpencilandroidlibrary.R;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 16 28
 */
public class BaseGridView extends GridView {

    public BaseGridView(Context context) {
        super(context);
        init();
    }

    public BaseGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
//		setSelector  点击显示的颜色
//		setCacheColorHint  拖拽的颜色
//		setDivider  每个条目的间隔	的分割线
        this.setSelector(R.drawable.nothing);  // 什么都没有
        this.setCacheColorHint(R.drawable.nothing);

    }
}
