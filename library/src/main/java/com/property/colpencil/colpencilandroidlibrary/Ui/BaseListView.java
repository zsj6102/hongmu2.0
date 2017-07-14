package com.property.colpencil.colpencilandroidlibrary.Ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.property.colpencil.colpencilandroidlibrary.R;


/**
 * @author 作者：LigthWang
 * @version 1.0
 * @date 创建时间：2015年8月30日 上午10:29:54
 * @parameter 内容描述：listView的基类封装
 * @return
 */
@SuppressWarnings("ALL")
public class BaseListView extends ListView {

    public BaseListView(Context context) {
        super(context);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//		setSelector  点击显示的颜色
//		setCacheColorHint  拖拽的颜色
//		setDivider  每个条目的间隔	的分割线
        this.setSelector(R.drawable.nothing);  // 什么都没有
        this.setCacheColorHint(R.drawable.nothing);
        this.setDivider(getResources().getDrawable(R.drawable.nothing));
    }
}
