package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CartItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/14 11 23
 */
public class ShoppingCartAdapter extends CommonAdapter<CartItem> {

    public ShoppingCartAdapter(Context context, List<CartItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final CartItem item, final int position) {
        if(position == 0){
            holder.getView(R.id.line_view).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.line_view).setVisibility(View.VISIBLE);
        }
        holder.setText(R.id.item_shoppingcart_count, item.getNum() + "");
        holder.setText(R.id.iv_shoppingCartTitle, item.getName());
        holder.setText(R.id.iv_shoppingCartExplain, item.getSpecs());
        holder.setText(R.id.iv_shoppingCartPrice, "￥" + item.getPrice());
        holder.setImageByUrl(mContext, R.id.iv_shoppingCartImg, item.getImage_default());
        if (item.isChooseState()) {
            ((ImageView) (holder.getView(R.id.iv_shoppingCartSelect))).setImageResource(R.mipmap.select_yes_red);
        } else {
            ((ImageView) (holder.getView(R.id.iv_shoppingCartSelect))).setImageResource(R.mipmap.select_no);
        }

        if(item.getNum() <= 1){
            holder.getView(R.id.item_shoppingcart_reduce).setClickable(false);
        } else {
            holder.getView(R.id.item_shoppingcart_reduce).setClickable(true);
            holder.getView(R.id.item_shoppingcart_reduce).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(1);
                    rxBusMsg.setPositonGroupMsg(position);
                    rxBusMsg.setMsg("reduce");
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                }
            });
        }
        holder.getView(R.id.ll_good).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodDetailActivity.class);
                intent.putExtra("goodsId", item.getGoods_id());
                mContext.startActivity(intent);
            }
        });
        holder.getView(R.id.item_shoppingcart_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(1);
                rxBusMsg.setPositonGroupMsg(position);
                rxBusMsg.setMsg("add");
                RxBus.get().post("rxBusMsg", rxBusMsg);
            }
        });
        holder.getView(R.id.iv_shoppingCartSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(1);
                rxBusMsg.setPositonGroupMsg(position);
                if (item.isChooseState()) {
                    item.setChooseState(false);
                    rxBusMsg.setMsg(item.getCatid() + "");
                } else {
                    item.setChooseState(true);
                    rxBusMsg.setMsg("");
                }
                RxBus.get().post("rxBusMsg", rxBusMsg);
                notifyDataSetChanged();
            }
        });
    }
}
