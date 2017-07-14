package com.colpencil.redwood.holder.adapter;

import android.content.Context;

import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.function.widgets.list.BasicAdapterSuper;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.cyclo.CycloItemViewHolder;

import java.util.List;

public class CycloItemAdapter extends BasicAdapterSuper<CyclopediaInfoVo> {

    public CycloItemAdapter(List<CyclopediaInfoVo> datas, Context context) {
        super(datas, context);
    }

    @Override
    protected Decomposers<CyclopediaInfoVo> getHolder(int position) {
        return new CycloItemViewHolder(position,mContext);
    }
}
