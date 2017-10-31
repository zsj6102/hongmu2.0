package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.mine.StoreHomeActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class FansItemAdapter extends CommonAdapter<ItemStoreFans>{
    private CareClick listener;

    public FansItemAdapter(Context context, List<ItemStoreFans> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }
    @Override
    public void convert(CommonViewHolder holder, final ItemStoreFans item, final int position) {
        ImageLoaderUtils.loadImage(mContext, item.getFace(), (ImageView) holder.getView(R.id.iv_head));
        holder.setText(R.id.store_name,item.getStore_name());
        if(item.getIsfocus() == 0){
            holder.getView(R.id.layout_care).setVisibility(View.GONE);
            holder.getView(R.id.layout_uncare).setVisibility(View.VISIBLE);
        }else{
            holder.getView(R.id.layout_care).setVisibility(View.VISIBLE);
            holder.getView(R.id.layout_uncare).setVisibility(View.GONE);
        }
        holder.getView(R.id.all_laytout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.toClick(position);
//                Intent intent = new Intent(mContext, StoreHomeActivity.class);
//                intent.putExtra("type", item.getStore_type() + "");
//                intent.putExtra("store_id", Integer.parseInt(item.getStore_id()));
//                mContext.startActivity(intent);
            }
        });
        holder.getView(R.id.layout_care_uncare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.careClick(position);
            }
        });
    }
    public void setListener(CareClick click){
        this.listener = click;
    }
    public interface CareClick{
        void careClick(int position);
        void toClick(int position);
    }
}
