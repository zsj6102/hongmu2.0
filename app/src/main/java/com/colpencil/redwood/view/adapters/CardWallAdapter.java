package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CardInfo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.util.List;


public class CardWallAdapter extends CommonAdapter<CardInfo> {

    private  ComOnClickListener listener;
    public CardWallAdapter(Context context, List<CardInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, CardInfo item, final int position) {
        Glide.with(mContext).load(item.getFace()).into((SelectableRoundedImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_praise_count,item.getPraise_rate());
        helper.setText(R.id.tv_follow,item.getStore_count()+"");
        if(item.getIsfocus() == 0){
            helper.getView(R.id.layout_care).setVisibility(View.GONE);
            helper.getView(R.id.layout_uncare).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.layout_care).setVisibility(View.VISIBLE);
            helper.getView(R.id.layout_uncare).setVisibility(View.GONE);
        }
        helper.getView(R.id.layout_care_uncare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.careClick(position);
            }
        });
    }
    public void setListener(ComOnClickListener listener) {
        this.listener = listener;
    }
    public interface ComOnClickListener {
        void careClick(int position);

    }
}
