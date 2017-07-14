package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.widgets.list.BasicAdapterSuper;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.cyclo.HotGoodViewHolder;

import java.util.List;

/**
 * 描述：个人中心 推荐适配器
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 10 54
 */
public class HotGoodAdapter extends BasicAdapterSuper<HomeGoodInfo> {

    public HotGoodAdapter(List<HomeGoodInfo> datas, Context context) {
        super(datas, context);
    }

    @Override
    protected Decomposers<HomeGoodInfo> getHolder(int position) {
        return new HotGoodViewHolder(position, mContext);
    }

}
