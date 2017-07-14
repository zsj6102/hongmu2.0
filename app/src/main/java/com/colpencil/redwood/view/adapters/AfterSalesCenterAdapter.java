package com.colpencil.redwood.view.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AfterSalesCenterItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.mine.OrderDetailsActivity;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

;

/**
 * 作者：售后中心
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 01
 */
public class AfterSalesCenterAdapter extends BGARecyclerViewAdapter<AfterSalesCenterItem> {

    private String mTypeFlag;//处理类型的标识

    public AfterSalesCenterAdapter(RecyclerView recyclerView, String tyleFlag) {
        super(recyclerView, R.layout.item_aftersalescenter);
        this.mTypeFlag = tyleFlag;
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, final AfterSalesCenterItem item) {
        helper.setText(R.id.itemAfter_No, "订单号:" + item.getSn());
        helper.setText(R.id.itemAfter_State, item.getStateName());
        helper.setText(R.id.good_name, item.getItems().getName());
        helper.setText(R.id.good_spec, item.getItems().getSpecs());
        helper.setText(R.id.item_buyGoodCount, "X" + item.getItems().getCount());
        helper.setText(R.id.item_buyGoodPrice, "￥" + item.getItems().getSalePrice());
        ImageLoaderUtils.loadImage(mContext, item.getItems().getGoodHeadPath(), (ImageView) helper.getView(R.id.item_goodimg));
        helper.getView(R.id.itemAfter_sumbit).setVisibility(View.GONE);
        if (item.getAfterSaleState() == 0) {
            helper.getView(R.id.apply_service).setVisibility(View.GONE);
            helper.getView(R.id.write_logistics).setVisibility(View.GONE);
            helper.getView(R.id.itemAfter_sumbit).setVisibility(View.VISIBLE);
            helper.setText(R.id.itemAfter_sumbit, "取消申请");
            helper.getView(R.id.itemAfter_sumbit).setOnClickListener(new View.OnClickListener() {//取消申请
                @Override
                public void onClick(View v) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("return_order_id", item.getAfterSaleId());
                    map.put("order_id", item.getOrder_id() + "");
                    map.put("item_id", item.getItems().getItem_id() + "");
                    sumbitOnClick(15, map);
                }
            });
        } else if (item.getAfterSaleState() == 1) {
            helper.getView(R.id.itemAfter_sumbit).setVisibility(View.GONE);
            helper.getView(R.id.write_logistics).setVisibility(View.GONE);
            helper.getView(R.id.apply_service).setVisibility(View.VISIBLE);
            helper.getView(R.id.apply_service).setOnClickListener(new View.OnClickListener() {  //联系客服
                @Override
                public void onClick(View v) {
                    HashMap<String, String> map = new HashMap<>();
                    sumbitOnClick(17, map);
                }
            });
        } else if (item.getAfterSaleState() == 2 || item.getAfterSaleState() == 4 || item.getAfterSaleState() == 5) {
            helper.getView(R.id.itemAfter_sumbit).setVisibility(View.GONE);
            helper.getView(R.id.apply_service).setVisibility(View.GONE);
            if (item.getItems().getIs_write() == 0) {
                helper.getView(R.id.apply_service).setOnClickListener(new View.OnClickListener() {  //联系客服
                    @Override
                    public void onClick(View v) {
                        HashMap<String, String> map = new HashMap<>();
                        sumbitOnClick(17, map);
                    }
                });
                helper.setText(R.id.write_logistics, "填写物流");
                helper.getView(R.id.write_logistics).setVisibility(View.VISIBLE);
                helper.getView(R.id.write_logistics).setOnClickListener(new View.OnClickListener() {//取消申请
                    @Override
                    public void onClick(View v) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("goodMsg", new Gson().toJson(item.getItems()));
                        map.put("afterId", item.getAfterSaleId());
                        sumbitOnClick(16, map);
                    }
                });
            } else {
                helper.setText(R.id.write_logistics, "已填写物流");
                helper.getView(R.id.write_logistics).setVisibility(View.VISIBLE);
            }
        } else {
            helper.getView(R.id.itemAfter_sumbit).setVisibility(View.GONE);
            helper.getView(R.id.write_logistics).setVisibility(View.GONE);
        }
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("status", 7);
                intent.putExtra("order_id", item.getOrder_id());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 如果要实现Item 项的单击事件，该方法必须填写
     *
     * @param viewHolderHelper
     */
    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }

    private void sumbitOnClick(int typeFlag, HashMap<String, String> map) {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(typeFlag);
        rxBusMsg.setMsg(mTypeFlag);
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        rxBusMsg.setMap(map);
        RxBus.get().post("rxBusMsg", rxBusMsg);
    }
}

