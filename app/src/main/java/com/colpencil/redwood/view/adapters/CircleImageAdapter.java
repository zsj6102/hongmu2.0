package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.List;

public class CircleImageAdapter extends CommonAdapter<String> {

    private int width;
    private int height;
    private ImageView iv;

    public CircleImageAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, String item, final int position) {
        iv = holder.getView(R.id.item_circle_iv);
        if (width == 0 || height == 0) {
            width = SharedPreferencesUtil.getInstance(App.getInstance()).getInt("CommoditySearchAdapter_width", 0);
            height = SharedPreferencesUtil.getInstance(App.getInstance()).getInt("CommoditySearchAdapter_height", 0);
            if (width == 0 || height == 0) {
                iv.post(new Runnable() {//动态更换ImageView的高度
                    @Override
                    public void run() {
                        width = iv.getWidth();
                        height = width;
                        SharedPreferencesUtil.getInstance(App.getInstance()).setInt("CommoditySearchAdapter_width", width);
                        SharedPreferencesUtil.getInstance(App.getInstance()).setInt("CommoditySearchAdapter_height", height);
                        iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                    }
                });
            } else {
                iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            }
        } else {
            iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }
        ImageLoaderUtils.loadImage(mContext, item, iv);

    }
}
