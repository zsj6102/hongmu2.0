package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.List;

/**
 * 描述：商品搜索
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 16 34
 */
public class CommoditySearchAdapter extends CommonAdapter<HomeGoodInfo> {
    /**
     * 控件宽
     */
    private int width;
    /**
     * 控件高
     */
    private int height;
    /**
     * ImageView 控件
     */
    private ImageView iv_commoditySearch;

    public CommoditySearchAdapter(Context context, List<HomeGoodInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, HomeGoodInfo goodInfo, int position) {
        View view_commoditySearch = helper.getConvertView().findViewById(R.id.view_commoditySearch);
        TextView tv_commoditySearchName = (TextView) helper.getConvertView().findViewById(R.id.tv_commoditySearchName);
        TextView tv_commoditySearchPrice = (TextView) helper.getConvertView().findViewById(R.id.tv_commoditySearchPrice);
        iv_commoditySearch = (ImageView) helper.getConvertView().findViewById(R.id.iv_commoditySearch);
        ImageLoaderUtils.loadImage(mContext, goodInfo.getImage(), iv_commoditySearch);
        if (position == 0 || position == 1) {
            view_commoditySearch.setVisibility(View.VISIBLE);
        } else {
            view_commoditySearch.setVisibility(View.GONE);
        }
        if (width == 0 || height == 0) {
            width = SharedPreferencesUtil.getInstance(App.getInstance()).getInt("CommoditySearchAdapter_width", 0);
            height = SharedPreferencesUtil.getInstance(App.getInstance()).getInt("CommoditySearchAdapter_height", 0);
            if (width == 0 || height == 0) {
                iv_commoditySearch.post(new Runnable() {//动态更换ImageView的高度
                    @Override
                    public void run() {
                        width = iv_commoditySearch.getWidth();
                        height = width;
                        SharedPreferencesUtil.getInstance(App.getInstance()).setInt("CommoditySearchAdapter_width", width);
                        SharedPreferencesUtil.getInstance(App.getInstance()).setInt("CommoditySearchAdapter_height", height);
                        iv_commoditySearch.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                    }
                });
            } else {
                iv_commoditySearch.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            }
        } else {
            iv_commoditySearch.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }
        tv_commoditySearchName.setText(goodInfo.getGoodsname());
        tv_commoditySearchPrice.setText(goodInfo.getCostprice() + "");

    }


}

