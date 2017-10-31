package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;

import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class AuctionItemAdapter extends CommonAdapter<JiashangItem> {
    private  MyListener listener;

    public AuctionItemAdapter(Context context, List<JiashangItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);

    }

    @Override
    public void convert(CommonViewHolder helper, final JiashangItem item,final  int position) {
//        Banner banner= helper.getView(auction_banner);

        RecyclerView recycler=helper.getView(R.id.auction_recycler);
        recycler.setHasFixedSize(true);
        HomeAuctionAdapter homeAllGoodsAdapter = new HomeAuctionAdapter(mContext,item.getList_view_gallery());
        homeAllGoodsAdapter.setListener(new HomeAuctionAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ArrayList<String> imgUrls = new ArrayList<String>();
                for(int i = 0; i< item.getList_view_gallery().size();i++){
                    imgUrls.add(item.getList_view_gallery().get(i).getThumbnail());
                }
                Intent intent = new Intent(mContext, GalleyActivity.class);
                intent.putExtra("position", pos);
                intent.putStringArrayListExtra("data",  imgUrls);
                mContext.startActivity(intent);
            }
        });

        recycler.setAdapter(homeAllGoodsAdapter);
        if (mDatas.get(position).getList_view_gallery().size() > 3) {
            recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        }


        helper.getView(R.id.search_header_hint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(position);
            }
        });
        helper.getView(R.id.iv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//收藏
                listener.like(position);
            }
        });
        helper.getView(R.id.reply_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.reply(position);
            }
        });
        if (Integer.parseInt(item.getStore()) <= 0) {
            helper.getView(R.id.tv_sure).setBackgroundResource(R.drawable.buy_no_left);
            helper.getView(R.id.tv_sure).setClickable(false);
            helper.setText(R.id.tv_sure, "已售罄");
        }else{
            helper.getView(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.buy(position);
                }
            });
        }
        if (item.getHave_collection() == 1) {
            helper.setImageById(R.id.iv_like, R.mipmap.iv_like_icon);
        } else {
            helper.setImageById(R.id.iv_like, R.mipmap.iv_unlike_icon);
        }
        final ImageView iv_top = helper.getView(R.id.iv_top);
        if (item.getCover() == null) {
            iv_top.setVisibility(View.GONE);
        } else {
            iv_top.setVisibility(View.VISIBLE);
        }

        ImageLoaderUtils.loadImage(mContext, item.getCover(), iv_top);

        iv_top.post(new Runnable() {
            @Override
            public void run() {
                int width = ScreenUtil.getInstance().getScreenWidth(mContext);
                int height;
                if (item.getCover_proportion() != 0) {
                    height = (int) (width / item.getCover_proportion());
                    iv_top.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
            }
        });

        helper.setText(R.id.tv_introduce,"【"+item.getName()+"】"+item.getIntro());
        helper.setText(R.id.tv_creattime,item.getCreate_time());
        helper.setText(R.id.tv_collect,item.getCollectmember()+"");
        helper.setText(R.id.tv_comment,item.getComment_count()+"");
        helper.setText(R.id.tv_shopprise,"￥"+item.getPrice()+"");
    }


    public void setListener(MyListener listener) {
        this.listener = listener;
    }
    public interface MyListener {
        void click(int position);

        void like(int position);

        void reply(int position);

        void buy(int position);
    }
}
