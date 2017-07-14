package com.colpencil.redwood.function.widgets.list;

import android.content.Context;
import android.view.View;

public abstract class Decomposers<Data> {

    private View contentView;
    private Data data;
    private int position;
    public Context mContext;


    public Decomposers(int position, Context context) {
        this.mContext = context;
        contentView = initView(position);
        this.position = position;
        contentView.setTag(this);
    }

    /**
     * 创建界面
     */
    public abstract View initView(int position);

    public View getContentView() {
        return contentView;
    }

    /**
     * 必须调用此方法才会显示数据
     *
     * @param data
     */
    public void setData(Data data) {
        this.data = data;
        refreshView(data, position);
    }

    /**
     * 根据数据刷新界面
     */
    public abstract void refreshView(Data data, int position);

}
