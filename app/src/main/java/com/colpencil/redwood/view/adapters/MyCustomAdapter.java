package com.colpencil.redwood.view.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Custom;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.discovery.CustomActivity;
import com.colpencil.redwood.view.activity.mine.CustomDetailActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者：我的定制
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 01
 */
public class MyCustomAdapter extends BGARecyclerViewAdapter<Custom> {

    private String mTypeFlag;//处理类型的标识

    private OnMyItemClickListener listener;

    public MyCustomAdapter(RecyclerView recyclerView, String tyleFlag) {
        super(recyclerView, R.layout.item_myweekshoot);
        this.mTypeFlag = tyleFlag;
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, final int position, final Custom custom) {
        helper.getView(R.id.current_price).setVisibility(View.GONE);
        helper.setText(R.id.itemMyWeekShoot_Time, TimeUtil.timeFormat(custom.getTime(), "yyyy-MM-dd HH:mm"));
        helper.setText(R.id.itemMyWeekShootPrice, "¥" + custom.getPrice());
        helper.setText(R.id.itemMyWeekShootName, custom.getName());
        ImageLoaderUtils.loadImage(mContext, custom.getImg(), (ImageView) helper.getView(R.id.itemMyWeekShoot_img));
        helper.getView(R.id.item_myweekShoot).setVisibility(View.GONE);
        if (custom.getStateName() == 0) {       //待审核
            helper.setText(R.id.itemMyWeekShoot__sumbit, "待审核");
            helper.getView(R.id.itemMyWeekShootPrice).setVisibility(View.GONE);
        } else if (custom.getStateName() == 1) {    //立即购买
            helper.getView(R.id.itemMyWeekShootPrice).setVisibility(View.VISIBLE);
            helper.setText(R.id.itemMyWeekShoot__sumbit, "立即购买");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnClick(20, custom);
                }
            });
        } else if (custom.getStateName() == 2) {    //待付款
            helper.getView(R.id.itemMyWeekShootPrice).setVisibility(View.VISIBLE);
            helper.setText(R.id.itemMyWeekShoot__sumbit, "待付款");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnClick(20, custom);
                }
            });
        } else if (custom.getStateName() == 3) {    //新的定制
            helper.getView(R.id.itemMyWeekShootPrice).setVisibility(View.VISIBLE);
            helper.setText(R.id.itemMyWeekShoot__sumbit, "新的定制");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnClick(21, custom);
                }
            });
        } else if (custom.getStateName() == 4) {    //待付首款
            helper.getView(R.id.itemMyWeekShootPrice).setVisibility(View.VISIBLE);
            helper.setText(R.id.itemMyWeekShoot__sumbit, "待付首款");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnClick(20, custom);
                }
            });
        } else if (custom.getStateName() == 5) {    //待付尾款
            helper.getView(R.id.itemMyWeekShootPrice).setVisibility(View.VISIBLE);
            helper.setText(R.id.itemMyWeekShoot__sumbit, "待付尾款");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnClick(20, custom);
                }
            });
        } else if (custom.getStateName() == 6) {    //已付尾款
            helper.getView(R.id.itemMyWeekShootPrice).setVisibility(View.VISIBLE);
            helper.setText(R.id.itemMyWeekShoot__sumbit, "已付尾款");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnClick(20, custom);
                }
            });
        } else {    //已取消
            helper.getView(R.id.itemMyWeekShootPrice).setVisibility(View.VISIBLE);
            helper.setText(R.id.itemMyWeekShoot__sumbit, "新的定制");
            helper.getView(R.id.itemMyWeekShoot__sumbit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sumbitOnClick(21, custom);
                }
            });
        }

        if (custom.getCm_status() == 0) {   //定制中
            helper.setText(R.id.itemMyWeekShoot_State, "定制中");
            helper.getView(R.id.item_iv_delete).setVisibility(View.GONE);
        } else if (custom.getCm_status() == 1) {    //已完成
            helper.setText(R.id.itemMyWeekShoot_State, "已完成");
            helper.getView(R.id.item_iv_delete).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.delete(position);
                }
            });
        } else {
            helper.setText(R.id.itemMyWeekShoot_State, "已取消");
            helper.getView(R.id.item_iv_delete).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.delete(position);
                }
            });
        }
        if (custom.getNum() == 0) {
            helper.setText(R.id.itemMyWeekShootCount, "X1");
        } else {
            helper.setText(R.id.itemMyWeekShootCount, "X" + custom.getNum());
        }
        if (position == mDatas.size() - 1) {
            helper.getView(R.id.item_myweekShoot).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_myweekShoot).setVisibility(View.GONE);
        }
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (custom.getType() == 0) {    //私人定制
                    Intent intent = new Intent(mContext, CustomDetailActivity.class);
                    intent.putExtra("id", custom.getCm_id());
                    mContext.startActivity(intent);
                } else {    //官方定制
                    Intent intent = new Intent(mContext, CustomActivity.class);
                    intent.putExtra("officialid", custom.getOc_id());
                    mContext.startActivity(intent);
                }
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

    public void setListener(OnMyItemClickListener listener) {
        this.listener = listener;
    }

    private void sumbitOnClick(int typeFlag, Custom custom) {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(typeFlag);
        rxBusMsg.setMsg(mTypeFlag);
        if (custom.getStateName() == 1 || custom.getStateName() == 4 || custom.getStateName() == 2) {
            rxBusMsg.setOrderNum(custom.getOrder_id() + "");

        } else if (custom.getStateName() == 5) {
            rxBusMsg.setOrderNum(custom.getOrder_id_sub() + "");
        }
        rxBusMsg.setCustomType(custom.getType());
        rxBusMsg.setGoods_id(custom.getGoods_id());
        rxBusMsg.setProduct_id(custom.getProduct_id());
        rxBusMsg.setStateName(custom.getStateName());
        rxBusMsg.setCpns_id(custom.getCm_id());
        RxBus.get().post("rxBusMsg", rxBusMsg);
    }


    public interface OnMyItemClickListener {
        void itemClick(int position);

        void delete(int position);
    }
}

