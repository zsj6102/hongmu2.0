package com.property.colpencil.colpencilandroidlibrary.Ui;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.property.colpencil.colpencilandroidlibrary.R;

/**
 * @Description: 不滑动的listview
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/7/11
 */
public class MosaicListView extends ListView{
    public MosaicListView(Context context) {
        super(context);
        init();
    }

    public MosaicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MosaicListView(Context context, AttributeSet attrs, int defStyleAttr) {
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
//		setSelector  点击显示的颜色
//		setCacheColorHint  拖拽的颜色
//		setDivider  每个条目的间隔	的分割线
        this.setSelector(R.drawable.nothing);  // 什么都没有
        this.setCacheColorHint(R.drawable.nothing);
        this.setDivider(getResources().getDrawable(R.drawable.nothing));
    }
}
