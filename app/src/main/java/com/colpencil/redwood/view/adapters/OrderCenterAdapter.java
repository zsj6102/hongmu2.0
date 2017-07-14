package com.colpencil.redwood.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.OrderItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Ui.WrapHeightListView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者：订单中心
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 01
 */
public class OrderCenterAdapter extends BGARecyclerViewAdapter<OrderItem> {

    private String mTypeFlag;//处理类型的标识

    public OrderCenterAdapter(RecyclerView recyclerView, String tyleFlag) {
        super(recyclerView, R.layout.item_ordercenterfragment);
        this.mTypeFlag = tyleFlag;
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, final int position, final OrderItem item) {
        helper.setText(R.id.itemOrder_No, "订单号" + item.getOrder_sn());
        helper.setText(R.id.itemOrder_State, item.getStatusName());
        if (position == mDatas.size() - 1) {
            helper.getView(R.id.bottomView_order).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.bottomView_order).setVisibility(View.GONE);
        }

        if (item.getStatus() == 0) {        //待付款
            helper.getView(R.id.delete_iv).setVisibility(View.GONE);
            helper.getView(R.id.ll_payment).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_btn1).setVisibility(View.GONE);
            helper.getView(R.id.tv_btn2).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_btn3).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_btn2, "取消订单");
            helper.setText(R.id.tv_btn3, "立即付款");
            helper.getView(R.id.tv_btn2).setOnClickListener(new View.OnClickListener() {        //取消订单
                @Override
                public void onClick(View v) {
                    sumbitOnclick(7, item, position);
                }
            });
            helper.getView(R.id.tv_btn3).setOnClickListener(new View.OnClickListener() {        //立即付款
                @Override
                public void onClick(View v) {
                    sumbitOnclick(8, item, position);
                }
            });
        } else if (item.getStatus() == 4) {       //已付款
            helper.getView(R.id.delete_iv).setVisibility(View.GONE);
            helper.getView(R.id.ll_payment).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_btn1).setVisibility(View.GONE);
            helper.getView(R.id.tv_btn3).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_btn2, "申请退款");
            helper.setText(R.id.tv_btn3, "提醒发货");
            if (item.getOrderitems().get(0).getState() == 12) {
                helper.getView(R.id.tv_btn2).setVisibility(View.GONE);
                helper.setText(R.id.itemOrder_State, item.getOrderitems().get(0).getItemstatus_name());
            } else {
                helper.getView(R.id.tv_btn2).setVisibility(View.VISIBLE);
                helper.setText(R.id.itemOrder_State, item.getStatusName());
            }
            helper.getView(R.id.tv_btn2).setOnClickListener(new View.OnClickListener() {        //申请退款
                @Override
                public void onClick(View v) {
                    sumbitOnclick(9, item, position);
                }
            });
            helper.getView(R.id.tv_btn3).setOnClickListener(new View.OnClickListener() {        //提醒发货
                @Override
                public void onClick(View v) {
                    sumbitOnclick(51, item, position);
                }
            });
        } else if (item.getStatus() == 5) {     //卖家已发货
            helper.getView(R.id.delete_iv).setVisibility(View.GONE);
            helper.getView(R.id.ll_payment).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_btn1).setVisibility(View.GONE);
            helper.getView(R.id.tv_btn2).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_btn3).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_btn1, "申请退款");
            helper.setText(R.id.tv_btn2, "查看物流");
            helper.setText(R.id.tv_btn3, "确认收货");
            if (item.getOrderitems().get(0).getState() == 12) {
                helper.getView(R.id.tv_btn1).setVisibility(View.GONE);
                helper.setText(R.id.itemOrder_State, item.getOrderitems().get(0).getItemstatus_name());
            } else {
                helper.getView(R.id.tv_btn1).setVisibility(View.GONE);
                helper.setText(R.id.itemOrder_State, item.getStatusName());
            }
            helper.getView(R.id.tv_btn1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnclick(9, item, position);
                }
            });
            helper.getView(R.id.tv_btn2).setOnClickListener(new View.OnClickListener() {        //查看物流
                @Override
                public void onClick(View v) {
                    sumbitOnclick(10, item, position);
                }
            });
            helper.getView(R.id.tv_btn3).setOnClickListener(new View.OnClickListener() {        //确认收货
                @Override
                public void onClick(View v) {
                    sumbitOnclick(11, item, position);
                }
            });
        } else if (item.getStatus() == 7 || item.getStatus() == 8) {     //已完成和已取消
            helper.getView(R.id.ll_payment).setVisibility(View.GONE);
            helper.getView(R.id.delete_iv).setVisibility(View.VISIBLE);
            helper.getView(R.id.delete_iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnclick(64, item, position);
                }
            });
            if (item.getOptType() == 5) {
                helper.getView(R.id.delete_iv).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.delete_iv).setVisibility(View.VISIBLE);
            }
        } else if (item.getStatus() == -1) {        //退款
            helper.getView(R.id.delete_iv).setVisibility(View.GONE);
            helper.getView(R.id.ll_payment).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_btn1).setVisibility(View.GONE);
            helper.getView(R.id.tv_btn2).setVisibility(View.GONE);
            helper.getView(R.id.tv_btn3).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_btn3, "取消退款");
            helper.getView(R.id.tv_btn3).setOnClickListener(new View.OnClickListener() {        //取消退款
                @Override
                public void onClick(View v) {
                    sumbitOnclick(12, item, position);
                }
            });
        } else {
            helper.getView(R.id.delete_iv).setVisibility(View.GONE);
            helper.getView(R.id.ll_payment).setVisibility(View.GONE);
        }
        helper.getView(R.id.goTo_orderdetails).setOnClickListener(new View.OnClickListener() {//进入订单详情
            @Override
            public void onClick(View v) {
                sumbitOnclick(52, item, position);
            }
        });
        ((WrapHeightListView) (helper.getView(R.id.itemOrder_GoodInfor))).setAdapter(
                new BuyGoodsInforAdapter(mContext,
                        item.getOrderitems(),
                        R.layout.item_buygoodsinfor,
                        mTypeFlag,
                        item.getOrder_id(),
                        item.getStatus(),
                        item.getOptType()));
    }

    /**
     * 如果要实现Item 项的单击事件，该方法必须填写
     *
     * @param viewHolderHelper
     */
    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }

    private void sumbitOnclick(int type, OrderItem orderItem, int position) {
        RxBusMsg msg = new RxBusMsg();
        msg.setType(type);
        msg.setMsg(mTypeFlag);
        msg.setOrderNum(orderItem.getOrder_id() + "");
        msg.setReturn_order_id(orderItem.getReturn_order_id());
        msg.setSn(orderItem.getOrder_sn());
        msg.setStatus(orderItem.getStatus());
        msg.setStateName(orderItem.getOrderitems().get(0).getState());
        msg.setPosition(position);
        RxBus.get().post("rxBusMsg", msg);
    }

}

