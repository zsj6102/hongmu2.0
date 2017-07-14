package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.bean.AllAuctionItemInfo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class AllAuctionItemAdapter extends CommonAdapter<AllAuctionItemInfo> {
    public AllAuctionItemAdapter(Context context, List<AllAuctionItemInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, AllAuctionItemInfo item, int position) {

    }
}
