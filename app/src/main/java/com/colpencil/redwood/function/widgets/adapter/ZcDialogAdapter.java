package com.colpencil.redwood.function.widgets.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CoverSpecialItem;

import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;
import com.colpencil.redwood.view.base.CommonAdapter;

public class ZcDialogAdapter extends CommonAdapter<CoverSpecialItem> {

    public ZcDialogAdapter(Context context, List<CoverSpecialItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, CoverSpecialItem item, int position) {
        TextView tv = holder.getView(R.id.post_dialog_item_tv);
        ImageView iv = holder.getView(R.id.post_dialog_item_iv);
        tv.setText(item.getSpecial_name());
        if (item.isChoose()) {
            iv.setImageResource(R.mipmap.post_dialog_check);
        } else {
            iv.setImageResource(R.mipmap.post_dialog_uncheck);
        }
    }
}
