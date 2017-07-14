package com.colpencil.redwood.holder.adapter;

import android.content.Context;

import com.colpencil.redwood.bean.RemoduleVo;
import com.colpencil.redwood.function.widgets.list.BasicAdapterSuper;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.home.MiddleItemViewHolder;

import java.util.List;

public class HomeMiddleAdapter extends BasicAdapterSuper<RemoduleVo> {

    public HomeMiddleAdapter(List<RemoduleVo> datas, Context context) {
        super(datas, context);
    }

    @Override
    protected Decomposers<RemoduleVo> getHolder(int position) {
        return new MiddleItemViewHolder(position, mContext);
    }
}
