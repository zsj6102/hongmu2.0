package com.colpencil.redwood.function.widgets.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BasicAdapterSuper<T> extends BaseAdapter {

    protected List<T> datas;
    public Context mContext;

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public BasicAdapterSuper(List<T> datas, Context context) {
        this.datas = datas;
        // 给ListView设置条目的点击事件
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Decomposers holder;
        if (convertView == null) {
            holder = getHolder(position);
        } else {
            holder = (Decomposers) convertView.getTag();
        }

        holder.setData(datas.get(position));
        return holder.getContentView();
    }

    protected abstract Decomposers<T> getHolder(int position);


}
