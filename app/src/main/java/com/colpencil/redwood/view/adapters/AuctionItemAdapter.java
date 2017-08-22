package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AuctionInfo;
import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import static com.colpencil.redwood.R.id.auction_banner;
import static com.colpencil.redwood.R.id.auction_recycler;

public class AuctionItemAdapter extends CommonAdapter<JiashangItem> {

    public AuctionItemAdapter(Context context, List<JiashangItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, JiashangItem item, int position) {
        Banner banner= helper.getView(auction_banner);
        RecyclerView recycler=helper.getView(auction_recycler);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new HomeAuctionAdapter(mContext, mDatas.get(position).getList_view_gallery()));
        if (mDatas.get(position).getList_view_gallery().size() > 3) {
            recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        }

        banner.setImageLoader(new MyImageLoader());
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add(item.getCover());
        banner.setImages(imgUrls);
        if (imgUrls.size() > 1) {
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//指示器模式
        } else {
            banner.setBannerStyle(BannerConfig.NOT_INDICATOR);//无指示器模式
        }
        banner.start();
        helper.setText(R.id.auction_test,item.getIntro());
        helper.setText(R.id.tv_creattime,item.getCreate_time());
        helper.setText(R.id.tv_like_num,item.getCollectmember()+"");
        helper.setText(R.id.tv_comment_count,item.getComment_count()+"");
        helper.setText(R.id.price,item.getPrice()+"");
    }
}
