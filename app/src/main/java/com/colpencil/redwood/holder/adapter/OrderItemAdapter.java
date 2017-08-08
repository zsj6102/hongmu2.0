package com.colpencil.redwood.holder.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.OrderArray;
import com.colpencil.redwood.bean.result.OrderGoodsItem;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.OrderActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class OrderItemAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;  //
    private static final int TYPE_SEPARATOR = 1; //时间分割
    private List<OrderArray> mlist = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> integerList = new ArrayList<>();
    HashMap<Integer, View> localAppMap = new HashMap<Integer, View>();
    private MyListener listener;

    public OrderItemAdapter(Context context, List<OrderArray> list) {
        this.mContext = context;
        this.mlist = list;
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

    public void setListener(MyListener listener) {
        this.listener = listener;
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
        if (null != mlist) {
//            int count = mlist.get(0).getItemCount() - 1;
//            for (OrderArray category : mlist) {
//                integerList.add(count);
//                count += category.getItemCount();
//            }
            int count = 0;
            for(int i = 0; i < mlist.size(); i++){

                if(i == 0){
                    count = mlist.get(0).getItemCount()-1;
                    integerList.add(count);
                }else{
                    count += mlist.get(i).getItemCount();
                    integerList.add(count);
                }
            }
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
                    view = mInflater.inflate(R.layout.item_shopcar_title, null);
                }
                TextView textView = (TextView) view.findViewById(R.id.tv_time);
                textView.setText((String) getItem(position));
                break;
            case TYPE_ITEM:

                final ViewHolder holder;
                if (localAppMap.get(position) == null) {
                    view = mInflater.inflate(R.layout.order_childlist_layout, null);
                    holder = new ViewHolder(view);
                    localAppMap.put(position, view);
                    view.setTag(holder);
                } else {
                    view = localAppMap.get(position);
                    holder = (ViewHolder) view.getTag();
                }
                if (integerList != null && integerList.contains(position)) {
                    holder.bottomlayout.setVisibility(View.VISIBLE);
                    for (int k = 0; k < mlist.size(); k++) {
                        if (mlist.get(k).getGoodsItem().contains(getItem(position))) {
                            mlist.get(k).setNeedPay(mlist.get(k).getOrderPrice().getOriginalPrice() + mlist.get(k).getDeliverPrice() - mlist.get(k).getDaijin() - mlist.get(k).getYouhui());
                            RxBusMsg msg = new RxBusMsg();
                            msg.setType(100);
                            RxBus.get().post("rxBusMsg",msg);
                            holder.tvOriginPrice.setText("¥" + mlist.get(k).getOrderPrice().getOriginalPrice() + "");
                            holder.tvDk.setText("-" + "¥" + mlist.get(k).getOrderPrice().getDiscountPrice() + "");
                            if(mlist.get(k).getDeliver()!=null ){
                                holder.tvDeliver.setText(mlist.get(k).getDeliver()+"("+mlist.get(k).getDeliverPrice()+")");
                            }else{
                                holder.tvDeliver.setText("未选择");
                            }
                            holder.tvNeedPay.setText(mlist.get(k).getOrderPrice().getOriginalPrice() + mlist.get(k).getDeliverPrice() - mlist.get(k).getDaijin() - mlist.get(k).getYouhui()+"");
                            if(mlist.get(k).getYouhuiid()!= -1 ){
                                holder.tvYhq.setText("¥" + mlist.get(k).getYouhui());
                            }else{
                                holder.tvYhq.setText("未使用优惠券");
                            }
                            if( mlist.get(k).getDaijinid()!=null){
                                holder.tvDjq.setText("¥" + mlist.get(k).getDaijin());
                            }else{
                                holder.tvDjq.setText("未使用代金券");
                            }
                            holder.tvNeedPay.setText("¥" + mlist.get(k).getNeedPay());
                            holder.deliverLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    listener.deliverclick(position );
                                }
                            });
                            holder.djqLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    listener.djqClick(position );
                                }
                            });
                            holder.yhqLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    listener.yhqClick(position );
                                }
                            });
                            holder.postEditText.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    ((OrderActivity)mContext).saveEditData(position,charSequence.toString());
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });
                        }
                    }
                }
                holder.name_payForGood.setText(((OrderGoodsItem) getItem(position)).getName());
                holder.price_payForGood.setText("¥" + ((OrderGoodsItem) getItem(position)).getSalePrice() + "");
                holder.type_payForGood.setText(((OrderGoodsItem) getItem(position)).getSpecs());
                holder.cout_payForGood.setText("X" + ((OrderGoodsItem) getItem(position)).getNum() + "");
                ImgTool.getImgToolInstance(mContext).loadImgByString(((OrderGoodsItem) getItem(position)).getImg(), holder.iv_payForGood);
                break;
        }

        return view;
    }

    public interface MyListener {
        void deliverclick(int position );

        void yhqClick(int position);

        void djqClick(int position);
    }

    public static class ViewHolder {
        public ImageView iv_payForGood;
        public TextView name_payForGood;
        public TextView price_payForGood;
        public TextView type_payForGood;
        public TextView cout_payForGood;
        public LinearLayout bottomlayout;
        public RelativeLayout deliverLayout;
        public RelativeLayout yhqLayout;
        public RelativeLayout djqLayout;
        public EditText postEditText;
        public TextView tvDeliver;
        public TextView tvOriginPrice;
        public TextView tvYhq;
        public TextView tvDjq;
        public TextView tvDk;
        public TextView tvNeedPay;

        public ViewHolder(View convertView) {
            cout_payForGood = (TextView) convertView.findViewById(R.id.cout_payForGood);
            name_payForGood = (TextView) convertView.findViewById(R.id.name_payForGood);
            iv_payForGood = (ImageView) convertView.findViewById(R.id.iv_payForGood);
            bottomlayout = (LinearLayout) convertView.findViewById(R.id.bottom_layout);
            price_payForGood = (TextView) convertView.findViewById(R.id.price_payForGood);
            type_payForGood = (TextView) convertView.findViewById(R.id.type_payForGood);
            deliverLayout = (RelativeLayout) convertView.findViewById(R.id.deliver_layout);
            yhqLayout = (RelativeLayout) convertView.findViewById(R.id.layout_youhuiquan);
            djqLayout = (RelativeLayout) convertView.findViewById(R.id.layout_daijinquan);
            postEditText = (EditText) convertView.findViewById(R.id.post_content);
            tvDeliver = (TextView) convertView.findViewById(R.id.tv_deliver);
            tvOriginPrice = (TextView) convertView.findViewById(R.id.tv_originPrice);
            tvYhq = (TextView) convertView.findViewById(R.id.tv_youhuiquan);
            tvDjq = (TextView) convertView.findViewById(R.id.tv_daijinquan);
            tvDk = (TextView) convertView.findViewById(R.id.tv_discount);
            tvNeedPay = (TextView) convertView.findViewById(R.id.tv_needpay);
        }

    }


}
