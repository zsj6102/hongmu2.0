package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.RecommendVo;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.adapters.HomeMiddleGoodAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

public class MiddleItemsViewHolder extends Decomposers<RecommendVo> {

    private Banner banner;
    private RecyclerView recycler;

    public MiddleItemsViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_foryou, null);
        banner = (Banner) itemView.findViewById(R.id.recom_banner);
        recycler = (RecyclerView) itemView.findViewById(R.id.recom_recycler);
        return itemView;
    }

    @Override
    public void refreshView(final RecommendVo vo, int position) {

        recycler.setHasFixedSize(true);
        recycler.setAdapter(new HomeMiddleGoodAdapter(mContext, vo.getGoodsList()));
        if (vo.getGoodsList().size() > 3) {
            recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        }
        if (!ListUtils.listIsNullOrEmpty(vo.getAdvList())) {
            banner.setImageLoader(new MyImageLoader());
            List<String> imgUrls = new ArrayList<>();
            for (BannerVo bannerVo : vo.getAdvList()) {
                imgUrls.add(bannerVo.getUrl());
            }
            banner.setImages(imgUrls);
            if (vo.getAdvList().size() > 1) {
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//指示器模式
            } else {
                banner.setBannerStyle(BannerConfig.NOT_INDICATOR);//无指示器模式
            }
            banner.start();
        }
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int mposition) {
                if (!ListUtils.listIsNullOrEmpty(vo.getAdvList())) {
                    BannerVo bannerVo = vo.getAdvList().get(mposition - 1);
                    if ("good".equals(bannerVo.getType())) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, GoodDetailActivity.class);
                        intent.putExtra("goodsId", bannerVo.getGoodsId());
                        mContext.startActivity(intent);
                    } else if ("link".equals(bannerVo.getType())) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, MyWebViewActivity.class);
                        intent.putExtra("webviewurl", bannerVo.getHtmlurl());
                        intent.putExtra("type", "banner");
                        mContext.startActivity(intent);
                    }
                }
            }
        });
    }

}
