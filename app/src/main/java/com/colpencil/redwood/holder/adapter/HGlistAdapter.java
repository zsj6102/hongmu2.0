package com.colpencil.redwood.holder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Goods_list_item;

import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class HGlistAdapter  extends CommonAdapter<Goods_list_item> {

    public HGlistAdapter(Context context, List<Goods_list_item> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, final Goods_list_item item, int position) {
        helper.setText(R.id.item_good_name, item.getName());
        helper.setText(R.id.item_good_price, "￥" + item.getPrice());
        ImageLoaderUtils.loadImage(mContext, item.getThumbnail(), (ImageView) helper.getView(R.id.item_good_img));
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodDetailActivity.class);
                intent.putExtra("goodsId", item.getGoods_id());
                mContext.startActivity(intent);
            }
        });
    }
}
