package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Postages;
import com.colpencil.redwood.bean.RxBusMsg;
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
public class PostagesAdapter extends CommonAdapter<Postages> {

    public PostagesAdapter(Context context, List<Postages> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final Postages postages, final int position) {
        holder.setText(R.id.tvTitle_item_payment_other, postages.getPostageName() + "(" + postages.getPostagePrice() + "元)");
        holder.getView(R.id.iv_item_payment_other).setVisibility(View.GONE);
        if (mDatas.size() - 1 == position) {
            holder.getView(R.id.item_payment_other).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.item_payment_other).setVisibility(View.VISIBLE);
        }
        if (!postages.isChooseState()) {
            ((ImageView) (holder.getView(R.id.select_item_payment_other))).setImageResource(R.mipmap.select_no);
        } else {
            ((ImageView) (holder.getView(R.id.select_item_payment_other))).setImageResource(R.mipmap.select_yes_red);
        }
        holder.getView(R.id.select_item_payment_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postages.isChooseState() == false) {
                    RxBusMsg rxBusMsg = new RxBusMsg();
                    rxBusMsg.setType(41);
                    rxBusMsg.setPosition(position);
                    RxBus.get().post("rxBusMsg", rxBusMsg);
                }
            }
        });
    }
}
