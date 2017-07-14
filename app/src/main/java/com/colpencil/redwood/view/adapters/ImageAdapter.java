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

/**
 * @author 陈宝
 * @Description:评论详情商品的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class ImageAdapter extends CommonAdapter<String> {

    private int width;
    private int height;

    public ImageAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, String item, int position) {
        final ImageView iv = holder.getView(R.id.item_circle_iv);
        width = SharedPreferencesUtil.getInstance(App.getInstance()).getInt("comment_width", 0);
        height = SharedPreferencesUtil.getInstance(App.getInstance()).getInt("comment_height", 0);
        if (width == 0 || height == 0) {
            iv.post(new Runnable() {//动态更换ImageView的高度
                @Override
                public void run() {
                    width = iv.getWidth();
                    height = width;
                    SharedPreferencesUtil.getInstance(App.getInstance()).setInt("comment_width", width);
                    SharedPreferencesUtil.getInstance(App.getInstance()).setInt("comment_height", height);
                    iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
            });
        } else {
            iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }
        ImageLoaderUtils.loadImage(mContext, item, iv);

    }
}
