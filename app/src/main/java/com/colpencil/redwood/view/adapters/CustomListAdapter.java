package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.result.OfficialResult.ResultBean;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.discovery.CustomActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class CustomListAdapter extends CommonAdapter<ResultBean> {

    private Context context;
    private int width;
    private int height;

    public CustomListAdapter(Context context, List<ResultBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(CommonViewHolder holder, final ResultBean item, int position) {
        final ImageView iv = holder.getView(R.id.item_good_img);
        holder.setText(R.id.item_good_name, item.getGoods_name());
        holder.setText(R.id.item_good_price, "￥" + item.getPrice());
        ImageLoaderUtils.loadImage(mContext, item.getImg(), iv);
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
        holder.getView(R.id.item_home_regood_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomActivity.class);
                intent.putExtra("officialid", item.getId());
                context.startActivity(intent);
            }
        });
    }
}
