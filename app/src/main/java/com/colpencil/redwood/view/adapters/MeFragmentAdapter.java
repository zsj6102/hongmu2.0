package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * 描述：个人中心 推荐适配器
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/25 10 54
 */
public class MeFragmentAdapter extends CommonAdapter<HomeGoodInfo> {
    /**
     * 控件宽
     */
    private int width;
    /**
     * 控件高
     */
    private int height;

    public MeFragmentAdapter(Context context, List<HomeGoodInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, HomeGoodInfo item, int position) {
        final ImageView iv_item_me = (ImageView) helper.getConvertView().findViewById(R.id.iv_item_me);
        ImageLoaderUtils.loadImage(mContext, item.getImage(), iv_item_me);
        helper.setText(R.id.tv_item_me, item.getGoodsname());
        helper.setText(R.id.tv_item_mePrice, "¥" + item.getCostprice());
        if (width == 0 || height == 0) {
            iv_item_me.post(new Runnable() {//动态更换ImageView的高度
                @Override
                public void run() {
                    width = iv_item_me.getWidth();
                    height = width;
                    iv_item_me.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
            });
        } else {
            iv_item_me.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }
    }
}
