package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodOfOrder;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.FormatUtils;

import java.util.List;

/**
 * 描述：我的订单购买商品信息适配器
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 14 17
 */
public class BuyGoodsInforAdapter extends CommonAdapter<GoodOfOrder> {

    private String mTypeFlag;//处理标识
    private RxBusMsg rxBusMsg = new RxBusMsg();
    private int morderId;//订单ID
    private int morderState;//订单状态
    private int type;

    public BuyGoodsInforAdapter(Context context, List<GoodOfOrder> mDatas, int itemLayoutId, String typeFlag, int orderId, int orderState, int type) {
        super(context, mDatas, itemLayoutId);
        this.mTypeFlag = typeFlag;
        this.morderId = orderId;
        this.morderState = orderState;
        this.type = type;
    }


    @Override
    public void convert(CommonViewHolder helper, final GoodOfOrder item, int position) {
        helper.setImageByUrl(mContext, R.id.orderCenter_iv, item.getImage());
        helper.setText(R.id.orderCenter_goodName, item.getName());
        helper.setText(R.id.orderCenter_goodPrice, "¥" + FormatUtils.formatDouble(item.getPrice()));
        helper.setText(R.id.orderCenter_goodType, item.getSpecs());
        helper.setText(R.id.orderCenter_goodCount, "X" + item.getNum());
        if (morderState == 0 || morderState == 8 || morderState == 2 || morderState == -1) {//0:未付款/新订单  8:作废   2：已付款  -1：退款中
            helper.getView(R.id.item_buyAfterSale).setVisibility(View.GONE);
            helper.getView(R.id.item_buyAfterEvaluation).setVisibility(View.GONE);
        } else if (morderState == 7) {//已完成
            if (item.getIsAfterSales() == 0) {
                if (type == 5) {
                    helper.getView(R.id.item_buyAfterSale).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.item_buyAfterSale).setVisibility(View.VISIBLE);
                }
                helper.setText(R.id.item_buyAfterSale, "申请售后");
                helper.getView(R.id.item_buyAfterSale).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sumbitOnClick(13, item);
                    }
                });
            } else {
                if (TextUtils.isEmpty(item.getItemstatus_name())) {
                    helper.getView(R.id.item_buyAfterSale).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.item_buyAfterSale).setVisibility(View.VISIBLE);
                    helper.setText(R.id.item_buyAfterSale, item.getItemstatus_name() + "中");
                }
            }
            if (item.getAddcommentflag() == 0) {
                helper.setText(R.id.item_buyAfterEvaluation, "去评价");
                if (type == 5) {
                    helper.getView(R.id.item_buyAfterEvaluation).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.item_buyAfterEvaluation).setVisibility(View.VISIBLE);
                }
                helper.getView(R.id.item_buyAfterEvaluation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sumbitOnClick(14, item);
                    }
                });
            } else {
                helper.setText(R.id.item_buyAfterEvaluation, "已评价");
                if (type == 5) {
                    helper.getView(R.id.item_buyAfterEvaluation).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.item_buyAfterEvaluation).setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void sumbitOnClick(int typeFlag, GoodOfOrder item) {
        rxBusMsg.setType(typeFlag);
        rxBusMsg.setMsg(mTypeFlag);
        rxBusMsg.setOrderNum(morderId + "");
        rxBusMsg.setGoods_id(item.getGoods_id());
        rxBusMsg.setItem_id(item.getItem_id());
        rxBusMsg.setName(item.getName());
        RxBus.get().post("rxBusMsg", rxBusMsg);
    }
}
