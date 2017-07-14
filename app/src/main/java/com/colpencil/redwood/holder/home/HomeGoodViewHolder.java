package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;

public class HomeGoodViewHolder extends Decomposers<HomeGoodInfo> {

    private ImageView iv;
    private TextView tv_name;
    private TextView tv_price;

    public HomeGoodViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_good, null);
        iv = (ImageView) view.findViewById(R.id.item_good_img);
        tv_name = (TextView) view.findViewById(R.id.item_good_name);
        tv_price = (TextView) view.findViewById(R.id.item_good_price);
        return view;
    }

    @Override
    public void refreshView(final HomeGoodInfo info, int position) {
        tv_name.setText(info.getGoodsname());
        tv_price.setText("￥" + info.getCostprice());
        ImageLoaderUtils.loadImage(mContext, info.getImage(), iv);
        getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodDetailActivity.class);
                intent.putExtra("goodsId", info.getGoodsid());
                mContext.startActivity(intent);
            }
        });
    }
}
