package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class WeekShelfAdapter extends CommonAdapter<String> {

    public WeekShelfAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, String item, int position) {
        helper.setText(R.id.tv_title,item);
    }

}
