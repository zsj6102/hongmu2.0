package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.PayType;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.List;

/**
 * @author 曾 凤
 * @Description: 订单确认 商品适配器
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class PayTypeAdapter extends CommonAdapter<PayType> {

    public PayTypeAdapter(Context context, List<PayType> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final PayType payType, final int position) {
        View item_payment_other = holder.getConvertView().findViewById(R.id.item_payment_other);
        ImageView iv_item_payment_other = (ImageView) holder.getConvertView().findViewById(R.id.iv_item_payment_other);
        TextView tvTitle_item_payment_other = (TextView) holder.getConvertView().findViewById(R.id.tvTitle_item_payment_other);
        ImageView select_item_payment_other = (ImageView) holder.getConvertView().findViewById(R.id.select_item_payment_other);
        ImageLoaderUtils.loadImage(mContext, payType.getPayImgPath(), iv_item_payment_other);
        tvTitle_item_payment_other.setText(payType.getPayName());
        if (mDatas.size() - 1 == position) {
            item_payment_other.setVisibility(View.GONE);
        } else {
            item_payment_other.setVisibility(View.VISIBLE);
        }
        if (payType.isChooseState() == false) {
            select_item_payment_other.setImageResource(R.mipmap.select_no);
        } else {
            select_item_payment_other.setImageResource(R.mipmap.select_yes_red);
        }
        select_item_payment_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payType.isChooseState() == false) {
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(42);
                    rxBusMsg.setPosition(position);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                }

            }
        });
    }
}
