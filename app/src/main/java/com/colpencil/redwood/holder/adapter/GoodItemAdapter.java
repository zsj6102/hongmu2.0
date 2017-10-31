package com.colpencil.redwood.holder.adapter;

import android.content.Context;

import com.colpencil.redwood.bean.Goods_list;
import com.colpencil.redwood.function.widgets.list.BasicAdapterSuper;
import com.colpencil.redwood.function.widgets.list.Decomposers;

import java.util.List;

public class GoodItemAdapter extends BasicAdapterSuper<Goods_list> {

    public GoodItemAdapter(List<Goods_list> datas, Context context) {
        super(datas, context);
    }

    @Override
    protected Decomposers<Goods_list> getHolder(int position) {
        return new GoodItemViewHolder(position,mContext);
    }
}
