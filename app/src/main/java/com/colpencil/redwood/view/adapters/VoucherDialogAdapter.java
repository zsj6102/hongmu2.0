package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;

import com.colpencil.redwood.R;

import com.colpencil.redwood.bean.result.MemberCoupon;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class VoucherDialogAdapter extends CommonAdapter<MemberCoupon> {

    public VoucherDialogAdapter(Context context, List<MemberCoupon> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, MemberCoupon item, int position) {
        helper.setImageByUrl(mContext, R.id.item_voucher_iv, item.getCpns_img());
        helper.getView(R.id.item_voucher_choose).setVisibility(View.VISIBLE);
        helper.getView(R.id.divide_line).setVisibility(View.GONE);
        if (item.isChoose()) {
            helper.setImageById(R.id.item_voucher_choose, R.mipmap.select_yes_red);
        } else {
            helper.setImageById(R.id.item_voucher_choose, R.mipmap.select_no);
        }
    }
}
