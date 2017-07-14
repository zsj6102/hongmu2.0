package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.WeekAuctionList;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.home.ProductdetailsActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RushBuyCountDownTimerView;

import java.math.BigDecimal;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 10 58
 */
public class WeekShootAdapter extends BGARecyclerViewAdapter<WeekAuctionList> {

    private Context context;
    private int width;
    private int height;

    public WeekShootAdapter(RecyclerView recyclerView, Context context) {
        super(recyclerView, R.layout.item_weekshoot);
        this.context = context;
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, WeekAuctionList model) {
        RushBuyCountDownTimerView weekShoot_time = (RushBuyCountDownTimerView) helper.getConvertView().findViewById(R.id.weekShoot_time);
        if (position == 0) {
            helper.getView(R.id.view_weekShoot).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_weekShoot).setVisibility(View.GONE);
        }
        helper.setText(R.id.the_activity_time, TimeUtil.timeFormat(model.getBegin_time(), "yyyy-MM-dd HH:mm:ss")
                + " ~ "
                + TimeUtil.timeFormat(model.getEnd_time(), "yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.item_weekshoot_goodname, "【" + model.getType_name() + "】" + model.getGoods_name());
        BigDecimal bd = new BigDecimal(Double.toString(model.getSpot_price()));
        helper.setText(R.id.item_weekshoot_goodprice, "￥" + bd.toPlainString());
        ImageLoaderUtils.loadImage(mContext, model.getImg(), (ImageView) helper.getView(R.id.item_weekshoot_goodimg));
        final int goodsid = model.getId();
        LinearLayout ll_content = helper.getView(R.id.ll_content);
        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductdetailsActivity.class);
                intent.putExtra("goodsId", goodsid);
                context.startActivity(intent);
            }
        });
        if (model.getStatus() == 0) {       //未开始
            if (TimeUtil.getDay(model.getSystemTime(), model.getBegin_time()) > 0) {
                helper.getView(R.id.weekShoot_day).setVisibility(View.VISIBLE);
                helper.setText(R.id.weekShoot_day, TimeUtil.getDay(model.getSystemTime(), model.getBegin_time()) + "天");
            } else {
                helper.getView(R.id.weekShoot_day).setVisibility(View.GONE);
                helper.setText(R.id.weekShoot_day, 0 + "天");
            }
            helper.setText(R.id.weekShoot_tv, "距离开始：");
            weekShoot_time.setTime(TimeUtil.getHourse(model.getSystemTime(), model.getEnd_time()),
                    TimeUtil.getMinute(model.getSystemTime(), model.getBegin_time()),
                    TimeUtil.getSecond(model.getSystemTime(), model.getBegin_time()));
            weekShoot_time.start();
        } else if (model.getStatus() == 1) {        //竞拍中
            if (TimeUtil.getDay(model.getSystemTime(), model.getEnd_time()) > 0) {
                helper.getView(R.id.weekShoot_day).setVisibility(View.VISIBLE);
                helper.setText(R.id.weekShoot_day, TimeUtil.getDay(model.getSystemTime(), model.getEnd_time()) + "天");
            } else {
                helper.getView(R.id.weekShoot_day).setVisibility(View.GONE);
                helper.setText(R.id.weekShoot_day, 0 + "天");
            }
            helper.setText(R.id.weekShoot_tv, "距离结束：");
            weekShoot_time.setTime(TimeUtil.getHourse(model.getSystemTime(), model.getEnd_time()),
                    TimeUtil.getMinute(model.getSystemTime(), model.getEnd_time()),
                    TimeUtil.getSecond(model.getSystemTime(), model.getEnd_time()));
            weekShoot_time.start();
        } else {
            helper.getView(R.id.weekShoot_day).setVisibility(View.GONE);
            helper.setText(R.id.weekShoot_day, 0 + "天");
            helper.setText(R.id.weekShoot_tv, "已结束：");
        }
        final ImageView iv = helper.getView(R.id.item_weekshoot_goodimg);
        if (width == 0 || height == 0) {
            iv.post(new Runnable() {
                @Override
                public void run() {
                    width = iv.getWidth();
                    height = width;
                    iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
            });
        } else {
            iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }
    }

    /**
     * 如果要实现Item 项的单击事件，该方法必须填写
     *
     * @param helper
     */
    @Override
    public void setItemChildListener(BGAViewHolderHelper helper) {

    }

}
