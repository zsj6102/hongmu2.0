package com.colpencil.redwood.holder.adapter;

import android.content.Context;

import com.colpencil.redwood.bean.RecommendVo;
import com.colpencil.redwood.function.widgets.list.BasicAdapterSuper;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.home.MiddleItemsViewHolder;

import java.util.List;

public class MiddleItemAdapter extends BasicAdapterSuper<RecommendVo> {

    public MiddleItemAdapter(List<RecommendVo> datas, Context context) {
        super(datas, context);
    }

    @Override
    protected Decomposers<RecommendVo> getHolder(int position) {
        return new MiddleItemsViewHolder(position,mContext);
    }
}

