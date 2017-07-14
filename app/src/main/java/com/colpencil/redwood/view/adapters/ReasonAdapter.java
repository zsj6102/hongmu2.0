package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RefundReason;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * @author 陈宝
 * @Description:这个是空的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/11
 */
public class ReasonAdapter extends CommonAdapter<RefundReason> {

    public ReasonAdapter(Context context, List<RefundReason> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, RefundReason refundReason, final int position) {
        if (refundReason.isChooseState() == false) {
            ((ImageView)(holder.getView(R.id.iv_selectReason))).setImageResource(R.mipmap.select_no);
        } else {
            ((ImageView)(holder.getView(R.id.iv_selectReason))).setImageResource(R.mipmap.select_yes_red);
        }
        if (position + 1 == mDatas.size()) {
            holder.getView(R.id.reason_view).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.reason_view).setVisibility(View.VISIBLE);
        }
        holder.setText(R.id.tv_reason_name, refundReason.getRefundResonContent());
    }

}
