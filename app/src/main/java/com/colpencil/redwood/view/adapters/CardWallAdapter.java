package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.util.List;


public class CardWallAdapter extends CommonAdapter<CardWallInfo> {

    public CardWallAdapter(Context context, List<CardWallInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, CardWallInfo item, int position) {
        Glide.with(mContext).load(item.head).into((SelectableRoundedImageView) helper.getView(R.id.iv_head));
    }
}
