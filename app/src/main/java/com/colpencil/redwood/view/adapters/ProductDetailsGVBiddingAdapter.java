package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.WeekPersonList;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.util.List;

/**
 * 描述：竞拍者信息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/11 10 23
 */
public class ProductDetailsGVBiddingAdapter extends CommonAdapter<WeekPersonList> {

    public ProductDetailsGVBiddingAdapter(Context context, List<WeekPersonList> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, WeekPersonList item, int position) {
        if (position == 11) {
            helper.getView(R.id.iv_bidding_more).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_iv_biddingimg).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_bidding_more).setVisibility(View.GONE);
            helper.getView(R.id.item_iv_biddingimg).setVisibility(View.VISIBLE);
            ImageLoaderUtils.loadImage(mContext, item.getFace(), (SelectableRoundedImageView) helper.getView(R.id.item_iv_biddingimg));
        }
    }

}
