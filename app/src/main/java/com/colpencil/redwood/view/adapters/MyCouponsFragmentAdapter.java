package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Coupon;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * 作者：我的优惠券
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 01
 */
public class MyCouponsFragmentAdapter extends CommonAdapter<Coupon> {

    //0表示代金券，1表示优惠券
    private int mType;
    //0表示不可操作，1表示可操作
    private int mState;
    private ChangeListener listener;

    public MyCouponsFragmentAdapter(Context context, List<Coupon> mDatas, int itemLayoutId, int type, int state) {
        super(context, mDatas, itemLayoutId);
        this.mType = type;
        this.mState = state;
    }

    @Override
    public void convert(CommonViewHolder helper, Coupon item, final int position) {
        ImageLoaderUtils.loadImage(mContext, item.getCpns_img(), (ImageView) helper.getView(R.id.item_voucher_iv));
        helper.getView(R.id.item_voucher_choose).setVisibility(View.GONE);
        if (mType == 0) {   //代金券
            if (mState == 1) {      //可操作
                helper.getView(R.id.item_voucher_point).setVisibility(View.VISIBLE);
                helper.setText(R.id.item_voucher_point, "本代金券兑换需要" + item.getPoint() + "积分");
                helper.getView(R.id.item_voucher_change).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_voucher_change).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.change(position);
                    }
                });
            } else {      //不可操作
                helper.getView(R.id.item_voucher_point).setVisibility(View.GONE);
                helper.getView(R.id.item_voucher_change).setVisibility(View.GONE);
            }
        } else {    //优惠券
            if (mState == 1) {      //可操作
                helper.getView(R.id.item_voucher_point).setVisibility(View.GONE);
                helper.getView(R.id.item_voucher_change).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_voucher_change).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.change(position);
                    }
                });
            } else {      //不可操作
                helper.getView(R.id.item_voucher_change).setVisibility(View.GONE);
            }
        }
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void change(int position);
    }

}

