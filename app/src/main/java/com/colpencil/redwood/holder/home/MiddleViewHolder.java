package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RemoduleVo;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.adapter.HomeMiddleAdapter;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;

import java.util.List;

public class MiddleViewHolder extends Decomposers<List<RemoduleVo>> {

    private MosaicListView recycler;

    public MiddleViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_pager_middle, null);
        recycler = (MosaicListView) view.findViewById(R.id.middle_recycler);
        return view;
    }

    @Override
    public void refreshView(List<RemoduleVo> remoduleVos, int position) {
        recycler.setAdapter(new HomeMiddleAdapter(remoduleVos, mContext));
    }
}
