package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class SearchGoodAdapter extends CommonAdapter<String> {

    public SearchGoodAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, String item, int position) {
        TextView tv = holder.getView(R.id.item_category_name);
        tv.setText(item);
    }
}
