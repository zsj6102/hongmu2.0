package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class HomeReGoodAdapter extends CommonAdapter<HomeGoodInfo> {

    private Context context;
    /**
     * 控件宽
     */
    private int width;
    /**
     * 控件高
     */
    private int height;

    public HomeReGoodAdapter(Context context, List<HomeGoodInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(CommonViewHolder holder, final HomeGoodInfo item, int position) {
        final ImageView iv = holder.getView(R.id.iv_good_img);
        TextView tv_name = holder.getView(R.id.iv_good_name);
        TextView tv_price = holder.getView(R.id.iv_good_price);
        LinearLayout ll_content = holder.getView(R.id.item_home_regood_ll);
        tv_name.setText(item.getGoodsname());
        tv_price.setText("￥" + item.getCostprice());
        ImageLoaderUtils.loadImage(mContext, item.getImage(), iv);
        if (width == 0 || height == 0) {
            iv.post(new Runnable() {//动态更换ImageView的高度
                @Override
                public void run() {
                    width = iv.getWidth();
                    height = width;
                    iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
            });
        } else {
            iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }
        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodDetailActivity.class);
                intent.putExtra("goodsId", item.getGoodsid());
                context.startActivity(intent);
            }
        });
    }
}
