package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.RecommendVo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.MosaicRecyclerView;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.adapters.HomeMiddleGoodAdapter;
import com.colpencil.redwood.view.adapters.ViewPagerAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

public class MiddleItemsViewHolder extends Decomposers<RecommendVo> {

//    private Banner banner;
    private MosaicRecyclerView recycler;
    private ViewPager viewPager;
    private List<View> viewPagerList = new ArrayList<>();
    private  MosaicRecyclerView.LayoutManager layoutManager;
     public MiddleItemsViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_foryou, null);
//        banner = (Banner) itemView.findViewById(R.id.recom_banner);
        viewPager = (ViewPager) itemView.findViewById(R.id.viewpager);
        recycler = (MosaicRecyclerView) itemView.findViewById(R.id.recom_recycler);
        return itemView;
    }

    @Override
    public void refreshView(final RecommendVo vo, int position) {

        recycler.setHasFixedSize(true);
        int spanCount = 1; // 只显示一行
        layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(new HomeMiddleGoodAdapter(mContext, vo.getGoodsList()));
        if (!ListUtils.listIsNullOrEmpty(vo.getAdvList())) {
            viewPagerList.clear();
            for (int i = 0; i < vo.getAdvList().size(); i++) {
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageLoaderUtils.loadImage(mContext, vo.getAdvList().get(i).getUrl(), imageView);
                viewPagerList.add(imageView);
            }
            viewPager.setAdapter(new ViewPagerAdapter(viewPagerList));
        } else {
            viewPager.setVisibility(View.GONE);
        }
        ViewConfiguration configuration = ViewConfiguration.get(mContext);
        final float mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            int touchFlag = 0;
            float x = 0, y = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchFlag = 0;
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float xDiff = Math.abs(event.getX() - x);
                        float yDiff = Math.abs(event.getY() - y);
                        if (xDiff < mTouchSlop && xDiff >= yDiff)
                            touchFlag = 0;
                        else
                            touchFlag = -1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (touchFlag == 0) {
                            if (!ListUtils.listIsNullOrEmpty(vo.getAdvList())) {
                                int currentItem = viewPager.getCurrentItem();
                                BannerVo bannerVo = vo.getAdvList().get(currentItem);
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
                        break;
                }
                return false;
            }
        });
//        if (vo.getGoodsList().size() > 3) {
//            recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//        } else {
//            recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
//        }

//        if (!ListUtils.listIsNullOrEmpty(vo.getAdvList())) {
//            banner.setImageLoader(new MyImageLoader());
//            List<String> imgUrls = new ArrayList<>();
//            for (BannerVo bannerVo : vo.getAdvList()) {
//                imgUrls.add(bannerVo.getUrl());
//            }
//            banner.setImages(imgUrls);
//            if (vo.getAdvList().size() > 1) {
//                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//指示器模式
//            } else {
//                banner.setBannerStyle(BannerConfig.NOT_INDICATOR);//无指示器模式
//            }
//            banner.start();
//        }
//        banner.setOnBannerClickListener(new OnBannerClickListener() {
//            @Override
//            public void OnBannerClick(int mposition) {
//                if (!ListUtils.listIsNullOrEmpty(vo.getAdvList())) {
//                    BannerVo bannerVo = vo.getAdvList().get(mposition - 1);
//                    if ("good".equals(bannerVo.getType())) {
//                        Intent intent = new Intent();
//                        intent.setClass(mContext, GoodDetailActivity.class);
//                        intent.putExtra("goodsId", bannerVo.getGoodsId());
//                        mContext.startActivity(intent);
//                    } else if ("link".equals(bannerVo.getType())) {
//                        Intent intent = new Intent();
//                        intent.setClass(mContext, MyWebViewActivity.class);
//                        intent.putExtra("webviewurl", bannerVo.getHtmlurl());
//                        intent.putExtra("type", "banner");
//                        mContext.startActivity(intent);
//                    }
//                }
//            }
//        });
    }

}
