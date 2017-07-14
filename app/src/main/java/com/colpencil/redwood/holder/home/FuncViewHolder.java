package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.FuncPointVo;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.view.adapters.FuncItemAdapter;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.List;

public class FuncViewHolder extends Decomposers<List<FuncPointVo>> {

    private MosaicGridView gridview;

    public FuncViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_pager_func, null);
        gridview = (MosaicGridView) view.findViewById(R.id.func_gridview);
        return view;
    }

    @Override
    public void refreshView(List<FuncPointVo> list, int position) {
        gridview.setAdapter(new FuncItemAdapter(mContext, list, R.layout.item_home_func));
    }
}
