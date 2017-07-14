package com.colpencil.redwood.view.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.MyWeekShootItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.home.ProductdetailsActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者：我的周拍
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 01
 */
public class MyWeekShootAdapter extends BGARecyclerViewAdapter<MyWeekShootItem> {

    private String mTypeFlag;//处理类型的标识
    private ItemClickListener listener;

    public MyWeekShootAdapter(RecyclerView recyclerView, String tyleFlag) {
        super(recyclerView, R.layout.item_myweekshoot);
        this.mTypeFlag = tyleFlag;
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, final int position, final MyWeekShootItem item) {
        helper.setText(R.id.itemMyWeekShoot_Time, TimeUtil.timeFormat(item.getTime(), "yyyy-MM-dd"));
        ImageLoaderUtils.loadImage(mContext, item.getImg(), (ImageView) helper.getView(R.id.itemMyWeekShoot_img));
        helper.setText(R.id.itemMyWeekShootName, item.getGoods_name());
        helper.setText(R.id.itemMyWeekShootPrice, "¥" + item.getPrice());
        helper.getView(R.id.item_myweekShoot).setVisibility(View.GONE);
        if (position == mDatas.size() - 1) {
            helper.getView(R.id.item_myweekShoot).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_myweekShoot).setVisibility(View.GONE);
        }
        if (item.getStatusName() == 0) {   //竞拍失败
            helper.setText(R.id.itemMyWeekShoot_State, "竞拍失败");
        } else if (item.getStatusName() == 1) {     //竞拍成功
            helper.setText(R.id.itemMyWeekShoot_State, "已拍得");
        } else if (item.getStatusName() == 2) {     //可购买
            helper.setText(R.id.itemMyWeekShoot_State, "可购买");
        } else if (item.getStatusName() == 3) {     //待付款
            helper.setText(R.id.itemMyWeekShoot_State, "待付款");
        } else if (item.getStatusName() == 4) {      //已付款
            helper.setText(R.id.itemMyWeekShoot_State, "已付款");
        } else {    //竞拍中
            helper.setText(R.id.itemMyWeekShoot_State, "竞拍中");
        }
        helper.getView(R.id.current_price).setVisibility(View.VISIBLE);
        helper.setText(R.id.current_price, "当前拍价：￥" + item.getSpot_price());
        if (item.getStataName() == 0) {     //立即购买
            helper.setText(R.id.itemMyWeekShoot__sumbit, "立即购买");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnClick(18, item);
                }
            });
        } else if (item.getStataName() == 1) {      //立即付款
            helper.setText(R.id.itemMyWeekShoot__sumbit, "立即付款");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnClick(65, item);
                }
            });
        } else if (item.getStataName() == 2) {      //已付款
            helper.setText(R.id.itemMyWeekShoot__sumbit, "已付款");
        } else if (item.getStataName() == 3) {      //去竞拍
            helper.setText(R.id.itemMyWeekShoot__sumbit, "去竞拍");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getStatusName() == 5) {       //竞拍中
                        Intent intent = new Intent(mContext, ProductdetailsActivity.class);
                        intent.putExtra("goodsId", item.getId());
                        mContext.startActivity(intent);
                    } else if (item.getStatusName() == 0) {     //竞拍失败
                        sumbitOnClick(19, item);
                    }
                }
            });
        } else {      //已购买
            helper.setText(R.id.itemMyWeekShoot__sumbit, "已购买");
        }
        if (item.getStatus() == 2) {
            if (item.getBuy_status() == 3 || item.getBuy_status() == 4 || item.getResult() == 0) {
                helper.getView(R.id.delete_iv).setVisibility(View.VISIBLE);
                helper.getView(R.id.delete_iv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.deleteClick(position);
                    }
                });
            } else {
                helper.getView(R.id.delete_iv).setVisibility(View.GONE);
            }
        } else {
            helper.getView(R.id.delete_iv).setVisibility(View.GONE);
        }
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(position);
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

    private void sumbitOnClick(int typeFlag, MyWeekShootItem myWeekShootItem) {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(typeFlag);
        rxBusMsg.setMsg(mTypeFlag);
        rxBusMsg.setProduct_id(myWeekShootItem.getProduct_id());
        rxBusMsg.setGoods_id(myWeekShootItem.getGoods_id());
        rxBusMsg.setOrder_id(myWeekShootItem.getOrder_id());
        RxBus.get().post("rxBusMsg", rxBusMsg);
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener {
        void itemClick(int position);

        void deleteClick(int position);
    }
}

