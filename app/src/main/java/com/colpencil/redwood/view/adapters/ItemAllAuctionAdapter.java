package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllGoodsInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;
import com.youth.banner.Banner;

import java.util.List;

import static com.colpencil.redwood.R.id.auction_banner;
import static com.colpencil.redwood.R.id.auction_recycler;
import static com.colpencil.redwood.R.id.recycler;
import static com.colpencil.redwood.R.string.comment;

public class ItemAllAuctionAdapter extends CommonAdapter<AllGoodsInfo> {
    private MyListener listener;
    public ItemAllAuctionAdapter(Context context, List<AllGoodsInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, final AllGoodsInfo item, final int position) {

        ImageView iv_head=helper.getView(R.id.iv_head);
        ImageLoaderUtils.loadImage(mContext,item.getStore_logo(),iv_head);
        helper.setText(R.id.tv_shopname,item.getStore_name());
        helper.setText(R.id.tv_address,item.getStore_city());
        helper.setText(R.id.tv_shoplevel,item.getPoint_name());
        ImageView iv_shoplevel=helper.getView(R.id.iv_shoplevel);
        ImageLoaderUtils.loadImage(mContext,item.getPoint(),iv_shoplevel);

        helper.setText(R.id.tv_shoptype,item.getStore_type_name());

        ImageView iv_shoptype=helper.getView(R.id.iv_shoptype);
        ImageLoaderUtils.loadImage(mContext,item.getStore_type(),iv_shoptype);
//        helper.setText(R.id.tv_praise,item.getGoods_rate()+"好评");
        helper.setText(R.id.tv_fans,item.getFans_count()+"粉丝");
        helper.setText(R.id.tv_introduce,item.getIntro());
        helper.setText(R.id.tv_creattime,item.getCreate_time());
        helper.setText(R.id.tv_collect,item.getCollectmember()+"");
        helper.setText(R.id.tv_comment,item.getComment_count()+"");
        helper.setText(R.id.tv_shopprise,item.getPrice()+"");
        helper.getView(R.id.search_header_hint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(position);
            }
        });
        helper.getView(R.id.iv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.like(position);
            }
        });
        helper.getView(R.id.reply_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.reply(position);
            }
        });
        final ImageView iv_top=helper.getView(R.id.iv_top);
        iv_top.post(new Runnable() {
            @Override
            public void run() {
                int width = ScreenUtil.getInstance().getScreenWidth(mContext);
                int height;
                if(item.getCover_proportion()!=0){
                    height = (int)(width/item.getCover_proportion());
                }else{
                    height = width/2;
                }
                iv_top.setLayoutParams(new LinearLayout.LayoutParams(width,height));
            }
        });
        if (item.getHave_collection() == 1) {
            helper.setImageById(R.id.iv_like, R.mipmap.iv_like_icon);
        } else {
            helper.setImageById(R.id.iv_like, R.mipmap.iv_unlike_icon);
        }
        if(item.getCover() == null){
            iv_top.setVisibility(View.GONE);
        }else{
            iv_top.setVisibility(View.VISIBLE);
        }
        ImageLoaderUtils.loadImage(mContext,item.getCover(),iv_top);

        TextView tv_istop=helper.getView(R.id.tv_istop);
        if(item.getIs_top()==1){
            tv_istop.setVisibility(View.VISIBLE);
        }else{
            tv_istop.setVisibility(View.GONE);
        }
        RecyclerView auction_recycler=helper.getView(R.id.auction_recycler);
        auction_recycler.setHasFixedSize(true);
        auction_recycler.setAdapter(new HomeAllGoodsAdapter(mContext,item.getList_view_gallery()));
        if (item.getList_view_gallery().size() > 3) {
            auction_recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            auction_recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        }
    }
    public void setListener(MyListener listener){
        this.listener = listener;
    }
    public  interface MyListener{
        void click(int position);

        void like(int position);

        void reply(int position);
    }
}
