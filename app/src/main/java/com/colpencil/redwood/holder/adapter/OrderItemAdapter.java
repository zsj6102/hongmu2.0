package com.colpencil.redwood.holder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.result.OrderArray;
import com.colpencil.redwood.bean.result.OrderGoodsItem;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OrderItemAdapter extends BaseAdapter{

    private static final int TYPE_ITEM = 0;  //
    private static final int TYPE_SEPARATOR = 1; //时间分割
    private List<OrderArray> mlist = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> ilist = new ArrayList<>();
    HashMap<Integer, View> localAppMap = new HashMap<Integer, View>();
    public OrderItemAdapter(Context context, List<OrderArray> list,List<Integer> slist) {
        this.mContext = context;
        this.mlist = list;
        this.ilist = slist;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = 0;
        if (null != mlist) {
            for (OrderArray category : mlist) {

                count += category.getItemCount();

            }
        }
        return count;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mlist || position < 0 || position > getCount()) {
            return TYPE_ITEM;
        }
        int categoryFirstIndex = 0;
        for (OrderArray category : mlist) {
            int size = category.getItemCount();
            int categoryIndex = position - categoryFirstIndex;
            if (categoryIndex == 0) {
                return TYPE_SEPARATOR;
            }
            categoryFirstIndex += size;
        }
        return TYPE_ITEM;
    }

    @Override
    public Object getItem(int position) {
        if (null == mlist || position < 0 || position > getCount()) {
            return null;
        }
        int categoryFirstIndex = 0;
        for (OrderArray category : mlist) {
           int size = category.getItemCount();
            int categoryIndex = position - categoryFirstIndex;
            if (categoryIndex < size) {
                return category.getItem(categoryIndex);
            }
            categoryFirstIndex += size;
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {


        int type = getItemViewType(position);

        switch (type) {

            case TYPE_SEPARATOR:
                if (null == view) {
                    view = mInflater.inflate(R.layout.item_special_past_group, null);
                }
                TextView textView = (TextView) view.findViewById(R.id.tv_time);
                textView.setText((String) getItem(position));
                break;
            case TYPE_ITEM:

                 ViewHolder holder  ;
                if (localAppMap.get(position) == null) {
                    view = mInflater.inflate(R.layout.order_childlist_layout, null);
                    holder = new  ViewHolder(view);
                    localAppMap.put(position,view);
                    view.setTag(holder);
//                    holder.iv_payForGood = (ImageView) view.findViewById(R.id.iv_payForGood);
//                    holder.name_payForGood = (TextView)view.findViewById(R.id.name_payForGood);
//                    holder.price_payForGood = (TextView)view.findViewById(R.id.price_payForGood);
//                    holder.type_payForGood = (TextView)view.findViewById(R.id.type_payForGood);
//                    holder.cout_payForGood = (TextView)view.findViewById(R.id.cout_payForGood);
//                    holder.bottomlayout = (LinearLayout)view.findViewById(R.id.bottom_layout);
//                    view.setTag(holder);
                } else {
                    view = localAppMap.get(position);
                    holder = ( ViewHolder) view.getTag();
                }

                if(ilist.contains(position)){
                    holder.bottomlayout.setVisibility(View.VISIBLE);
                }
                holder.name_payForGood.setText(((OrderGoodsItem)getItem(position)).getName());
                holder.price_payForGood.setText(((OrderGoodsItem)getItem(position)).getSalePrice()+"");
                holder.type_payForGood.setText(((OrderGoodsItem)getItem(position)).getSpecs());
//                holder.cout_payForGood.setText(((OrderGoodsItem)getItem(position)).getNum()+"");
//                ImgTool.getImgToolInstance(mContext).loadImgByString(((AllSpecialInfo) getItem(position)).getSpe_picture(), holder.iv_payForGood);
                break;
        }

        return view;
    }


    static class ViewHolder {
        ImageView iv_payForGood;
        TextView name_payForGood;
        TextView price_payForGood;
        TextView type_payForGood;
        TextView cout_payForGood;
        LinearLayout bottomlayout;
        public ViewHolder(View convertView){
            name_payForGood = (TextView)convertView.findViewById(R.id.name_payForGood);
            iv_payForGood = (ImageView)convertView.findViewById(R.id.iv_payForGood);
            bottomlayout =(LinearLayout)convertView.findViewById(R.id.bottom_layout);
            price_payForGood = (TextView)convertView.findViewById(R.id.price_payForGood);
            type_payForGood = (TextView)convertView.findViewById(R.id.type_payForGood);
        }

    }


}
