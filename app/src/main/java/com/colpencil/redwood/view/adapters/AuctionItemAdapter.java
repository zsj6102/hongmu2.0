package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.colpencil.redwood.bean.AuctionInfo;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import static com.colpencil.redwood.R.id.auction_banner;
import static com.colpencil.redwood.R.id.auction_recycler;

public class AuctionItemAdapter extends CommonAdapter<AuctionInfo> {

    public AuctionItemAdapter(Context context, List<AuctionInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, AuctionInfo item, int position) {
        Banner banner= helper.getView(auction_banner);
        RecyclerView recycler=helper.getView(auction_recycler);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new HomeAuctionAdapter(mContext, mDatas.get(position).getHomeAuctionInfos()));
        if (mDatas.get(position).getHomeAuctionInfos().size() > 3) {
            recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        }

        banner.setImageLoader(new MyImageLoader());
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489627919&di=f00e984bd8d85c96cebae9afbdbb2ccd&imgtype=jpg&er=1&src=http%3A%2F%2Fpic71.nipic.com%2Ffile%2F20150629%2F17961491_211658772000_2.jpg");
        imgUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489033202628&di=0dacc1f0cae01168ebe1d362f64a314e&imgtype=0&src=http%3A%2F%2F5.26923.com%2Fdownload%2Fpic%2F000%2F327%2F862e3d13da417308895e06bfac198f87.jpg");
        banner.setImages(imgUrls);
        if (imgUrls.size() > 1) {
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//指示器模式
        } else {
            banner.setBannerStyle(BannerConfig.NOT_INDICATOR);//无指示器模式
        }
        banner.start();
    }
}
