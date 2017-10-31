package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.view.activity.classification.CommoditySearchActivity;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.activity.home.ProductdetailsActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

public class TopBannerViewHolder extends Decomposers<List<BannerVo>> {

    private Banner banner;

    public TopBannerViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_pager_banner, null);
        banner = (Banner) view.findViewById(R.id.home_banner);
        banner.setImageLoader(new MyImageLoader());
        banner.isAutoPlay(false);
        banner.setIndicatorGravity(BannerConfig.CENTER);//指示器位置
        return view;
    }

    @Override
    public void refreshView(final List<BannerVo> bannerVos, int position) {
        if (bannerVos.size() > 1) {
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        } else {
            banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        }
        if (!ListUtils.listIsNullOrEmpty(bannerVos)) {
            List<String> imgUrls = new ArrayList<>();
            for (BannerVo bannerVo : bannerVos) {
                imgUrls.add(bannerVo.getUrl());
            }
            banner.setImages(imgUrls);
            banner.start();
            banner.setVisibility(View.VISIBLE);
            banner.post(new Runnable() {
                @Override
                public void run() {
                    int width = banner.getWidth();
                    int height;
                    if((int)bannerVos.get(0).getImage_scale()!=0){
                        height  = (int) (width / bannerVos.get(0).getImage_scale());
                        banner.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                    }
                }
            });
        } else {
            banner.setVisibility(View.GONE);
        }
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerVo vo = bannerVos.get(position - 1);
                if ("good".equals(vo.getType())) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, GoodDetailActivity.class);
                    intent.putExtra("goodsId", Integer.valueOf(vo.getGoodsId()));
                    mContext.startActivity(intent);
                } else if ("link".equals(vo.getType())) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, MyWebViewActivity.class);
                    intent.putExtra("webviewurl", vo.getHtmlurl());
                    intent.putExtra("type", "banner");
                    mContext.startActivity(intent);
                }else if("zhoupai".equals(vo.getType())){
                    Intent intent = new Intent();
                    intent.setClass(mContext, ProductdetailsActivity.class);
                    intent.putExtra("goodsId",Integer.valueOf(vo.getGoodsId()));
                    mContext.startActivity(intent);
                }else if("cat".equals(vo.getType())){
                    Intent intent = new Intent(mContext, CommoditySearchActivity.class);
                    intent.putExtra("child_cat_id", Integer.valueOf(vo.getGoodsId()));
                    mContext.startActivity(intent);
                }
            }
        });

    }
}
