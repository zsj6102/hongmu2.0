package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.bean.RatedInfo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class RatedAdapter extends CommonAdapter<RatedInfo> {


    public RatedAdapter(Context context, List<RatedInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, RatedInfo item, int position) {

    }
}
