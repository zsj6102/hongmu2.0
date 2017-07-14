package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * @author 陈宝
 * @Description:商品列表的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class HomeGoodListAdapter extends CommonAdapter<HomeGoodInfo> {

    public HomeGoodListAdapter(Context context, List<HomeGoodInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, final HomeGoodInfo item, int position) {
        helper.setText(R.id.item_good_name, item.getGoodsname());
        helper.setText(R.id.item_good_price, "￥" + item.getCostprice());
        ImageLoaderUtils.loadImage(mContext, item.getImage(), (ImageView) helper.getView(R.id.item_good_img));
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodDetailActivity.class);
                intent.putExtra("goodsId", item.getGoodsid());
                mContext.startActivity(intent);
            }
        });
    }
}
