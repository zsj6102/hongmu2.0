package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.bean.ChidrenCat;
import com.colpencil.redwood.function.widgets.list.BasicAdapterSuper;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.cyclo.HSearchGViewHolder;

import java.util.List;

/**
 * 描述：纵向搜索GridView 适配器
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/14 09 29
 */
public class HSearchGVAdapter extends BasicAdapterSuper<ChidrenCat> {

    public HSearchGVAdapter(List<ChidrenCat> datas, Context context) {
        super(datas, context);
    }

    @Override
    protected Decomposers<ChidrenCat> getHolder(int position) {
        return new HSearchGViewHolder(position, mContext);
    }
}
