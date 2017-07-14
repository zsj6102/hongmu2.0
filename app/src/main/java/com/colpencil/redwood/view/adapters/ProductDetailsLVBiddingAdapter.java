package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BidderInfoVo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * 描述 竞拍信息listview 适配器
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/11 11 04
 */
public class ProductDetailsLVBiddingAdapter extends CommonAdapter<BidderInfoVo> {

    private Context context;
    private boolean isFinish;

    public ProductDetailsLVBiddingAdapter(Context context, List<BidderInfoVo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(CommonViewHolder holder, BidderInfoVo item, int position) {
        holder.setImageByUrl(context, R.id.item_iv_biddingcontent, item.getFace());
        holder.setText(R.id.item_auction_nickname, item.getName());
        holder.setText(R.id.item_auction_price, "￥" + item.getOffer());
        TextView tv_state = holder.getView(R.id.item_product_hasbid);
        if (position == 0) {
            tv_state.setTextColor(context.getResources().getColor(R.color.main_red));
            if (isFinish) {
                holder.setText(R.id.item_product_hasbid, "获胜者");
            } else {
                holder.setText(R.id.item_product_hasbid, "已出价");
            }
        } else {
            holder.setText(R.id.item_product_hasbid, "出局");
            tv_state.setTextColor(context.getResources().getColor(R.color.text_color_thirst));
        }
    }

    public void setState(boolean isFinish) {
        this.isFinish = isFinish;
        notifyDataSetChanged();
    }
}
