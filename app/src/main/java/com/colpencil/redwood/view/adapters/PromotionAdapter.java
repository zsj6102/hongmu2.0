package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.PromotionVo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class PromotionAdapter extends CommonAdapter<PromotionVo> {

    public PromotionAdapter(Context context, List<PromotionVo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, PromotionVo item, int position) {
        TextView tv_type = holder.getView(R.id.promotion_type);
        TextView tv_content = holder.getView(R.id.promotion_content);
        tv_type.setText(item.getName());
        tv_content.setText(item.getBrief());
    }

    public void notifys(List<PromotionVo> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }
}
