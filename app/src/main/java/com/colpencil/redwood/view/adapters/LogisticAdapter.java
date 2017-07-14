package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.LogisTicsBean;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class LogisticAdapter extends CommonAdapter<LogisTicsBean> {

    public LogisticAdapter(Context context, List<LogisTicsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, LogisTicsBean item, int position) {
        helper.setText(R.id.post_dialog_item_tv, item.getLogi_name());
        if (item.isChoose()) {
            ((ImageView)(helper.getView(R.id.post_dialog_item_iv))).setImageResource(R.mipmap.post_dialog_check);
        } else {
            ((ImageView)(helper.getView(R.id.post_dialog_item_iv))).setImageResource(R.mipmap.post_dialog_uncheck);
        }
    }
}
