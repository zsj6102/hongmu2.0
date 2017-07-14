package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodsItem;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * @author 曾 凤
 * @Description: 商品
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class GoodsAdapter extends CommonAdapter<GoodsItem> {

    public GoodsAdapter(Context context, List<GoodsItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, GoodsItem goodsItem, int position) {
        holder.setImageByUrl(mContext, R.id.iv_goodCollection, goodsItem.getImage());
        holder.setText(R.id.title_goodCollection, goodsItem.getName());
        holder.setText(R.id.sales_goodCollection, "销量：" + goodsItem.getBuy_count());
        holder.setText(R.id.price_buyGoodPrice, "￥" + goodsItem.getPrice());
    }
}
