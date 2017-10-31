package com.colpencil.redwood.holder;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Goods_list;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.adapter.GoodItemAdapter;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;
import java.util.List;



/**
 * 通用的广告加商品
 */
public class GoodItemsViewHolder extends Decomposers<List<Goods_list>> {
    private MosaicListView gridView;
    public GoodItemsViewHolder(int position, Context context) {
        super(position, context);
    }
    @Override
    public View initView(int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.homepage_item_goods, null);
        gridView = (MosaicListView) itemView.findViewById(R.id.home_good_recycler);
        return itemView;
    }
    @Override
    public void refreshView(final List<Goods_list> vo, int position) {
        gridView.setAdapter(new GoodItemAdapter(vo,mContext));

    }

}
