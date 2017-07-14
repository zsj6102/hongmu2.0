package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodOfOrder;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * @author 曾 凤
 * @Description: 订单确认 商品适配器
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class PaymentGoodsAdapter extends CommonAdapter<GoodOfOrder> {

    public PaymentGoodsAdapter(Context context, List<GoodOfOrder> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, GoodOfOrder goodOfOrder, int position) {
        ImageLoaderUtils.loadImage(mContext, goodOfOrder.getImg(), (ImageView) holder.getView(R.id.iv_payForGood));
        holder.setText(R.id.name_payForGood, goodOfOrder.getName());
        holder.setText(R.id.price_payForGood, "¥" + goodOfOrder.getSalePrice());
        holder.setText(R.id.type_payForGood, goodOfOrder.getSpecs());
        holder.setText(R.id.cout_payForGood, "X" + goodOfOrder.getNum());
    }
}
