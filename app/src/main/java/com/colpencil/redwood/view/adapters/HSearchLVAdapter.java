package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Cat;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/13 17 11
 */
public class HSearchLVAdapter extends CommonAdapter<Cat> {

    public HSearchLVAdapter(Context context, List<Cat> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, Cat cat, int position) {
        helper.setText(R.id.lv_item_hSearchTile, cat.getName());
        if (cat.isChooseState()) {
            ((TextView) helper.getView(R.id.lv_item_hSearchTile)).setTextColor(mContext.getResources().getColor(R.color.color_bf5d5d));
        } else {
            ((TextView) helper.getView(R.id.lv_item_hSearchTile)).setTextColor(mContext.getResources().getColor(R.color.color_613b33));
        }
        if (position + 1 == mDatas.size()) {
            helper.getView(R.id.view_item_hsearchlv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_item_hsearchlv).setVisibility(View.GONE);
        }
    }

    public void notifys() {
        if (!ListUtils.listIsNullOrEmpty(mDatas)) {
            for (Cat cat : mDatas) {
                cat.setChooseState(false);
            }
        }
        notifyDataSetChanged();
    }
}
