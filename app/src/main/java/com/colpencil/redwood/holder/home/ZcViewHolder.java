package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.adapter.GoodsSpecialAdapter;
import com.colpencil.redwood.view.activity.mine.AllSpecialActivity;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.List;

public class ZcViewHolder extends Decomposers<List<AllSpecialInfo>> {
    private MosaicGridView gridView;
    private TextView tv;
    public ZcViewHolder(int position, Context context) {
        super(position, context);
    }
    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_home_special, null);
        gridView = (MosaicGridView) view.findViewById(R.id.func_gridview);
        tv = (TextView)view.findViewById(R.id.all_special);
        return view;
    }

    @Override
    public void refreshView(List<AllSpecialInfo> coverSpecialItems, int position) {
       tv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(mContext, AllSpecialActivity.class);
               mContext.startActivity(intent);
           }
       });
        gridView.setAdapter(new GoodsSpecialAdapter(mContext,coverSpecialItems,R.layout.goods_special_item));
    }
}
