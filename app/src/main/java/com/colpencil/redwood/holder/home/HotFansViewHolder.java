package com.colpencil.redwood.holder.home;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.function.widgets.list.Decomposers;

import com.colpencil.redwood.view.adapters.FansItemAdapter;

import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.List;

public class HotFansViewHolder extends Decomposers<List<ItemStoreFans>> {

    private MosaicGridView gridView;
    private LinearLayout morelayout;
    private FansItemAdapter adapter;
    @Override
    public View initView(int position) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.goods_fans_layout,null);
        gridView = (MosaicGridView) view.findViewById(R.id.func_gridview);
        morelayout = (LinearLayout)view.findViewById(R.id.ll_meEdit);
        return view;
    }

    @Override
    public void refreshView(final List<ItemStoreFans> itemStoreFanses, final int position) {
        adapter = new FansItemAdapter(mContext,itemStoreFanses, R.layout.item_laytou_fans);
        gridView.setAdapter(adapter);

    }

    public HotFansViewHolder(int position, Context context) {
        super(position, context);
    }
    public FansItemAdapter getAdapter(){
        return adapter;
    }

    public LinearLayout getMorelayout(){
        return morelayout;
    }
}
