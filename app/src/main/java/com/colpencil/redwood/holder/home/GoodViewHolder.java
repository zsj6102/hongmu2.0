package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.view.adapters.HomeGoodListAdapter;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.List;

public class GoodViewHolder extends Decomposers<List<HomeGoodInfo>> {

    private MosaicGridView recycler;

    public GoodViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_page_good, null);
        recycler = (MosaicGridView) view.findViewById(R.id.home_good_recycler);
        return view;
    }

    @Override
    public void refreshView(List<HomeGoodInfo> list, int position) {
        recycler.setAdapter(new HomeGoodListAdapter(mContext, list, R.layout.item_home_good));
    }
}
