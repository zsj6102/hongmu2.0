package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CardInfo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.util.List;

import static android.R.id.list;


public class CardWallAdapter extends CommonAdapter<CardInfo> {

    private  ComOnClickListener listener;
    public CardWallAdapter(Context context, List<CardInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, CardInfo item, final int position) {
        Glide.with(mContext).load(item.getFace()).into((SelectableRoundedImageView) helper.getView(R.id.iv_head));
        Glide.with(mContext).load(item.getMember_photo()).into((ImageView) helper.getView(R.id.iv_dengji));
        Glide.with(mContext).load(item.getStore_type_path()).into((ImageView) helper.getView(R.id.iv_type));
        if(item.getStore_type() == 0){
            helper.setText(R.id.tv_type,"普通会员");
        }else if(item.getStore_type() == 1){
            helper.setText(R.id.tv_type,"个人商家");
        }else if(item.getStore_type() == 2){
            helper.setText(R.id.tv_type,"品牌商家");
        }else if(item.getStore_type() == 3){
            helper.setText(R.id.tv_type,"名师名匠");
        }
        if(item.getStore_recommend() == 0){
            helper.getView(R.id.tv_recommend).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.tv_recommend).setVisibility(View.VISIBLE);
        }
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
        helper.getView(R.id.content_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.contentClick(position);
            }
        });
    }
    public void setListener(ComOnClickListener listener) {
        this.listener = listener;
    }
    public interface ComOnClickListener {
        void careClick(int position);

        void contentClick(int position);
    }
}
