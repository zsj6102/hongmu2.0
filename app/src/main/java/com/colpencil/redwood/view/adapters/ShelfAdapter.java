package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.PlainRack;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class ShelfAdapter extends CommonAdapter<PlainRack> {
    public ShelfAdapter(Context context, List<PlainRack> list,int itemLayoutId){
        super(context,list,itemLayoutId);
    }
    @Override
    public void convert(CommonViewHolder helper, final PlainRack item, int position) {
        Glide.with(mContext).load(item.getThumbnail()).into((ImageView) (helper.getView(R.id.iv_good)));
        helper.setText(R.id.tv_address,item.getAddress());
        helper.setText(R.id.tv_name,item.getGoods_name());
        helper.setText(R.id.tv_shop,item.getStore_name());
        helper.setText(R.id.tv_price,item.getPrice()+"");
        ImageView tv_istop=helper.getView(R.id.tv_istop);
        ImageLoaderUtils.loadImage(mContext,item.getLevel_pic(),tv_istop);
        helper.getView(R.id.goods_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, GoodDetailActivity.class);
                intent.putExtra("goodsId", item.getGoods_id());
                mContext.startActivity(intent);
            }
        });
    }
}
