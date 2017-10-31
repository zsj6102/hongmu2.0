package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Goods_list;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.adapter.GoodItemAdapter;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页其他推荐 广告加商品list
 */
public class GoodItemViewHolder extends Decomposers<ResultInfo<List<Goods_list>>> {

    private MosaicListView recyclerView;
    public GoodItemViewHolder(int position, Context context) {
        super(position, context);
    }
    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_goods, null);
        recyclerView = (MosaicListView) view.findViewById(R.id.item_middle_recycler);
        return view;
    }

    @Override
    public void refreshView(ResultInfo<List<Goods_list>> remoduleVo, int position) {
        List<Goods_list> list = new ArrayList<>();
        list.addAll(remoduleVo.getData());
        recyclerView.setAdapter(new GoodItemAdapter(list,mContext));
    }
}
