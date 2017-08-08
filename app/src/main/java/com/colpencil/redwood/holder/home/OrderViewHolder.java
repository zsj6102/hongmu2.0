package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.result.OrderArray;
import com.colpencil.redwood.function.widgets.list.Decomposers;

import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;


import java.util.ArrayList;
import java.util.List;


public class OrderViewHolder extends Decomposers<List<OrderArray>> {
    private MosaicListView listView;
    public OrderViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_listview, null);
        listView = (MosaicListView) view.findViewById(R.id.order_listView);
        return view;
    }

    @Override
    public void refreshView(List<OrderArray> list, int position) {
          List<Integer> mlist = new ArrayList<>();
        if(list!=null){

            int count = list.get(0).getItemCount()-1;
                for (OrderArray category : list) {
                    mlist.add(count);
                    count += category.getItemCount();
                }
        }
//        listView.setAdapter(new OrderItemAdapter(mContext,list,mlist));
    }
}
