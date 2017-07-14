package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.bean.WinworkInfo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;


public class WinworkAdapter extends CommonAdapter<WinworkInfo> {

    public WinworkAdapter(Context context, List<WinworkInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, WinworkInfo item, int position) {

    }
}
